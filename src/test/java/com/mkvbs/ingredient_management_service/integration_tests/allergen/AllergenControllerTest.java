package com.mkvbs.ingredient_management_service.integration_tests.allergen;

import com.mkvbs.ingredient_management_service.integration_tests.BasicIntegration;
import com.mkvbs.ingredient_management_service.integration_tests.TestStringTemplates;
import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import com.mkvbs.ingredient_management_service.resource.model.AllergenValues;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(scripts = {
        "/sql_test_queries/clean_database.sql",
        "/sql_test_queries/allergen_controller_test.sql"})
class AllergenControllerTest extends BasicIntegration {

    @Test
    void testGetAllergenDescription() {
        try {
            mockMvc.perform(get(ApiPath.ALLERGEN.TEST_GET_DESCRIPTION_V1 + "NONE"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().string(AllergenValues.NONE));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    void testGetAllergensFromIngredients() {
        try {
            String id1 = "aad56e32-925c-4fe6-a923-a88c9b60e0d4";
            String id2 = "bad56e32-925c-4fe6-a923-a88c9b60e0d5";
            String id3 = "cad56e32-925c-4fe6-a923-a88c9b60e0d6";
            mockMvc.perform(get(ApiPath.ALLERGEN.TEST_GET_FROM_INGREDIENTS_IDS_V1 + id1 + "," + id2 + "," + id3))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$", containsInAnyOrder("NONE", "EGG", "GLUTEN")));

        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }
}
