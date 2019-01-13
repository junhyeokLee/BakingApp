package com.junhyeoklee.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.widget.Toast;

import com.junhyeoklee.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    private Utility(){ }

    private static final String TAG = "Utility";
    public static void showToastMessage(Context context, String msg){
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static <Type> ArrayList<Type> fromListtoArrayList (List<Type> newList) {
        int size = newList.size();

        ArrayList<Type> arrayList = new ArrayList<>(size);

        arrayList.addAll(newList);

        return arrayList;

    }

    public static CircularProgressDrawable getCircleProgressDrawable(Context ctx, float strokeWidth, float centerRadius) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(ctx);
        circularProgressDrawable.setStrokeWidth(strokeWidth);
        circularProgressDrawable.setCenterRadius(centerRadius);
        int secondaryColor = ContextCompat.getColor(ctx, R.color.colorSecondary);
        int primaryDarkColor = ContextCompat.getColor(ctx, R.color.colorPrimaryDark);
        circularProgressDrawable.setColorSchemeColors(secondaryColor, primaryDarkColor);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }
}
