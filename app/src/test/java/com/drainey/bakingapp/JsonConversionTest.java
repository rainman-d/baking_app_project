package com.drainey.bakingapp;

import com.drainey.bakingapp.helper.JsonUtils;
import com.drainey.bakingapp.model.Ingredient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david-rainey on 7/9/18.
 */

public class JsonConversionTest {
    private String testIngredientsArray = "{ingredients:[{quantity: 2,measure:\"CUP\",ingredient:\"Graham Cracker crumbs\"}, " +
            "{quantity:6,measure:\"TBLSP\",ingredient:\"unsalted butter, melted\"}]}";
    private String testIngredient = "{quantity: 2,measure:\"CUP\",ingredient:\"Graham Cracker crumbs\"}";

    @Test
    public void testJsonIngredientConversionToIngredientClass(){
        Gson gson = new Gson();
        Ingredient ingredient = gson.fromJson(testIngredient, Ingredient.class);
        Ingredient expected = new Ingredient("Graham Cracker crumbs", "CUP", 2);
        Assert.assertEquals("Ingredients should be equal", expected, ingredient);
    }

    @Test
    public void testJsonIngredientConversionToListOfIngredients() throws JSONException{
        // get the ingredients built through JsonUtils utility method
        List<Ingredient> actualIngredients = JsonUtils.getJavaList(Ingredient.class, testIngredientsArray, "ingredients");

        // get expected results
        List<Ingredient> expectedIngredients = getTestIngredients();
        Assert.assertEquals("Lists should be identical", expectedIngredients, actualIngredients);

    }

    private static List<Ingredient> getTestIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient x = new Ingredient("Graham Cracker crumbs", "CUP", 2);
        Ingredient y = new Ingredient("unsalted butter, melted", "TBLSP", 6);
        ingredients.add(x);
        ingredients.add(y);
        return ingredients;
    }
}
