package com.mkvbs.ingredient_management_service.factory.ingredient;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientResponseFactoryTest {

    private Factory<IngredientResponse, Ingredient> factory;

    private final static String NAME = "name";
    private final static UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        factory = new IngredientResponseFactory();
    }

    @Test
    void testCreateMethod() {
        Ingredient ingredient = new Ingredient(ID, NAME, TypeOfQuantity.NONE, Allergen.CELERY);

        IngredientResponse ingredientResponse = factory.create(ingredient);

        assertThat(ingredientResponse.getId()).isEqualTo(ID);
        assertThat(ingredientResponse.getName()).isEqualTo(NAME);
        assertThat(ingredientResponse.getTypeOfQuantity()).isEqualTo(TypeOfQuantity.NONE);
        assertThat(ingredientResponse.getAllergen()).isEqualTo(Allergen.CELERY);
    }
}