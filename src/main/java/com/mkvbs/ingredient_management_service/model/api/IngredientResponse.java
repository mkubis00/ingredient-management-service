package com.mkvbs.ingredient_management_service.model.api;

import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class IngredientResponse {

    private final UUID id;
    private final String name;
    private final TypeOfQuantity typeOfQuantity;
    private final Allergen allergen;
}
