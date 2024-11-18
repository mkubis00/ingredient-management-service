package com.mkvbs.ingredient_management_service.api.ingredient;

import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import com.mkvbs.ingredient_management_service.service.ingredient.DeleteIngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class DeleteIngredientController {

    private final DeleteIngredientService ingredientService;

    /**
     * Api method used to delete an instance of ingredient.
     * @param id of the ingredient to delete
     * @return void
     */
    @DeleteMapping(ApiPath.INGREDIENT.DELETE_V1)
    public ResponseEntity<Void> deleteIngredientById(@PathVariable UUID id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Api method used to delete all ingredients.
     * @return void
     */
    @DeleteMapping(ApiPath.INGREDIENT.DELETE_ALL_V1)
    public ResponseEntity<Void> deleteAllIngredients() {
        ingredientService.deleteAllIngredients();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
