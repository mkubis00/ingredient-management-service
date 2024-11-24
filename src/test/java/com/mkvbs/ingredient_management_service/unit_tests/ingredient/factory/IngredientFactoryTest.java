package com.mkvbs.ingredient_management_service.unit_tests.ingredient.factory;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.factory.ingredient.IngredientFactory;
import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientFactoryTest {

    Factory<Ingredient, IngredientRequest> ingredientFactory;

    private static final String NAME = "name";

    @BeforeEach
    void setUp() {
        ingredientFactory = new IngredientFactory();
    }

    @Test
    void testCreateMethod() {
        IngredientRequest ingredientRequest = new IngredientRequest(NAME, TypeOfQuantity.NONE, Allergen.CELERY);

        Ingredient ingredient = ingredientFactory.create(ingredientRequest);

        assertThat(ingredient.getName()).isEqualTo(NAME);
        assertThat(ingredient.getTypeOfQuantity()).isEqualTo(TypeOfQuantity.NONE);
        assertThat(ingredient.getAllergen()).isEqualTo(Allergen.CELERY);
    }
}