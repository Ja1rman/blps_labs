package com.example.blps.services;

import com.example.blps.entities.Recipe;
import com.example.blps.entities.Review;
import com.example.blps.entities.User;
import com.example.blps.model.ModerationResultDTO;
import com.example.blps.repositories.RecipeRepository;
import com.example.blps.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;

    public ResponseEntity<?> showDraft(Long id, User currentUser) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(recipe.getOwnerId(), currentUser.getId())) {
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(recipe);
    }

    public ResponseEntity<?> show(Long id) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            boolean isDraft = recipe.getIsDraft();
            boolean moderStatus = recipe.getModerStatus();
            if (isDraft || moderStatus) {
                return new ResponseEntity<>("а вот нельзя. рецепт ещё не готов", HttpStatus.CREATED);
            } else {
                recipe.addView();
                recipeRepository.save(recipe);
                return ResponseEntity.ok(recipe);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> showModeration() {
        Optional<List<Recipe>> recipeOptional = recipeRepository.findByModerStatus(true);
        return ResponseEntity.ok(recipeOptional);
    }

    public ResponseEntity<?> sendModerationResult(ModerationResultDTO moderationResult) {
        Recipe recipe = recipeRepository.findById(moderationResult.getId()).orElse(null);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        recipe.setModerStatus(false);
        recipe.setModerComment(moderationResult.getModerComment());
        recipe.setIsDraft(!moderationResult.getModerResult());
        recipeRepository.save(recipe);
        return ResponseEntity.ok("Статус обновлён");
    }

    public ResponseEntity<Optional<List<Review>>> reviews(Long id) {
        Optional<List<Review>> reviews = reviewRepository.findByEntityId(id);
        return ResponseEntity.ok(reviews);
    }

    private String checkAccess(Recipe recipe, User currentUser){
        if (recipe.getId() != null) {
            Recipe oldRecipe = recipeRepository.findById(recipe.getId()).orElse(null);
            if (oldRecipe == null) {
                return "not found";
            }
            if (!Objects.equals(oldRecipe.getOwnerId(), currentUser.getId())) {
                return "Доступ запрещен";
            }
        }
        recipe.setOwnerId(currentUser.getId());
        recipe.setViews(0);
        return "true";
    }

    public ResponseEntity<?> addDraft(Recipe recipe, User currentUser) {
        String response = checkAccess(recipe, currentUser);
        if (response.equals("not found")) {
            return ResponseEntity.notFound().build();
        } else if (response.equals("Доступ запрещен")) {
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        recipe.setIsDraft(true);
        recipe.setModerStatus(false);
        Recipe savedDraft = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedDraft, HttpStatus.CREATED);
    }

    public ResponseEntity<?> add(Recipe recipe, User currentUser) {
        String response = checkAccess(recipe, currentUser);
        if (response.equals("not found")) {
            return ResponseEntity.notFound().build();
        } else if (response.equals("Доступ запрещен")) {
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        boolean isDraft = recipe.hasNullFieldsForDraft();
        recipe.setIsDraft(isDraft);
        if (!recipe.getIsDraft()) {
            recipe.setModerStatus(true);
        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }
}
