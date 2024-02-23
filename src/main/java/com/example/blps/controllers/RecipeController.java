package com.example.blps.controllers;

import com.example.blps.entities.Recipe;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("recipe")
@RequiredArgsConstructor
public class RecipeController {
    @GetMapping("show/draft")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String showDraft(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return "recipe id: " + id;
        }
        else {
            return "last recipe is ...";
        }
    }
    @GetMapping("show")
    public String show(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return "recipe id: " + id;
        }
        else {
            return "last recipe is ...";
        }
    }
    @GetMapping("rankings")
    public String rankings(@RequestParam(value = "id", required = true) Integer id) {
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
    public String add(@RequestBody Recipe recipe) {
        // saving recipe as draft
        return "recipe received";
    }
}
