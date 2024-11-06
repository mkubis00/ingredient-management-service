package com.mkvbs.ingredient_management_service.api.ingredient;

import com.mkvbs.ingredient_management_service.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
@Validated
public class DeleteIngredientController {

    private final IngredientService ingredientService;

    /**
     * Api method used to delete an instance of ingredient.
     * @param uuid of the ingredient to delete
     * @return void
     */
    @DeleteMapping("/v1/delete_ingredient/{uuid}")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable UUID uuid) {
        ingredientService.deleteIngredient(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/v1/delete_all_ingredients")
    public ResponseEntity<Void> deleteAllIngredients() {
        ingredientService.deleteAllIngredients();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}