package com.mkvbs.ingredient_management_service.factory.ingredient;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;

public class IngredientFactory implements Factory<Ingredient, IngredientRequest> {

    @Override
    public Ingredient create(IngredientRequest resource) {
        return new Ingredient(resource.getName(),
                resource.getTypeOfQuantity(),
                resource.getAllergen());
    }
}
