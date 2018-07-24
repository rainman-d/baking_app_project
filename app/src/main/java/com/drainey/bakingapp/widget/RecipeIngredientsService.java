package com.drainey.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.drainey.bakingapp.R;
import com.drainey.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecipeIngredientsService extends IntentService {
    private static final String ACTION_UPDATE_INGREDIENTS =
            "com.drainey.bakingapp.widget.action.update_ingredients";
    private static final String INGREDIENTS_LIST = "com.drainey.bakingapp.widget.extra.INGREDIENTS_LIST";


    public RecipeIngredientsService() {
        super("RecipeIngredientsService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateIngredients(Context context) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("food", "G", 2.5));
        Intent intent = new Intent(context, RecipeIngredientsService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putParcelableArrayListExtra(INGREDIENTS_LIST, new ArrayList<>(ingredients));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                final List<Ingredient> ingredients = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
                handleActionUpdateIngredientsList(ingredients);
            }
        }
    }

    /**
     * Handle action UpdateIngredientsList in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateIngredientsList(List<Ingredient> ingredients) {
        int imgRes = R.drawable.food;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_baking);
        BakingWidgetProvider.updateRecipeWidgets(this, appWidgetManager, imgRes, appWidgetIds);
    }

}
