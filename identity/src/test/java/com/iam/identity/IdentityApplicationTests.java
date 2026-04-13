package com.iam.identity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        properties = "spring.autoconfigure.exclude=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration")
class IdentityApplicationTests {

	@Test
	void contextLoads() {
	}

}

@WebMvcTest(UsersController.class)
class UsersControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getUserById_returnsUserId() throws Exception {
		mockMvc.perform(get("/users/42"))
				.andExpect(status().isOk())
				.andExpect(content().string("42"));
	}

	@Test
	void getUserById_returnsStringUserId() throws Exception {
		mockMvc.perform(get("/users/john"))
				.andExpect(status().isOk())
				.andExpect(content().string("john"));
	}

	@Test
	void getUserById_notFoundForMissingSegment() throws Exception {
		mockMvc.perform(get("/users/"))
				.andExpect(status().isNotFound());
	}
}

