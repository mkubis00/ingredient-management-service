package com.mkvbs.ingredient_management_service.api;

import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.service.IngredientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * Api method used to create an instance of ingredient in database.
     * @param ingredientRequest provides information about new ingredient.
     * @return saved ingredient
     */
    @PostMapping("/v1/post_ingredient")
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
    @PostMapping("/v1/post_ingredients")
    public ResponseEntity<List<IngredientResponse>> postIngredientsList(@RequestBody List<IngredientRequest> ingredientsRequest) {
        List<IngredientResponse> savedIngredientList = ingredientService.saveIngredientsList(ingredientsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredientList);
    }

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

    /**
     * Api method used to get ingredients object from uuid list.
     * Typically used to fetch ingredients for recipe which holds only ingredients ids.
     * @param uuidList list of ingredients id
     * @return list of ingredients
     */
    @GetMapping("/v1/get_ingredients_from_uuid_list/{uuidList}")
    public ResponseEntity<List<IngredientResponse>> getIngredientsByIdList(@PathVariable List<UUID> uuidList) {
        List<IngredientResponse> ingredients = ingredientService.getIngredientsByUuidList(uuidList);
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    /**
     * Api method used to fetch ingredient object by its id.
     * @param uuid ingredient to fetch
     * @return ingredient
     */
    @GetMapping("/v1/get_ingredient/{uuid}")
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable UUID uuid) {
        IngredientResponse ingredient = ingredientService.getIngredientById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ingredient);
    }

    /**
     * Api method used to fetch all ingredients store in database.
     * Use only for test purposes.
     * @return list of all ingredients in database.
     */
    @GetMapping("/v1/get_all_ingredient")
    public ResponseEntity<List<IngredientResponse>> getAllIngredients() {
        List<IngredientResponse> ingredients = ingredientService.findAllIngredients();
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    /**
     * Api method that returns ids list of the recipes which names matches passed name list.
     * Typically used to return ids of the ingredients which are searched by user.
     * @param ingredientNames names of ingredients
     * @return ingredients ids
     */
    @GetMapping("/v1/get_ids_from_ingredient_name_list/{ingredientNames}")
    public ResponseEntity<List<UUID>> getIngredientsByIngredientsNames(@PathVariable List<String> ingredientNames) {
        List<UUID> ingredientsUuids = ingredientService.findIngredientsWithNames(ingredientNames);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientsUuids);
    }

    @GetMapping("/v1/get_ingredient_by_name/{ingredientName}")
    public ResponseEntity<UUID> getIngredienIdtByName(@PathVariable String ingredientName) {
        UUID ingredientIdWithName = ingredientService.getIngredientByName(ingredientName);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientIdWithName);
    }
}
