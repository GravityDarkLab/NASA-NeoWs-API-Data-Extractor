package com.darklab.asteroids;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.darklab.asteroids.service.NeoWsService;

@SpringBootTest
@AutoConfigureMockMvc
public class NeoWsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NeoWsService neoWsService;

	@Test
	public void testListMaxMinDiameter() throws Exception {
		List<String> mockResult = Arrays.asList("Diameter1", "Diameter2");
		when(neoWsService.fetchDataAndExtractInfo(anyString(), anyString(), eq("maxMinDiameter")))
				.thenReturn(mockResult);
		mockMvc.perform(get("/listMaxMinDiameter").param("start_date", "2023-01-01").param("end_date", "2023-01-04"))
				.andExpect(status().isOk()).andExpect(model().attribute("result", mockResult))
				.andExpect(view().name("index"));
	}

	@Test
	void testListRelativeVelocityPositiveScenario() throws Exception {
		List<String> mockData = Arrays.asList("Velocity1", "Velocity2");
		when(neoWsService.fetchDataAndExtractInfo(anyString(), anyString(), anyString())).thenReturn(mockData);
		mockMvc.perform(get("/listRelativeVelocity").param("start_date", "2021-01-01").param("end_date", "2021-01-02"))
				.andExpect(status().isOk()).andExpect(model().attribute("result", hasSize(2)))
				.andExpect(model().attribute("result", hasItem("Velocity1"))).andExpect(view().name("index"));
	}

	@Test
	void testListRelativeVelocityWithNoData() throws Exception {
		when(neoWsService.fetchDataAndExtractInfo(anyString(), anyString(), anyString()))
				.thenReturn(Collections.emptyList());
		mockMvc.perform(get("/listRelativeVelocity")
						.param("start_date", "2021-01-01")
						.param("end_date", "2021-01-02"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("result", hasSize(0)))
				.andExpect(view().name("index"));
	}

	@Test
	void testListRelativeVelocityServiceException() throws Exception {
		when(neoWsService.fetchDataAndExtractInfo(anyString(), anyString(), anyString()))
				.thenThrow(new RuntimeException("Internal error occurred"));
		mockMvc.perform(get("/listRelativeVelocity")
						.param("start_date", "2021-01-01")
						.param("end_date", "2021-01-02"))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	@Test
	void testListRelativeVelocityMissingParams() throws Exception {
		mockMvc.perform(get("/listRelativeVelocity")).andExpect(status().isBadRequest());
	}

}
