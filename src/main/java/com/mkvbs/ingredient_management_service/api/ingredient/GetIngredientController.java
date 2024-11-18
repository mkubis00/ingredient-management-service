package com.mkvbs.ingredient_management_service.api.ingredient;

import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import com.mkvbs.ingredient_management_service.service.ingredient.GetIngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class GetIngredientController {

    private final GetIngredientService getIngredientService;

    /**
     * Api method used to fetch ingredient object by its id.
     * @param id ingredient to fetch
     * @return ingredient
     */
    @GetMapping(ApiPath.INGREDIENT.GET_V1)
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable UUID id) {
        IngredientResponse ingredient = getIngredientService.getIngredientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ingredient);
    }

    /**
     * Api method used to fetch all ingredients store in database.
     * Use only for test purposes.
     * @return list of all ingredients in database.
     */
    @GetMapping(ApiPath.INGREDIENT.GET_ALL_V1)
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
    @GetMapping(ApiPath.INGREDIENT.GET_FROM_ID_LIST_V1)
    public ResponseEntity<List<IngredientResponse>> getIngredientsByIdsList(@PathVariable List<UUID> idsList) {
        List<IngredientResponse> ingredients = getIngredientService.getIngredientsByUuidList(idsList);
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    /**
     * Api method used to fetch ingredient object by its name.
     * @param ingredientName to fetch.
     * @return ingredient
     */
    @GetMapping(ApiPath.INGREDIENT.GET_INGREDIENT_ID_BY_NAME_V1)
    public ResponseEntity<UUID> getIngredientIdtByName(@PathVariable String ingredientName) {
        UUID ingredientIdWithName = getIngredientService.getIngredientByName(ingredientName);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientIdWithName);
    }

    /**
     * Api method used to fetch ingredients objects with similar name.
     * @param similarName fo fetch.
     * @return ingredient
     */
    @GetMapping(ApiPath.INGREDIENT.GET_LIST_WITH_SIMILAR_NAME_V1)
    public ResponseEntity<List<String>> getIngredientsNamesSimilarToName(@PathVariable String similarName) {
       List<String> names = getIngredientService.getIngredientsNamesSimilarToName(similarName);
        return ResponseEntity.status(HttpStatus.OK).body(names);
    }
}
