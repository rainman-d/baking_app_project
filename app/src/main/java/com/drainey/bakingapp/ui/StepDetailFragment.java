package com.drainey.bakingapp.ui;


import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.data.RecipeStepAdapter;
import com.drainey.bakingapp.model.RecipeStep;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment implements Player.EventListener {
    private List<RecipeStep> mRecipeSteps;
    private int currentStepIndex;
    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private static final String CURRENT_STEP_INDEX = "current_step";
    private static final String STEPS_LIST = "steps_list";
    private static final String PLAYER_POSITION = "player_position";
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    TextView mStepDetailTextView;
    Toolbar mToolbar;
    TextView mPreviousStepTextView;
    TextView mNextStepTextView;
    boolean mDualPane;

    private long playerPosition = C.TIME_UNSET;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public static StepDetailFragment createInstance(List<RecipeStep> list, int currentIndex){
        StepDetailFragment f = new StepDetailFragment();

        Bundle args = new Bundle();
        args.putInt(CURRENT_STEP_INDEX, currentIndex);
        args.putParcelableArrayList(STEPS_LIST, new ArrayList<>(list));
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDualPane = getActivity().getResources().getBoolean(R.bool.is_tablet);

        if(savedInstanceState != null){
            currentStepIndex = savedInstanceState.getInt(CURRENT_STEP_INDEX);
            mRecipeSteps = savedInstanceState.getParcelableArrayList(STEPS_LIST);
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
        } else {
            initializeArguments();
        }
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        mPlayerView = rootView.findViewById(R.id.ex_step_video);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                getResources(), android.R.drawable.presence_video_busy
        ));

        mStepDetailTextView = rootView.findViewById(R.id.tv_step_details);
        mToolbar = rootView.findViewById(R.id.toolbar);
        mPreviousStepTextView = rootView.findViewById(R.id.tv_previous_step);
        mNextStepTextView = rootView.findViewById(R.id.tv_next_step);

        toggleNavButtons();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getActivity().isChangingConfigurations()){
            return;
        } else{
            releasePlayer();
        }
    }

    private void initializeArguments(){
        if(mDualPane){
            mRecipeSteps = getArguments().getParcelableArrayList(STEPS_LIST);
            currentStepIndex = getArguments().getInt(CURRENT_STEP_INDEX);
        } else {
            if(getActivity().getIntent().hasExtra(RecipeStepAdapter.RECIPE_STEPS)){
                mRecipeSteps= getActivity().getIntent().getExtras().getParcelableArrayList(RecipeStepAdapter.RECIPE_STEPS);
            }
            if(getActivity().getIntent().hasExtra(RecipeStepAdapter.STEP_INDEX)){
                currentStepIndex = getActivity().getIntent().getExtras().getInt(RecipeStepAdapter.STEP_INDEX);
            }
        }

    }

    private void initializeMediaSession(){
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                    PlaybackStateCompat.ACTION_PAUSE |
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                    PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new SessionCallback());
        mMediaSession.setActive(true);
    }

    private void initializePlayer(String mediaSource){
        if(mExoPlayer == null){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(trackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            Uri uri = Uri.parse(mediaSource);
            MediaSource mediaSource1 = new ExtractorMediaSource.Factory(
                    new DefaultDataSourceFactory(getContext(), userAgent)).createMediaSource(uri);
            mExoPlayer.prepare(mediaSource1);
            mExoPlayer.setPlayWhenReady(false);

        }
    }

    private void toggleNavButtons(){
        if(mDualPane){
            // if tablet remove navigation toolbar
            mToolbar.setVisibility(View.GONE);
        } else {
            int prevStepVisibility = currentStepIndex == 0 ? View.GONE: View.VISIBLE;
            mPreviousStepTextView.setVisibility(prevStepVisibility);

            int nextStepVisibility = (currentStepIndex == mRecipeSteps.size() - 1)? View.GONE : View.VISIBLE;
            mNextStepTextView.setVisibility(nextStepVisibility);

            if(mPreviousStepTextView.getVisibility() == View.VISIBLE){
                mPreviousStepTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentStepIndex--;
                        toggleNavButtons();
                        resetRecipeStep();
                    }
                });
            }

            if(mNextStepTextView.getVisibility() == View.VISIBLE){
                mNextStepTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentStepIndex++;
                        toggleNavButtons();
                        resetRecipeStep();
                    }
                });
            }
        }

    }

    private void resetRecipeStep(){
        mExoPlayer = null;
        if(mRecipeSteps != null && mRecipeSteps.size() != 0){
            if(mRecipeSteps.get(currentStepIndex).getVideoURL().isEmpty()){
                mPlayerView.setVisibility(View.GONE);
            }else {
                if(mPlayerView.getVisibility() == View.GONE){
                    mPlayerView.setVisibility(View.VISIBLE);
                }
                initializeMediaSession();
                initializePlayer(mRecipeSteps.get(currentStepIndex).getVideoURL());
            }
            setStepDetails();
        }
    }

    private void setStepDetails(){
        mStepDetailTextView.setText(mRecipeSteps.get(currentStepIndex).getDescription());
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !mDualPane) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPlayerView.getLayoutParams();
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);
            mToolbar.setVisibility(View.GONE);
            mStepDetailTextView.setVisibility(View.GONE);
        }

        resetRecipeStep();
        // if player position was saved seek ahead to it
        if(playerPosition != C.TIME_UNSET) {
            mExoPlayer.seekTo(playerPosition);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        long position = mExoPlayer.getCurrentPosition();
        outState.putInt(CURRENT_STEP_INDEX, currentStepIndex);
        outState.putParcelableArrayList(STEPS_LIST, new ArrayList<>(mRecipeSteps));
        outState.putLong(PLAYER_POSITION, position);

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private class SessionCallback extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }
    }

    private void releasePlayer(){
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(false);
        }
        super.onPause();
    }
}
