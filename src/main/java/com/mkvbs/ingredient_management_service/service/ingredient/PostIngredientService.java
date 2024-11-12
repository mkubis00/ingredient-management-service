package com.mkvbs.ingredient_management_service.service.ingredient;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.model.exception.EntityAlreadyExistsException;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostIngredientService {

    private final IngredientRepository ingredientRepository;
    private final Factory<Ingredient, IngredientRequest> ingredientFactory;
    private final Factory<IngredientResponse, Ingredient> ingredientResponseFactory;

    public IngredientResponse saveIngredient(IngredientRequest ingredientRequest) {
        if (isRecipeExists(ingredientRequest.getName())) {
            throw new EntityAlreadyExistsException("Ingredient with name " + ingredientRequest.getName() + " already exists.");
        }
        Ingredient ingredient = ingredientFactory.create(ingredientRequest);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientResponseFactory.create(savedIngredient);
    }

    public List<IngredientResponse> saveIngredientsList(List<IngredientRequest> ingredientList) {
        List<Ingredient> ingredientsToCheck = ingredientList.stream().map(ingredientFactory::create).toList();
        List<Ingredient> ingredientsToSave = ingredientsToCheck.stream().filter(ingredient -> !isRecipeExists(ingredient.getName())).toList();
        List<Ingredient> savedIngredients = ingredientRepository.saveAll(ingredientsToSave);
        return savedIngredients.stream().map(ingredientResponseFactory::create).toList();
    }

    private boolean isRecipeExists(String name) {
        return ingredientRepository.findByName(name).isPresent();
    }
}
