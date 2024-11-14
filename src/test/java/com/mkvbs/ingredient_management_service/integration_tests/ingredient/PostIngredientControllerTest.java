package com.mkvbs.ingredient_management_service.integration_tests.ingredient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final static String POST_INGREDIENT = "post_ingredient";
    private final static String POST_INGREDIENTS = "post_ingredients";
    private final static String BASE_URL = "/ingredients/v1/";
    private final static String FIRST_INGREDIENT_NAME = "chicken";
    private final static String SECOND_INGREDIENT_NAME = "egg";

    @Autowired
    private ObjectMapper objectMapper;

    public PostIngredientControllerTest() {
        ingredient1 = new Ingredient(FIRST_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.FISH);
        ingredient2 = new Ingredient(SECOND_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.EGG);
    }

    @Test
    public void testPostIngredientWithNotExistingIngredient() throws Exception {
        String jsonIngredient = objectMapper.writeValueAsString(ingredient1);

        mockMvc.perform(post(BASE_URL + POST_INGREDIENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIngredient))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(FIRST_INGREDIENT_NAME))
                .andExpect(jsonPath("$.typeOfQuantity").value("NONE"))
                .andExpect(jsonPath("$.allergen").value("FISH"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void testPostIngredientWithExistingIngredient() throws Exception {
        String jsonIngredient = objectMapper.writeValueAsString(ingredient2);

        mockMvc.perform(post(BASE_URL + POST_INGREDIENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonIngredient))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.details").value("Ingredient with name " + SECOND_INGREDIENT_NAME + " already exists."));
    }

    @Test
    public void testPostIngredientListWithExistingIngredient() throws Exception {
        List<Ingredient> ingredientList = List.of(ingredient1, ingredient2);
        String jsonIngredient = objectMapper.writeValueAsString(ingredientList);

        mockMvc.perform(post(BASE_URL + POST_INGREDIENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonIngredient))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testPostIngredientListWithOnlyExistingIngredient() throws Exception {
        List<Ingredient> ingredientList = List.of(ingredient2);
        String jsonIngredient = objectMapper.writeValueAsString(ingredientList);

        mockMvc.perform(post(BASE_URL + POST_INGREDIENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonIngredient))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
