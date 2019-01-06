package com.junhyeoklee.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by goandroid on 6/25/18.
 */

public class Ingredient implements Parcelable {

    /*
    {
        "quantity":2,
        "measure":"CUP",
        "ingredient":"Graham Cracker crumbs"
    },
     */

    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredient(double quantity,
                      String measure,
                      String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantiy) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

//    private double quantity;
//    private String measure;
//    private String ingredient;


    @Override
    public int describeContents() {
        return 0;
    }

    protected Ingredient(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
