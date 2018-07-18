package com.drainey.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david-rainey on 7/9/18.
 */

public class Recipe implements Parcelable{
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<RecipeStep> steps;
    private int servings;
    private String image;

    public Recipe() {
    }

    public Recipe(Parcel in){
        ReadFromParcel(in);
    }

    public Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Recipe createFromParcel(Parcel in){
            return new Recipe(in);
        }

        public Recipe[] newArray(int size){
            return new Recipe[size];
        }
    };

    private void ReadFromParcel(Parcel in){
        id = in.readInt();
        name = in.readString();
        ingredients = new ArrayList<Ingredient>();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        steps = new ArrayList<RecipeStep>();
        in.readTypedList(steps, RecipeStep.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
