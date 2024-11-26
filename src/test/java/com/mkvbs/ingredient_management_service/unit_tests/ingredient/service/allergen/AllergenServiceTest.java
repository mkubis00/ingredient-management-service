package com.mkvbs.ingredient_management_service.unit_tests.ingredient.service.allergen;

import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import com.mkvbs.ingredient_management_service.service.allergen.AllergenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AllergenServiceTest {

    private IngredientRepository ingredientRepository;
    private AllergenService allergenService;
    private final List<Allergen> allergenList = new ArrayList<>();
    private final List<UUID> uuidList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ingredientRepository = mock(IngredientRepository.class);
        allergenService = new AllergenService(ingredientRepository);

        when(ingredientRepository.findDistinctAllergensByIngredientIds(uuidList)).thenReturn(allergenList);

        setUpLists();
    }

    @Test
    void getAllergensListByIngredientsIdList() {
        List<Allergen> allergens = allergenService.getAllergensListByIngredientsIdList(uuidList);

        assertThat(allergens).hasSize(3).contains(Allergen.EGG, Allergen.CELERY, Allergen.FISH);
    }

    private void setUpLists() {
        Allergen allergen1 = Allergen.EGG;
        Allergen allergen2 = Allergen.FISH;
        Allergen allergen3 = Allergen.CELERY;

        allergenList.add(allergen1);
        allergenList.add(allergen2);
        allergenList.add(allergen3);

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        uuidList.add(uuid1);
        uuidList.add(uuid2);
        uuidList.add(uuid3);
    }
}