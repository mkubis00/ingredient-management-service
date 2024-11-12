package com.mkvbs.ingredient_management_service.factory.ingredient;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import org.springframework.stereotype.Component;

@Component
public class IngredientResponseFactory implements Factory<IngredientResponse, Ingredient> {
    @Override
    public IngredientResponse create(Ingredient resource) {
        return new IngredientResponse(
                resource.getId(),
                resource.getName(),
                resource.getTypeOfQuantity(),
                resource.getAllergen());
    }
}
