package com.junhyeoklee.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.junhyeoklee.bakingapp.data.model.Ingredient;

import java.util.ArrayList;


/**
 * Created by goandroid on 9/6/18.
 */

public class WidgetUpdateService extends IntentService {


    private static final String TAG = "WidgetUpdateService";
    public static final String NAME_EXTRA = "recipe_name";
    public static final String INGREDIENT_List_EXTRA = "ingredients";
    public static final String ACTION_UPDATE_WIDGET = "com.eightmin4mile.goandroid.bakingapp.widget.updateWidage";

    public WidgetUpdateService(){
        super("WidgetUpdateService");
    }

    // method to create intent for IntentService
    public static void startActionUpdateWidget(Context context,
                                    String recipeName,
                                    ArrayList<Ingredient> ingredientList ){
        Intent i = new Intent(context, WidgetUpdateService.class);


        i.putExtra(NAME_EXTRA, recipeName);
        i.putParcelableArrayListExtra(INGREDIENT_List_EXTRA, ingredientList);

        i.setAction(ACTION_UPDATE_WIDGET);
        context.startService(i);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // get data from intent
        // initiate widget update method

        if(intent != null){
            final String action = intent.getAction();
            if(ACTION_UPDATE_WIDGET.equals(action)){
                if(intent.hasExtra(NAME_EXTRA) && intent.hasExtra(INGREDIENT_List_EXTRA)){
                    String name = intent.getStringExtra(NAME_EXTRA);
                    ArrayList<Ingredient> ingredients =
                            intent.getParcelableArrayListExtra(INGREDIENT_List_EXTRA);
                    updateWidget(name, ingredients);
                }
            }
        }
    }

    private void updateWidget(String name, ArrayList<Ingredient> ingredientList){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, IngredientWidgetProvider.class));

        IngredientWidgetProvider.updateIngredientWidgets(this,
                appWidgetManager, appWidgetsIds, name, ingredientList);
    }
}
