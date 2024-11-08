package com.mkvbs.ingredient_management_service.service.ingredient;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.factory.provider.FactoryProvider;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.model.exception.EntityNotFoundException;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetIngredientService {

    private final IngredientRepository ingredientRepository;
    private final Factory<IngredientResponse, Ingredient> ingredientResponseFactory;

    public GetIngredientService(IngredientRepository ingredientRepository, FactoryProvider defaultFactoryProvider) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientResponseFactory = defaultFactoryProvider.getIngredientResponseFactory();
    }

    public List<IngredientResponse> getIngredientsByUuidList(List<UUID> uuids) {
        List<Ingredient> ingredientList = ingredientRepository.findAllById(uuids);
        return ingredientList.stream().map(ingredientResponseFactory::create).toList();
    }

    public IngredientResponse getIngredientById(UUID uuid) {
        Ingredient ingredientToReturn = ingredientRepository.findById(uuid).orElseThrow(()-> new EntityNotFoundException("Recipe with id: " + uuid + " not found."));
        return ingredientResponseFactory.create(ingredientToReturn);
    }

    public UUID getIngredientByName(String name) {
        return ingredientRepository.findUuidByIngredientName(name).orElseThrow(()-> new EntityNotFoundException("Recipe with name: " + name + " not found."));
    }

    public List<IngredientResponse> getAllIngredients() {
        List<Ingredient> ingredientsToReturn = ingredientRepository.findAll();
        return ingredientsToReturn.stream().map(ingredientResponseFactory::create).toList();
    }

    public List<UUID> getIngredientsWithNames(List<String> names) {
        return ingredientRepository.findUuidsByIngredientName(names).orElseThrow();
    }
}
