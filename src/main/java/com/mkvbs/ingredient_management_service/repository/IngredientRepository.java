package com.mkvbs.ingredient_management_service.repository;

import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    @Query("SELECT DISTINCT i.allergen FROM Ingredient i WHERE i.id IN :ingredientIds")
    public Optional<List<Allergen>> findDistinctAllergensByIngredientIds(@Param("ingredientIds") List<UUID> ingredientIds);

    @Query("SELECT DISTINCT i.id FROM Ingredient i WHERE i.name IN :IngredientNameList")
    public Optional<List<UUID>> findUuidsByIngredientName(@Param("IngredientNameList") List<String> ingredientNameList);

}
