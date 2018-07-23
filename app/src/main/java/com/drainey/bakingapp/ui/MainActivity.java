package com.drainey.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.drainey.bakingapp.R;

public class MainActivity extends AppCompatActivity implements  RecipeListFragment.OnRecipeClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Choose Recipe");
    }


    @Override
    public void onRecipeSelected(int position) {

    }
}
