package com.iam.identity;

import com.iam.identity.dao.IdentityDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IdentityApplicationTests {

	@Test
	void contextLoads() {
	}

}

@WebMvcTest(UsersController.class)
class UsersControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private IdentityDao identityDao;

	@Test
	void getUserById_returnsUserId() throws Exception {
		when(identityDao.getRootUser()).thenReturn("Passed");
		mockMvc.perform(get("/users/42"))
				.andExpect(status().isOk())
				.andExpect(content().string("42"));
	}

	@Test
	void getUserById_returnsStringUserId() throws Exception {
		when(identityDao.getRootUser()).thenReturn("Passed");
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

