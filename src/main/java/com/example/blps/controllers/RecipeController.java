package com.example.blps.controllers;

import com.example.blps.entities.Recipe;
import com.example.blps.entities.Review;
import com.example.blps.entities.User;
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
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            boolean isDraft = recipeOptional.get().hasNullFieldsForDraft();
            if (isDraft){
                return new ResponseEntity<>("you can't", HttpStatus.CREATED);
            } else {
                return ResponseEntity.ok(recipeOptional.get());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("reviews")
    public ResponseEntity<Optional<List<Review>>> reviews(@RequestParam(value = "id") Long id) {
        Optional<List<Review>> reviews = reviewRepository.findByEntityId(id);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }
    @PostMapping("add/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<Recipe> addDraft(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        recipe.setOwnerId(currentUser.getId());
        recipe.setIsDraft(true);
        Recipe savedDraft = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedDraft, HttpStatus.CREATED);
    }
    @PostMapping("add")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        recipe.setOwnerId(currentUser.getId());
        boolean isDraft = recipe.hasNullFieldsForDraft();
        recipe.setIsDraft(isDraft);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }
}
