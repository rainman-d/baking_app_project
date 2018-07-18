package com.drainey.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.data.RecipeAdapter;
import com.drainey.bakingapp.helper.BakingDataUtils;
import com.drainey.bakingapp.helper.JsonUtils;
import com.drainey.bakingapp.helper.NetworkUtils;
import com.drainey.bakingapp.model.Recipe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeListFragment.OnRecipeClickListener} interface
 * to handle interaction events.
 * Use the {@link RecipeListFragment} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = RecipeListFragment.class.getSimpleName();
    public static final String RECIPE_DATA = "recipe_data";
    public static final int BAKING_DATA_LOADER_ID = 101;
    RecipeAdapter mRecipeAdapter;

    OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener{
        void onRecipeSelected(int position);
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(BAKING_DATA_LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.recipes_grid_view);

        mRecipeAdapter = new RecipeAdapter(getContext(), new ArrayList<Recipe>());
        gridView.setAdapter(mRecipeAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                mCallback.onRecipeSelected(position);
                Recipe recipe = (Recipe) mRecipeAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(RECIPE_DATA, recipe);
                startActivity(intent);
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnRecipeClickListener)context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {

        return new AsyncTaskLoader<String>(getContext()) {
            String bakingData = null;
            @Override
            public String loadInBackground() {
                URL bakingDataURL = BakingDataUtils.getBakingAppDataUrl();
                String jsonData = null;
                try{
                    jsonData = NetworkUtils.getData(bakingDataURL);
                }catch (IOException e){
                    Log.e(LOG_TAG, "Error retrieving data!", e);
                }

                return jsonData;
                // for testing without network access
//                return JsonUtils.getRecipeList(getContext());
            }

            @Override
            public void deliverResult(String data) {
                bakingData = data;
                super.deliverResult(data);
            }

            @Override
            protected void onStartLoading() {
                if(bakingData != null){
                    deliverResult(bakingData);
                } else {
                    forceLoad();
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        List<Recipe> recipeList = JsonUtils.getRecipeList(s);
        Log.v(LOG_TAG, "List returned: " + recipeList);
        mRecipeAdapter.setRecipeList(recipeList);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        mRecipeAdapter.setRecipeList(null);
    }


}
