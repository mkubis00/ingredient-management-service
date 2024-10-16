package com.mkvbs.ingredient_management_service.service;

import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.exception.EntityNotFoundException;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredientsByUuidList(List<UUID> uuids) {
        return ingredientRepository.findAllById(uuids);
    }

    public Ingredient findIngredientById(UUID uuid) {
        return ingredientRepository.findById(uuid).orElseThrow(()-> new EntityNotFoundException("Recipe with id: " + uuid + " not found."));
    }

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    public List<UUID> findIngredientsWithNames(List<String> names) {
        return ingredientRepository.findUuidsByIngredientName(names).orElseThrow();
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> saveIngredientsList(List<Ingredient> ingredientList) {
        return ingredientRepository.saveAll(ingredientList);
    }

    public void deleteIngredient(UUID uuid) {
        ingredientRepository.deleteById(uuid);
    }

    public void deleteAllIngredients() {
        ingredientRepository.deleteAll();
    }
}
