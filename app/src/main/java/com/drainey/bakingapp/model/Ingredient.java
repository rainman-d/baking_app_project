package com.drainey.bakingapp.model;

/**
 * Created by david-rainey on 7/9/18.
 */

public class Ingredient {
    private String ingredient;
    private String measure;
    private int quantity;

    public Ingredient() {
    }

    public Ingredient(String ingredient, String measure, int quantity) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        result = 31 * result + getQuantity();
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
}
