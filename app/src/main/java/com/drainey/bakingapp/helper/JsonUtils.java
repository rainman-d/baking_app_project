package com.drainey.bakingapp.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david-rainey on 7/9/18.
 */

public class JsonUtils {
    public static <T> List<T> getListFromJsonArray(Class<T> type, String jsonString, String arrayName){
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(jsonString);
        JsonArray jsonArray = jo.getAsJsonArray(arrayName);
        Type listType = TypeToken.getParameterized(List.class, type).getType();
        List<T> objectList = new Gson().fromJson(jsonArray, listType);
        return objectList;
    }

    public static <T> List<T> getListFromJsonArray(Class<T> type, JsonArray jArray, String arrName){
        Type listType = TypeToken.getParameterized(List.class, type).getType();
        List<T> list = new Gson().fromJson(jArray, listType);
        return list;
    }

    public static List<Recipe> getRecipeList(String jsonString){
        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(jsonString, Recipe[].class);
        List<Recipe> objectList = Arrays.asList(recipes);
        return objectList;
    }

    public static String getRecipeList(Context context){
        String output = "";
        try{
            Resources res = context.getResources();
            InputStream is = res.openRawResource(R.raw.json_test);
            byte[] b = new byte[is.available()];
            is.read(b);
            output = new String(b);
        } catch (Exception e){
            Log.e(JsonUtils.class.getSimpleName(), "Error getting file data", e);
        }

        return output;

    }
}
