package com.example.blps.controllers;

import com.example.blps.entities.Recipe;
import com.example.blps.entities.Review;
import com.example.blps.entities.User;
import com.example.blps.model.ModerationResultDTO;
import com.example.blps.repositories.RecipeRepository;
import com.example.blps.repositories.ReviewRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;
    @GetMapping("show/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> showDraft(@RequestParam(value = "id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        // Проверяем, является ли текущий пользователь владельцем рецепта
        if (! (Objects.equals(recipe.getOwnerId(), currentUser.getId()))) {
            return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
        }
        // Если проверки пройдены, возвращаем данные рецепта
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("show")
    public ResponseEntity<?> show(@RequestParam(value = "id") Long id) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            boolean isDraft = recipe.getIsDraft();
            boolean moderStatus = recipe.getModerStatus();
            if (isDraft || moderStatus){
                return new ResponseEntity<>("you can't", HttpStatus.CREATED);
            } else {
                recipe.addView();
                recipeRepository.save(recipe);
                return ResponseEntity.ok(recipe);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("moderation/show")
    @Operation(summary = "Доступен только авторизованным пользователям (потом авторизованным модераторам)")
    public ResponseEntity<?> showModeration() {
        Optional<List<Recipe>> recipeOptional = recipeRepository.findByModerStatus(true);
        return ResponseEntity.ok(recipeOptional);
    }

    @PostMapping("moderation/send_result")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> sendModerationResult(@RequestBody ModerationResultDTO moderationResult) {
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

    @GetMapping("reviews")
    public ResponseEntity<Optional<List<Review>>> reviews(@RequestParam(value = "id") Long id) {
        Optional<List<Review>> reviews = reviewRepository.findByEntityId(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("add/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> addDraft(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (recipe.getId() != null) {
            Recipe oldRecipe = recipeRepository.findById(recipe.getId()).orElse(null);
            if (oldRecipe == null) {
                return ResponseEntity.notFound().build();
            }
            // Проверяем, является ли текущий пользователь владельцем рецепта
            if (! (Objects.equals(oldRecipe.getOwnerId(), currentUser.getId()))) {
                return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
            }
        }

        recipe.setOwnerId(currentUser.getId());
        recipe.setViews(0);
        recipe.setIsDraft(true);
        recipe.setModerStatus(false);
        Recipe savedDraft = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedDraft, HttpStatus.CREATED);
    }
    @PostMapping("add")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> add(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (recipe.getId() != null) {
            Recipe oldRecipe = recipeRepository.findById(recipe.getId()).orElse(null);
            if (oldRecipe == null) {
                return ResponseEntity.notFound().build();
            }
            // Проверяем, является ли текущий пользователь владельцем рецепта
            if (! (Objects.equals(oldRecipe.getOwnerId(), currentUser.getId()))) {
                return new ResponseEntity<>("Доступ запрещен", HttpStatus.FORBIDDEN);
            }
        }

        recipe.setOwnerId(currentUser.getId());
        recipe.setViews(0);
        boolean isDraft = recipe.hasNullFieldsForDraft();
        recipe.setIsDraft(isDraft);
        if (!recipe.getIsDraft()) {
            recipe.setModerStatus(true);
        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }
}
