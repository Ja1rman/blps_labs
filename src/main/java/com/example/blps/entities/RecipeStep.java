package com.example.blps.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecipeStep {
    // Геттеры и сеттеры
    private String stepText;
    private List<String> stepPhotos;

}
