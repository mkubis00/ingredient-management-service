package com.mkvbs.ingredient_management_service.unit_tests.ingredient.service.ingredient;

import com.mkvbs.ingredient_management_service.factory.Factory;
import com.mkvbs.ingredient_management_service.factory.ingredient.IngredientFactory;
import com.mkvbs.ingredient_management_service.factory.ingredient.IngredientResponseFactory;
import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.model.api.IngredientResponse;
import com.mkvbs.ingredient_management_service.model.exception.EntityAlreadyExistsException;
import com.mkvbs.ingredient_management_service.repository.IngredientRepository;
import com.mkvbs.ingredient_management_service.service.ingredient.PostIngredientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PostIngredientServiceTest {

    private PostIngredientService postIngredientService;

    private IngredientRequest ingredientRequest1;
    private IngredientRequest ingredientRequest2;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Ingredient ingredient1WithId;
    private Ingredient ingredient2WithId;

    private final String NOT_SAVED_INGREDIENT_NAME = "notSavedIngredientName";
    private final String ALREADY_SAVED_INGREDIENT_NAME = "alreadySavedIngredientName";

    @Test
    void testSaveNewIngredient() {
        IngredientResponse ingredientResponse = postIngredientService.saveIngredient(ingredientRequest1);

        assertThat(ingredientResponse.getName()).isEqualTo(NOT_SAVED_INGREDIENT_NAME);
        assertThat(ingredientResponse.getTypeOfQuantity()).isEqualTo(TypeOfQuantity.NONE);
        assertThat(ingredientResponse.getAllergen()).isEqualTo(Allergen.CELERY);
    }

    @Test
    void testSaveAlreadyExistingIngredient() {
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () ->
            postIngredientService.saveIngredient(ingredientRequest2));
        assertThat(exception.getMessage()).isEqualTo("Ingredient with name " + ingredientRequest2.getName() + " already exists.");
    }

    @Test
    void testSaveIngredientListWithAlreadyExistingIngredient() {
        List<IngredientRequest> ingredientRequestList = List.of(ingredientRequest1, ingredientRequest2);
        List<IngredientResponse> ingredientResponses = postIngredientService.saveIngredientsList(ingredientRequestList);

        assertThat(ingredientResponses.size()).isEqualTo(1);
        assertThat(ingredientResponses.get(0).getName()).isEqualTo(NOT_SAVED_INGREDIENT_NAME);
    }

    @BeforeAll
    void setUp() {
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        Factory<Ingredient, IngredientRequest> ingredientFactory = mock(IngredientFactory.class);
        Factory<IngredientResponse, Ingredient> ingredientResponseFactory = new IngredientResponseFactory();

        createIngredientsObjects();

        postIngredientService = new PostIngredientService(ingredientRepository, ingredientFactory, ingredientResponseFactory);
        ingredientRequest1 = new IngredientRequest(NOT_SAVED_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.CELERY);

        when(ingredientRepository.findByName(ALREADY_SAVED_INGREDIENT_NAME)).thenReturn(Optional.of(ingredient2WithId));
        when(ingredientRepository.findByName(NOT_SAVED_INGREDIENT_NAME)).thenReturn(Optional.empty());
        when(ingredientRepository.save(ingredient1)).thenReturn(ingredient1WithId);
        when(ingredientRepository.saveAll(anyList())).thenReturn(List.of(ingredient1));
        when(ingredientFactory.create(ingredientRequest1)).thenReturn(ingredient1);
        when(ingredientFactory.create(ingredientRequest2)).thenReturn(ingredient2);

        when(ingredientRepository.saveAll(anyList())).thenAnswer(invocation -> {
            List<Ingredient> ingredients = invocation.getArgument(0);
            if (ingredients.size() > 1) {
                return List.of(ingredient1WithId, ingredient2WithId);
            } else if (!ingredients.isEmpty()){
                return List.of(ingredient1WithId);
            } else {
                return Collections.emptyList();
            }
        });
    }

    private void createIngredientsObjects() {
        ingredientRequest1 = new IngredientRequest(NOT_SAVED_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.CELERY);
        ingredientRequest2 = new IngredientRequest(ALREADY_SAVED_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.CELERY);

        UUID uuid = UUID.randomUUID();

        ingredient1 = new Ingredient(ingredientRequest1.getName(), ingredientRequest1.getTypeOfQuantity(), ingredientRequest1.getAllergen());
        ingredient2 = new Ingredient(ingredientRequest2.getName(), ingredientRequest2.getTypeOfQuantity(), ingredientRequest2.getAllergen());

        ingredient1WithId = new Ingredient(uuid, ingredientRequest1.getName(), ingredientRequest1.getTypeOfQuantity(), ingredientRequest1.getAllergen());
        ingredient2WithId = new Ingredient(uuid, ingredientRequest2.getName(), ingredientRequest2.getTypeOfQuantity(), ingredientRequest2.getAllergen());
    }
}