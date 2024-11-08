package com.mkvbs.ingredient_management_service.service.allergen;

import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.exception.EntityNotFoundException;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AllergenService {

    private final IngredientRepository ingredientRepository;

    public List<Allergen> getAllergensListByIngredientsIdList(List<UUID> uuids) {
        return ingredientRepository.findDistinctAllergensByIngredientIds(uuids);
    }

    public String getAllergenDescription(Allergen allergen) {
        return allergen.getAllergenDescription();
    }
}
