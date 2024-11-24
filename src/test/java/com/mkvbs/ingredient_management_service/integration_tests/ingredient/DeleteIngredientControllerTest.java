package com.mkvbs.ingredient_management_service.integration_tests.ingredient;

import com.mkvbs.ingredient_management_service.integration_tests.BasicIntegration;
import com.mkvbs.ingredient_management_service.integration_tests.TestStringTemplates;
import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Sql(scripts = {
        "/sql_test_queries/clean_database.sql",
        "/sql_test_queries/delete_ingredient_controller_test.sql"})
public class DeleteIngredientControllerTest extends BasicIntegration {

    private static final String ID_TO_DELETE_1 = "dad56e32-925c-4fe6-a923-a88c9b60e0d4";
    private static final String ID_TO_DELETE_2 = "c4c56e32-925c-4fe6-a923-a88c9b60e0b5";

    @Test
    void testDeleteIngredientById() {
        try {
            mockMvc.perform(delete(ApiPath.INGREDIENT.TEST_V1 + ID_TO_DELETE_1))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_V1 + ID_TO_DELETE_1))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    void testDeleteAllIngredients() {
        try {
            mockMvc.perform(delete(ApiPath.INGREDIENT.DELETE_ALL_V1))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_V1 + ID_TO_DELETE_1))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());

            mockMvc.perform(get(ApiPath.INGREDIENT.TEST_V1 + ID_TO_DELETE_2))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }
}
