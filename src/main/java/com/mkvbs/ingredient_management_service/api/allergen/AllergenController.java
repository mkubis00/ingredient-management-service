package com.mkvbs.ingredient_management_service.api.allergen;

import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.service.allergen.AllergenService;
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
@RequestMapping("/allergen_description")
@AllArgsConstructor
public class AllergenController {

    private final AllergenService allergenService;

    /**
     * Api method used to get allergen description.
     * @param allergen which is allergen to describe.
     * @return void
     */
    @GetMapping("/v1/get_description/{allergen}")
    public ResponseEntity<String> getAllergenDescription(@PathVariable Allergen allergen) {
        String allergenValue = allergenService.getAllergenDescription(allergen);
        return ResponseEntity.status(HttpStatus.OK).body(allergenValue);
    }

    /**
     * Api method used to get allergens from ingredients list.
     * @param ingredients which is ingredients to take allergens from.
     * @return allergens
     */
    @GetMapping("/v1/get_allergens_from_ingredients/{ingredients}")
    public ResponseEntity<List<Allergen>> getAllergensFromIngredients(@PathVariable List<UUID> ingredients) {
        List<Allergen> allergens = allergenService.getAllergensListByIngredientsIdList(ingredients);
        return ResponseEntity.status(HttpStatus.OK).body(allergens);
    }
}
