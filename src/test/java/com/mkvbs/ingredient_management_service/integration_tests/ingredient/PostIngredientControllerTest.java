package com.mkvbs.ingredient_management_service.integration_tests.ingredient;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkvbs.ingredient_management_service.api.resource.ApiPath;
import com.mkvbs.ingredient_management_service.integration_tests.BasicIntegrationTest;
import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.Ingredient;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql(scripts = {
        "/sql_test_queries/clean_database.sql",
        "/sql_test_queries/post_ingredient_controller_test.sql"})
public class PostIngredientControllerTest extends BasicIntegrationTest {

    private final Ingredient ingredient1;
    private final Ingredient ingredient2;

    private final static String FIRST_INGREDIENT_NAME = "chicken";
    private final static String SECOND_INGREDIENT_NAME = "egg";

    @Autowired
    private ObjectMapper objectMapper;

    public PostIngredientControllerTest() {
        ingredient1 = new Ingredient(FIRST_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.FISH);
        ingredient2 = new Ingredient(SECOND_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.EGG);
    }

    @Test
    public void testPostIngredientWithNotExistingIngredient() {
        try {
            String jsonIngredient = objectMapper.writeValueAsString(ingredient1);
            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value(FIRST_INGREDIENT_NAME))
                    .andExpect(jsonPath("$.typeOfQuantity").value("NONE"))
                    .andExpect(jsonPath("$.allergen").value("FISH"))
                    .andExpect(jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            fail("Exeption during test: " + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithExistingIngredient() {
        try {
            String jsonIngredient = objectMapper.writeValueAsString(ingredient2);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isNotAcceptable())
                    .andExpect(jsonPath("$.details").value("Ingredient with name " + SECOND_INGREDIENT_NAME + " already exists."));
        } catch (Exception e) {
            fail("Exeption during test: " + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientListWithExistingIngredient() {
        try {
            List<Ingredient> ingredientList = List.of(ingredient1, ingredient2);
            String jsonIngredient = objectMapper.writeValueAsString(ingredientList);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_LIST_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.length()").value(1));
        } catch (Exception e) {
            fail("Exeption during test: " + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientListWithOnlyExistingIngredient() {
        try {
            List<Ingredient> ingredientList = List.of(ingredient2);
            String jsonIngredient = objectMapper.writeValueAsString(ingredientList);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_LIST_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.length()").value(0));
        } catch (Exception e) {
            fail("Exeption during test: " + e.getMessage());
        }
    }
}
