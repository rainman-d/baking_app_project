package com.drainey.bakingapp.data;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.model.RecipeStep;
import com.drainey.bakingapp.ui.StepDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david-rainey on 7/18/18.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder>{
    public static final String RECIPE_STEPS = "recipe_steps";
    public static final String STEP_INDEX = "recipe_step_index";
    private List<RecipeStep> recipeSteps;
    Context mContext;
    OnStepClickListener mCallback;

    public RecipeStepAdapter(List<RecipeStep> recipeSteps, Context mContext) {
        this.recipeSteps = recipeSteps;
        this.mContext = mContext;
        try{
            mCallback = (OnStepClickListener)mContext;
        } catch (ClassCastException e){
            throw new ClassCastException(mContext.toString() + " must implement OnStepClickListener");
        }
    }

    public interface OnStepClickListener{
        void onRecipeStepClicked(int position);
    }

    @Override
    public RecipeStepAdapter.RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipe_step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutId, parent, shouldAttachImmediately);
        RecipeStepViewHolder viewHolder = new RecipeStepViewHolder(view, mContext);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepAdapter.RecipeStepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder{
        TextView recipeStepView;
        Context context;
        public RecipeStepViewHolder(View itemView, final Context context) {
            super(itemView);
            this.context = context;
            recipeStepView = (TextView)itemView.findViewById(R.id.tv_recipe_step);
            recipeStepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Context con = view.getContext();
                    if(position != RecyclerView.NO_POSITION){
                        if(mContext.getResources().getBoolean(R.bool.is_tablet)){
                            mCallback.onRecipeStepClicked(position);
                        } else {
                            Intent intent = new Intent(mContext, StepDetailActivity.class);
                            intent.putParcelableArrayListExtra(RECIPE_STEPS, new ArrayList<Parcelable>(recipeSteps));
                            intent.putExtra(STEP_INDEX, position);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        }

        void bind(int index){
            recipeStepView.setText("" + (index + 1) + ": " + recipeSteps.get(index).getShortDescription());
        }
    }
}
