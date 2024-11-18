package com.mkvbs.ingredient_management_service.api.ingredient;

import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import com.mkvbs.ingredient_management_service.service.ingredient.PostIngredientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PostIngredientController {

    private final PostIngredientService ingredientService;

    /**
     * Api method used to create an instance of ingredient in database.
     * @param ingredientRequest provides information about new ingredient.
     * @return saved ingredient
     */
    @PostMapping(ApiPath.INGREDIENT.CREATE_V1)
    public ResponseEntity<IngredientResponse> postIngredient(@Valid @RequestBody IngredientRequest ingredientRequest) {
        IngredientResponse savedIngredient = ingredientService.saveIngredient(ingredientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient);
    }

    /**
     * Api method used to create instances of ingredients in database.
     * Typically used to created instances of ingredients during creation of a recipe.
     * @param ingredientsRequest provides information about new ingredients.
     * @return saved ingredients
     */
    @PostMapping(ApiPath.INGREDIENT.CREATE_LIST_V1)
    public ResponseEntity<List<IngredientResponse>> postIngredientsList(@RequestBody List<IngredientRequest> ingredientsRequest) {
        List<IngredientResponse> savedIngredientList = ingredientService.saveIngredientsList(ingredientsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredientList);
    }
}
