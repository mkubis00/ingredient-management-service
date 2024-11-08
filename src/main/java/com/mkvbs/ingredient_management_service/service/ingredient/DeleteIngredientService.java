package com.mkvbs.ingredient_management_service.service.ingredient;

import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteIngredientService {

    private final IngredientRepository ingredientRepository;

    public void deleteIngredient(UUID uuid) {
        ingredientRepository.deleteById(uuid);
    }

    public void deleteAllIngredients() {
        ingredientRepository.deleteAll();
    }
}
