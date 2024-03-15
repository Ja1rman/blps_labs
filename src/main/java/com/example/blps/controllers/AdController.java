package com.example.blps.controllers;

import com.example.blps.entities.User;
import com.example.blps.services.AdService;
import com.example.blps.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ad")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @GetMapping("static")
    @Operation(summary = "Доступно всем пользователям")
    public ResponseEntity<?> showDraft(@RequestParam(value = "post_id") Long post_id) {
        return adService.getStaticAd(post_id);
    }
}
