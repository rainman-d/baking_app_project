package com.drainey.bakingapp.data;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by david-rainey on 7/16/18.
 */

public class RecipeAdapter extends BaseAdapter {

    private Context mContext;
    private List<Recipe> mRecipeList;

    public RecipeAdapter(Context mContext, List<Recipe> mRecipeList) {
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
    }

    @Override
    public int getCount() {
        return this.mRecipeList.size();
    }

    @Override
    public Object getItem(int i) {
        if(mRecipeList != null && mRecipeList.size() >= i){
            return mRecipeList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        Context con = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(con);
            View v = inflater.inflate(R.layout.recipe_card, viewGroup, false);

            textView = (TextView) v.findViewById(R.id.tv_recipe_name);
            if(mContext.getResources().getBoolean(R.bool.is_tablet)){
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            }
        } else {
            textView = (TextView)view;
        }

        textView.setText(mRecipeList.get(i).getName());
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.background_card));

        return textView;
    }

    public void setRecipeList(List<Recipe> recipeList){
        this.mRecipeList = recipeList;
        notifyDataSetChanged();
    }
}
