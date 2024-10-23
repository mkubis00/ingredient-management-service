package com.mkvbs.ingredient_management_service.service;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.factory.provider.FactoryProvider;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.model.exception.EntityNotFoundException;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final Factory<Ingredient, IngredientRequest> ingredientFactory;
    private final Factory<IngredientResponse, Ingredient> ingredientResponseFactory;

    public IngredientService(IngredientRepository ingredientRepository, FactoryProvider defaultFactoryProvider) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientFactory = defaultFactoryProvider.getIngredientFactory();
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

    public List<IngredientResponse> findAllIngredients() {
        List<Ingredient> ingredientsToReturn = ingredientRepository.findAll();
        return ingredientsToReturn.stream().map(ingredientResponseFactory::create).toList();
    }

    public List<UUID> findIngredientsWithNames(List<String> names) {
        return ingredientRepository.findUuidsByIngredientName(names).orElseThrow();
    }

    public IngredientResponse saveIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientFactory.create(ingredientRequest);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientResponseFactory.create(savedIngredient);
    }

    public List<IngredientResponse> saveIngredientsList(List<IngredientRequest> ingredientList) {
        List<Ingredient> ingredientsToSave = ingredientList.stream().map(ingredientFactory::create).toList();
        List<Ingredient> savedIngredients = ingredientRepository.saveAll(ingredientsToSave);
        return savedIngredients.stream().map(ingredientResponseFactory::create).toList();
    }

    public void deleteIngredient(UUID uuid) {
        ingredientRepository.deleteById(uuid);
    }

    public void deleteAllIngredients() {
        ingredientRepository.deleteAll();
    }
}
