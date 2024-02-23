package com.example.blps.entities;

import java.util.List;

public class Recipe {
    private List<RecipeStep> steps;
    private String mainPhotoUrl;
    private List<String> tags;
    private String category;
    private Integer timeToCook;
    private Integer numberOfServings;
    private List<Ingredient> ingredients;
    // Геттеры и сеттеры
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

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTimeToCook() {
        return timeToCook;
    }

    public void setTimeToCook(Integer timeToCook) {
        this.timeToCook = timeToCook;
    }

    public Integer getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(Integer numberOfServings) {
        this.numberOfServings = numberOfServings;
    }
}
