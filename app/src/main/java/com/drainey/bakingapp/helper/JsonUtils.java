package com.drainey.bakingapp.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
}
