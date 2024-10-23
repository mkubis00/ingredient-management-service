package com.mkvbs.ingredient_management_service.factory.provider;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.factory.ingredient.IngredientFactory;
import com.mkvbs.ingredient_management_service.factory.ingredient.IngredientResponseFactory;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import org.springframework.stereotype.Component;

@Component
public class DefaultFactoryProvider implements FactoryProvider {

    private final Factory<Ingredient, IngredientRequest> ingredientFactory;
    private final Factory<IngredientResponse, Ingredient> ingredientResponseFactory;

    public DefaultFactoryProvider() {
        this.ingredientFactory = new IngredientFactory();
        this.ingredientResponseFactory = new IngredientResponseFactory();
    }

    @Override
    public Factory<Ingredient, IngredientRequest> getIngredientFactory() {
        return ingredientFactory;
    }

    @Override
    public Factory<IngredientResponse, Ingredient> getIngredientResponseFactory() {
        return ingredientResponseFactory;
    }
}
