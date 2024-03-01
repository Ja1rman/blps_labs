package com.example.blps.controllers;

import com.example.blps.entities.Recipe;
import com.example.blps.entities.Review;
import com.example.blps.entities.User;
import com.example.blps.model.ModerationResultDTO;
import com.example.blps.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    @GetMapping("show/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> showDraft(@RequestParam(value = "id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return recipeService.showDraft(id, currentUser);
    }

    @GetMapping("show")
    public ResponseEntity<?> show(@RequestParam(value = "id") Long id) {
        return recipeService.show(id);
    }

    @GetMapping("moderation/show")
    @Operation(summary = "Доступен только авторизованным пользователям (потом авторизованным модераторам)")
    public ResponseEntity<?> showModeration() {
        return recipeService.showModeration();
    }

    @PostMapping("moderation/send_result")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> sendModerationResult(@RequestBody ModerationResultDTO moderationResult) {
        return recipeService.sendModerationResult(moderationResult);
    }

    @GetMapping("reviews")
    public ResponseEntity<Optional<List<Review>>> reviews(@RequestParam(value = "id") Long id) {
        return recipeService.reviews(id);
    }

    @PostMapping("add/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> addDraft(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return recipeService.addDraft(recipe, currentUser);
    }
    @PostMapping("add")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<?> add(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return recipeService.add(recipe, currentUser);
    }
}
