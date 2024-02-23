package com.example.blps.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ingredient {
    // Геттеры и сеттеры
    private String name;
    private double weight; // Вес может быть в граммах, миллилитрах и т.д.
    private int quantity; // Количество, если применимо (например, 2 яблока)

}
