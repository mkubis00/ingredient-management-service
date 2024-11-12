package com.mkvbs.ingredient_management_service.model.api;

import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientRequest {

    @NotNull(message = "Ingredient name cannot be null")
    @Size(min = 3, max = 50, message = "Ingredient name must be between 3 and 50 characters")
    private final String name;
    @NotNull(message = "Type of quantity cannot be null")
    private final TypeOfQuantity typeOfQuantity;
    private final Allergen allergen;
}
