package com.drainey.bakingapp.helper;

import android.net.Uri;
import android.util.Log;

import com.drainey.bakingapp.model.Recipe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david-rainey on 7/11/18.
 */

public class BakingDataUtils {
    private static final String LOG_TAG = BakingDataUtils.class.getSimpleName();
    private static final String BAKING_DATA_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL getBakingAppDataUrl(){
        Uri uri = Uri.parse(BAKING_DATA_URL);
        URL url = convertUriToUrl(uri);
        return url;
    }

    public static URL convertUriToUrl(Uri uri){
        URL returnUrl = null;
        try{
            returnUrl = new URL(uri.toString());
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error creating URL from uri supplied: " + uri.toString(), e);
        }

        return returnUrl;
    }
}
