package com.example.blps.entities;

public class Ingredient {
    private String name;
    private double weight; // Вес может быть в граммах, миллилитрах и т.д.
    private int quantity; // Количество, если применимо (например, 2 яблока)

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
