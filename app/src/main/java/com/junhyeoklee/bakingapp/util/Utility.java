package com.junhyeoklee.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
}
