package com.drainey.bakingapp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.model.Recipe;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = findViewById(R.id.tv_detail_view);
        if(getIntent().getExtras() != null){
            Recipe recipe = getIntent().getExtras().getParcelable(RecipeListFragment.RECIPE_DATA);
            textView.setText(recipe.toString());
        } else {
            textView.setText("Recipe data sent!!!");
        }
    }

}
