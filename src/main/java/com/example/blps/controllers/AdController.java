package com.example.blps.controllers;

import com.example.blps.entities.User;
import com.example.blps.services.AdService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления рекламными блоками.
 * Предоставляет API для получения статической рекламы.
 */
@RestController
@RequestMapping("ad")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    /**
     * Возвращает статическую рекламу для указанного поста.
     * Метод доступен для всех пользователей без ограничений.
     *
     * @param postId ID поста, для которого запрашивается реклама.
     * @return ResponseEntity с рекламным контентом или сообщением об ошибке.
     */
    @GetMapping("static")
    @Operation(summary = "Доступно всем пользователям")
    public ResponseEntity<?> showDraft(@RequestParam(value = "post_id") Long postId) {
        return adService.getStaticAd(postId);
    }
    @GetMapping("balance")
    @Operation(summary = "Доступно авторизованным пользователям")
    public ResponseEntity<?> getMyBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return adService.getBalance(currentUser);
    }
}
