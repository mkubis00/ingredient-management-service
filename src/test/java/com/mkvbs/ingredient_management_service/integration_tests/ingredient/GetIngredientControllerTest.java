package com.mkvbs.ingredient_management_service.integration_tests.ingredient;

import com.mkvbs.ingredient_management_service.integration_tests.BasicIntegrationTest;
import com.mkvbs.ingredient_management_service.integration_tests.TestStringTemplates;
import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(scripts = {
        "/sql_test_queries/clean_database.sql",
        "/sql_test_queries/get_ingredient_controller_test.sql"})
public class GetIngredientControllerTest extends BasicIntegrationTest {

    private static final String ID_OF_SAVED_INGREDIENT_1 = "dad56e32-925c-4fe6-a923-a88c9b60e0d4";
    private static final String ID_OF_SAVED_INGREDIENT_2 = "cbd56e32-925c-4fe7-a923-a67c9b60e0d4";
    private static final String ID_OF_NOT_SAVED_INGREDIENT = "bcb00e34-564c-4fe6-a923-b8959b54c1f6";

    @Test
    void testGetAllIngredients() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.GET_ALL_V1))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath(TestStringTemplates.JSON_LENGTH).value(9));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    void testGetIngredientByIdFound() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.BASE_V1 + "/" + ID_OF_SAVED_INGREDIENT_1))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath(TestStringTemplates.JSON_ID).value(ID_OF_SAVED_INGREDIENT_1))
                    .andExpect(jsonPath(TestStringTemplates.JSON_NAME).value("name"))
                    .andExpect(jsonPath(TestStringTemplates.JSON_TYPE_OF_QUANTITY).value("CUP"))
                    .andExpect(jsonPath(TestStringTemplates.JSON_ALLERGEN).value("GLUTEN"));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    void testGetIngredientByIdNotFound() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_V1 + ID_OF_NOT_SAVED_INGREDIENT))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value("Recipe with id: " + ID_OF_NOT_SAVED_INGREDIENT + " not found."));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testGetIngredientsListContainsSpecificIds() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_GET_FROM_ID_LIST_V1 + ID_OF_SAVED_INGREDIENT_1 + "," + ID_OF_SAVED_INGREDIENT_2 + "," + ID_OF_NOT_SAVED_INGREDIENT))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath(TestStringTemplates.JSON_LENGTH).value(2))
                    .andExpect(jsonPath("$[*].id").value(hasItems(ID_OF_SAVED_INGREDIENT_1, ID_OF_SAVED_INGREDIENT_2)));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testGetIngredientIdtByNameWithExistingIngredient() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_GET_INGREDIENT_ID_BY_NAME_V1 + "name"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().string("\"" + ID_OF_SAVED_INGREDIENT_1 + "\""));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testGetIngredientIdtByNameWithOutExistingIngredient() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_GET_INGREDIENT_ID_BY_NAME_V1 + "notExistingNAme"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value("Recipe with name: notExistingNAme not found."));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testGetIngredientsNamesSimilarToName() {
        try {
            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_GET_LIST_WITH_SIMILAR_NAME_V1 + "nam"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(5)))
                    .andExpect(jsonPath("$", containsInAnyOrder("name", "name1", "name2", "name3", "name4")));

            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_GET_LIST_WITH_SIMILAR_NAME_V1 + "na"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(6)))
                    .andExpect(jsonPath("$", containsInAnyOrder("name", "name1", "name2", "name3", "name4", "nara")));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }
}
