package com.example.blps.repositories;

import com.example.blps.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long id);
    Optional<List<Recipe>> findByModerStatus(boolean moderStatus);
}

