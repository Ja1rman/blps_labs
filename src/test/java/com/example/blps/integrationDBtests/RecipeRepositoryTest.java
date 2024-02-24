package com.example.blps.integrationDBtests;

import com.example.blps.entities.Ingredient;
import com.example.blps.entities.Recipe;
import com.example.blps.entities.RecipeStep;
import com.example.blps.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testSaveAndFindById() {
        Recipe recipe = Recipe.builder()
                .ownerId(1L)
                .mainPhotoUrl("example.com/photo.jpg")
                .category("Dessert")
                .timeToCook(30)
                .isDraft(false)
                .numberOfServings(4)
                .tags(Arrays.asList("sweet", "summer"))
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Recipe> foundRecipe = recipeRepository.findById(savedRecipe.getId());

        assertThat(foundRecipe).isPresent();
        assertThat(foundRecipe.get().getOwnerId()).isEqualTo(1L);
        assertThat(foundRecipe.get().getTags()).contains("sweet", "summer");
    }

    @Test
    public void testRecipeDraftValidation() {
        Recipe draftRecipe = Recipe.builder()
                .ownerId(2L)
                .isDraft(true)
                .build();
        assertThat(draftRecipe.hasNullFieldsForDraft()).isTrue();

        draftRecipe.setMainPhotoUrl("example.com/photo.jpg");
        draftRecipe.setCategory("Breakfast");
        draftRecipe.setTimeToCook(15);
        draftRecipe.setNumberOfServings(2);
        draftRecipe.setSteps(Collections.singletonList(new RecipeStep()));
        draftRecipe.setIngredients(Collections.singletonList(new Ingredient()));
        draftRecipe.setTags(Collections.singletonList("quick"));

        assertThat(draftRecipe.hasNullFieldsForDraft()).isFalse();
    }

    @Test
    public void testDeleteRecipe() {
        Recipe recipe = Recipe.builder()
                .ownerId(3L)
                .mainPhotoUrl("example.com/photo.jpg")
                .category("Lunch")
                .timeToCook(45)
                .isDraft(false)
                .numberOfServings(6)
                .tags(Arrays.asList("healthy", "vegan"))
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);

        recipeRepository.deleteById(savedRecipe.getId());

        Optional<Recipe> foundRecipe = recipeRepository.findById(savedRecipe.getId());
        assertThat(foundRecipe).isNotPresent();
    }
}
