package com.junhyeoklee.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;


import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Ingredient;

import java.util.ArrayList;


/**
 * Created by goandroid on 9/6/18.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "IngredientWidgetProvid";

    private static String recipeName;
    public static ArrayList<Ingredient> ingredientArrayList;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                               int appWidgetId, String name, ArrayList<Ingredient> ingredients) {

        recipeName = name;
        ingredientArrayList = ingredients;

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if(recipeName == null){
            Log.d(TAG, "updateAppWidget: do nothing for recipeName = null");
        } else {

            remoteViews.setTextViewText(R.id.tv_widget_header, recipeName);

            Intent intent = new Intent(context, IngredientWidgetService.class);

            Bundle bundle = new Bundle();
            bundle.putString("name", recipeName);
            bundle.putParcelableArrayList("ingredients", ingredientArrayList);
            intent.putExtra("data", bundle);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_view);
            remoteViews.setRemoteAdapter(R.id.list_view, intent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }


    public static void updateIngredientWidgets(Context context,
                                               AppWidgetManager appWidgetManager,
                                               int[] appWidgetIds,
                                               String name,
                                               ArrayList<Ingredient> ingredientArrayList){

        for( int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId, name, ingredientArrayList);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // start the intent service update widget action,
        // the service takes care of updating the widgets UI
        WidgetUpdateService.startActionUpdateWidget(context, recipeName, ingredientArrayList);

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
    // Perform any action when an AppWidget for this provider is instantiated

    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }
}
