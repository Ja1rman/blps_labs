package com.example.blps.controllers;

import com.example.blps.entities.Recipe;
import com.example.blps.repositories.RecipeRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;
    @GetMapping("show/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String showDraft(@RequestParam(value = "id", required = true) Long id) {
        return "recipe id: " + id;
    }
    @GetMapping("show")
    public ResponseEntity<?> show(@RequestParam(value = "id", required = true) Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            return ResponseEntity.ok(recipeOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("rankings")
    public String rankings(@RequestParam(value = "id", required = true) Long id) {
        if (id != null) {
            return "rankings for id: " + id;
        }
        else {
            return "rankings for last is ...";
        }
    }
    @PostMapping("add/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String addDraft(@RequestBody Recipe recipe) {
        // saving recipe as draft
        return "Draft of recipe received";
    }
    @PostMapping("add")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }
}
