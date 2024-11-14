package com.mkvbs.ingredient_management_service;

import com.mkvbs.ingredient_management_service.integration_tests.BasicIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(scripts = {"/sql_test_queries/clean_database.sql",
		"/sql_test_queries/post_ingredient_controller_test.sql"})
class IngredientManagementServiceApplicationTests extends BasicIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ingredients/v1/get_all_ingredient")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}

}
