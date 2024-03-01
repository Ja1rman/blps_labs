package com.example.blps.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDTO {
    private Long recipeId;
    private Integer rating;
    private String comment;

    public boolean hasNulls() {
        return this.recipeId == null || this.rating == null || this.comment == null;
    }
}
