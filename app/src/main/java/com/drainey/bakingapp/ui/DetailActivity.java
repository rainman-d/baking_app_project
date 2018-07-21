package com.drainey.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.data.RecipeStepAdapter;
import com.drainey.bakingapp.model.Ingredient;
import com.drainey.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_ingredients_list)TextView mIngredients;
    @BindView(R.id.rv_recipe_steps)RecyclerView stepsRecyclerView;
    private Recipe mRecipe;
    private RecipeStepAdapter recipeStepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if(getIntent().getExtras() != null){
            mRecipe = getIntent().getExtras().getParcelable(RecipeListFragment.RECIPE_DATA);
            buildDetails();
        }

    }

    private void buildDetails(){
        // set the action bar to show the name of the recipe
        getSupportActionBar().setTitle(mRecipe.getName());

        // set the ingredients in the text view
        String ingredientList = "";
        int size = mRecipe.getIngredients().size();
        for(int x = 0; x < size; x++){
            Ingredient i = mRecipe.getIngredients().get(x);
            if(x != 0){
                ingredientList += "\n";
            }
            ingredientList += "\u2022 " + i.getQuantity() + " " + i.getMeasure() + " " + i.getIngredient();
        }
        mIngredients.setText(ingredientList);

        // create the recipe steps view
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeStepAdapter = new RecipeStepAdapter(mRecipe.getSteps(), this);
        stepsRecyclerView.setAdapter(recipeStepAdapter);
    }

}
