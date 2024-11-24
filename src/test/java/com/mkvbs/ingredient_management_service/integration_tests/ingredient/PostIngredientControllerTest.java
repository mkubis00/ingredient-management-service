package com.mkvbs.ingredient_management_service.integration_tests.ingredient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkvbs.ingredient_management_service.integration_tests.BasicIntegration;
import com.mkvbs.ingredient_management_service.integration_tests.TestStringTemplates;
import com.mkvbs.ingredient_management_service.model.Allergen;
import com.mkvbs.ingredient_management_service.model.TypeOfQuantity;
import com.mkvbs.ingredient_management_service.model.api.IngredientRequest;
import com.mkvbs.ingredient_management_service.resource.api.ApiPath;
import com.mkvbs.ingredient_management_service.resource.api.ValidationMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {
        "/sql_test_queries/clean_database.sql",
        "/sql_test_queries/post_ingredient_controller_test.sql"})
public class PostIngredientControllerTest extends BasicIntegration {

    private final static String NOT_SAVED_INGREDIENT_NAME = "chicken";
    private final static String ALREADY_SAVED_INGREDIENT_NAME = "egg";
    private final static String INCORRECT_NAME = "ab";
    private final static String NAME = "name";
    private final static String ALLERGEN = "allergen";
    private final static String TYPE_OF_QUANTITY = "typeOfQuantity";
    private final static String NONE = "NONE";
    private final static TypeOfQuantity PROPER_TYPE = TypeOfQuantity.NONE;
    private final static Allergen PROPER_ALLERGEN = Allergen.EGG;

    private final IngredientRequest ingredientRequest1;
    private final IngredientRequest ingredientRequest2;

    @Autowired
    private ObjectMapper objectMapper;

    public PostIngredientControllerTest() {
        ingredientRequest1 = new IngredientRequest(NOT_SAVED_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.FISH);
        ingredientRequest2 = new IngredientRequest(ALREADY_SAVED_INGREDIENT_NAME, TypeOfQuantity.NONE, Allergen.EGG);
    }

    @Test
    public void testPostIngredientWithNotExistingIngredient() {
        try {
            String jsonIngredient = objectMapper.writeValueAsString(ingredientRequest1);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath(TestStringTemplates.JSON_NAME).value(NOT_SAVED_INGREDIENT_NAME))
                    .andExpect(jsonPath(TestStringTemplates.JSON_TYPE_OF_QUANTITY).value("NONE"))
                    .andExpect(jsonPath(TestStringTemplates.JSON_ALLERGEN).value("FISH"))
                    .andExpect(jsonPath(TestStringTemplates.JSON_ID).isNotEmpty());
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithExistingIngredient() {
        try {
            String jsonIngredient = objectMapper.writeValueAsString(ingredientRequest2);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isNotAcceptable())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS)
                            .value("Ingredient with name " + ALREADY_SAVED_INGREDIENT_NAME + " already exists."));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithOutProperName() {
        try {
            IngredientRequest ingRequest = new IngredientRequest(INCORRECT_NAME, PROPER_TYPE, PROPER_ALLERGEN);
            String jsonIngredient = objectMapper.writeValueAsString(ingRequest);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(containsString(ValidationMessage.INCORRECT_NAME_LEN)));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }

    }

    @Test
    public void testPostIngredientWithOutAllergen() {
        try {
            Map<String, String> request = new HashMap<>();
            request.put(NAME, NOT_SAVED_INGREDIENT_NAME);
            request.put(TYPE_OF_QUANTITY, NONE);
            String jsonIngredient = objectMapper.writeValueAsString(request);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(containsString(ValidationMessage.ALLERGEN_NOT_NULL)));

        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithOutTypeOfQuantity() {
        try {
            Map<String, String> request = new HashMap<>();
            request.put(NAME, NOT_SAVED_INGREDIENT_NAME);
            request.put(ALLERGEN, NONE);
            String jsonIngredient = objectMapper.writeValueAsString(request);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(containsString(ValidationMessage.TYPE_OF_QUA_NOT_NULL)));

        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithAllPropertiesWrong() {
        try {
            Map<String, String> request = new HashMap<>();
            request.put(NAME, INCORRECT_NAME);
            String jsonIngredient = objectMapper.writeValueAsString(request);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(containsString(ValidationMessage.INCORRECT_NAME_LEN)))
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(containsString(ValidationMessage.ALLERGEN_NOT_NULL)))
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(containsString(ValidationMessage.TYPE_OF_QUA_NOT_NULL)));

        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithNoExistingProperty() {
        try {
            Map<String, String> request = new HashMap<>();
            request.put(NAME, NOT_SAVED_INGREDIENT_NAME);
            request.put(ALLERGEN, NONE);
            request.put(TYPE_OF_QUANTITY, NONE);
            request.put("nonExistingProperty", "test");
            String jsonIngredient = objectMapper.writeValueAsString(request);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.nonExistingProperty").doesNotExist());

        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientWithNullArgumentInRequest() {
        try {
            Map<String, String> request = new HashMap<>();
            request.put(NAME, NOT_SAVED_INGREDIENT_NAME);
            request.put(ALLERGEN, "");
            request.put(TYPE_OF_QUANTITY, NONE);
            String jsonIngredient = objectMapper.writeValueAsString(request);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath(TestStringTemplates.JSON_DETAILS).value(ValidationMessage.HTTP_MESSAGE_NOT_READABLE));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientListWithExistingIngredient() {
        try {
            List<IngredientRequest> ingredientList = List.of(ingredientRequest1, ingredientRequest2);
            String jsonIngredient = objectMapper.writeValueAsString(ingredientList);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_LIST_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath(TestStringTemplates.JSON_LENGTH).value(1));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }

    @Test
    public void testPostIngredientListWithOnlyExistingIngredient() {
        try {
            List<IngredientRequest> ingredientList = List.of(ingredientRequest2);
            String jsonIngredient = objectMapper.writeValueAsString(ingredientList);

            mockMvc.perform(post(ApiPath.INGREDIENT.CREATE_LIST_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonIngredient))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath(TestStringTemplates.JSON_LENGTH).value(0));
        } catch (Exception e) {
            fail(TestStringTemplates.DEFAULT_FAIL_MESSAGE + e.getMessage());
        }
    }
}
