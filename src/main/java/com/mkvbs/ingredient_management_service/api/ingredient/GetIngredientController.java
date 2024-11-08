package com.mkvbs.ingredient_management_service.api.ingredient;

import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.service.ingredient.GetIngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
public class GetIngredientController {

    private final GetIngredientService getIngredientService;

    /**
     * Api method used to fetch ingredient object by its id.
     * @param uuid ingredient to fetch
     * @return ingredient
     */
    @GetMapping("/v1/get_ingredient/{uuid}")
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable UUID uuid) {
        IngredientResponse ingredient = getIngredientService.getIngredientById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ingredient);
    }

    /**
     * Api method used to fetch all ingredients store in database.
     * Use only for test purposes.
     * @return list of all ingredients in database.
     */
    @GetMapping("/v1/get_all_ingredient")
    public ResponseEntity<List<IngredientResponse>> getAllIngredients() {
        List<IngredientResponse> ingredients = getIngredientService.getAllIngredients();
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    /**
     * Api method used to get ingredients objects from uuid list.
     * Typically used to fetch ingredients for recipe which holds only ingredients ids.
     * @param idsList list of ingredients id
     * @return list of ingredients
     */
    @GetMapping("/v1/get_ingredients_from_uuid_list/{uuidList}")
    public ResponseEntity<List<IngredientResponse>> getIngredientsByIdsList(@PathVariable List<UUID> idsList) {
        List<IngredientResponse> ingredients = getIngredientService.getIngredientsByUuidList(idsList);
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    /**
     * Api method used to fetch ingredient object by its name.
     * @param ingredientName to fetch.
     * @return ingredient
     */
    @GetMapping("/v1/get_ingredient_by_name/{ingredientName}")
    public ResponseEntity<UUID> getIngredientIdtByName(@PathVariable String ingredientName) {
        UUID ingredientIdWithName = getIngredientService.getIngredientByName(ingredientName);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientIdWithName);
    }

    /**
     * Api method used to fetch ingredients objects with similar name.
     * @param similarName fo fetch.
     * @return ingredient
     */
    @GetMapping("/v1/get_ingredient_with_similar_name/{similarName}")
    public ResponseEntity<List<String>> getIngredientsNamesSimilarToName(@PathVariable String similarName) {
       List<String> names = getIngredientService.getIngredientsNamesSimilarToName(similarName);
        return ResponseEntity.status(HttpStatus.OK).body(names);
    }

    /**
     * Api method that returns ids list of the recipes which names matches passed name list.
     * Typically used to return ids of the ingredients which are searched by user.
     * @param ingredientNames names of ingredients
     * @return ingredients ids
     */
    @GetMapping("/v1/get_ids_from_ingredient_name_list/{ingredientNames}")
    public ResponseEntity<List<UUID>> getIngredientsByIngredientsNames(@PathVariable List<String> ingredientNames) {
        List<UUID> ingredientsUuids = getIngredientService.getIngredientsWithNames(ingredientNames);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientsUuids);
    }
}
