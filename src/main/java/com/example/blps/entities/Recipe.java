package com.example.blps.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes")
public class Recipe {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<RecipeStep> steps;
    private String mainPhotoUrl;
    @ElementCollection
    private List<String> tags;
    private String category;
    private Integer timeToCook;
    private Boolean isDraft;
    private Integer numberOfServings;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients;
    public boolean hasNullFieldsForDraft() {
        if (this.mainPhotoUrl == null || this.category == null || this.timeToCook == null || this.numberOfServings == null) {
            return true;
        }
        return this.steps == null || this.steps.isEmpty() || this.ingredients == null || this.ingredients.isEmpty() || this.tags == null || this.tags.isEmpty();
    }
}
