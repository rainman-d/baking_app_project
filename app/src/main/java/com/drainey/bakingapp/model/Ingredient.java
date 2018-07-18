package com.drainey.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by david-rainey on 7/9/18.
 */

public class Ingredient implements Parcelable{
    private String ingredient;
    private String measure;
    private double quantity;

    public Ingredient() {
    }

    public Ingredient(Parcel in){
        ReadFromParcel(in);
    }

    public Ingredient(String ingredient, String measure, double quantity) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (getQuantity() != that.getQuantity()) return false;
        if (getIngredient() != null ? !getIngredient().equals(that.getIngredient()) : that.getIngredient() != null)
            return false;
        return getMeasure() != null ? getMeasure().equals(that.getMeasure()) : that.getMeasure() == null;
    }

    @Override
    public int hashCode() {
        int result = getIngredient() != null ? getIngredient().hashCode() : 0;
        result = 31 * result + (getMeasure() != null ? getMeasure().hashCode() : 0);
//        result = 31 * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredient='" + ingredient + '\'' +
                ", measure='" + measure + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ingredient);
        parcel.writeString(measure);
        parcel.writeDouble(quantity);
    }

    private void ReadFromParcel(Parcel in){
        ingredient = in.readString();
        measure = in.readString();
        quantity = in.readDouble();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Ingredient createFromParcel(Parcel in){
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size){
            return new Ingredient[size];
        }
    };
}
