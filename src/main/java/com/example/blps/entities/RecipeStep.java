package com.example.blps.entities;

import java.util.List;

public class RecipeStep {
    private String stepText;
    private List<String> stepPhotos;

    // Геттеры и сеттеры
    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public List<String> getStepPhotos() {
        return stepPhotos;
    }

    public void setStepPhotos(List<String> stepPhotos) {
        this.stepPhotos = stepPhotos;
    }
}
