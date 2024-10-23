package com.mkvbs.ingredient_management_service.factory.provider;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;

public interface FactoryProvider {

    Factory<Ingredient, IngredientRequest> getIngredientFactory();

    Factory<IngredientResponse, Ingredient> getIngredientResponseFactory();
}
