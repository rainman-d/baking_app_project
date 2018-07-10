package com.drainey.bakingapp.model;

import java.util.List;

/**
 * Created by david-rainey on 7/9/18.
 */

public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<RecipeStep> steps;

    public Recipe() {
    }

    public Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }
}
