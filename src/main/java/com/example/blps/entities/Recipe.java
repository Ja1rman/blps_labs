package com.example.blps.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Recipe {
    private List<RecipeStep> steps;
    private String mainPhotoUrl;
    private List<String> tags;
    private String category;
    private Integer timeToCook;
    private Integer numberOfServings;
    // Геттеры и сеттеры
    private List<Ingredient> ingredients;

}
