package com.rms.api.controller;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rms.api.model.Rate;
import com.rms.api.model.Surcharge;
import com.rms.api.service.RateService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RateController.class)
@EnableWebMvc
public class RateControllerTest {

	protected MockMvc mvc;

	@MockBean
	private RateService rateService;

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	WebApplicationContext webApplicationContext;

	private static final String URI_SURCHARGE = "https://surcharge.free.beeceptor.com/surcharge";

	@BeforeEach
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testSearchRateById() throws Exception {

		Long rateId = 1L;

		RequestBuilder requestBuider = MockMvcRequestBuilders.get("/ratemanagementsystem/rate/{rateId}", rateId);

		Rate rate = getRate();

		Mockito.when(rateService.searchById(rateId)).thenReturn(rate);
		Mockito.when(restTemplate.getForObject(URI_SURCHARGE, Surcharge.class)).thenReturn(new Surcharge(200L, 50L));

		mvc.perform(requestBuider).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testCreateRate() throws Exception {

		Rate rate = getRate();
		ObjectMapper objectMapper = new ObjectMapper();
		RequestBuilder requestBuider = MockMvcRequestBuilders.post("/ratemanagementsystem/createrate")
				.content(objectMapper.writeValueAsString(rate)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		Mockito.when(restTemplate.getForObject(URI_SURCHARGE, Surcharge.class)).thenReturn(new Surcharge(200L, 50L));
		Mockito.when(rateService.createRate(Mockito.any(Rate.class))).thenReturn(rate);

		mvc.perform(requestBuider).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rateId").exists()).andReturn();
	}

	@Test
	public void updateRate() throws Exception {
		Rate rate = getRate();
		ObjectMapper objectMapper = new ObjectMapper();
		RequestBuilder requestBuider = MockMvcRequestBuilders.put("/ratemanagementsystem/updaterate")
				.content(objectMapper.writeValueAsString(rate)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		Mockito.when(restTemplate.getForObject(URI_SURCHARGE, Surcharge.class)).thenReturn(new Surcharge(200L, 50L));
		Mockito.when(rateService.updateRate(rate)).thenReturn(rate);

		mvc.perform(requestBuider).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("rateId").value(1));
	}

	@Test
	public void deleteRateById() throws Exception {
		Long rateId = 1L;
		RequestBuilder requestBuider = MockMvcRequestBuilders.delete("/ratemanagementsystem/rate/{rateId}", rateId)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuider).andExpect(MockMvcResultMatchers.status().isOk());
		rateService.deleteRate(rateId);
	}

	private Rate getRate() {
		Calendar calendar = Calendar.getInstance();
		Rate rate = new Rate();
		rate.setRateId(1L);
		rate.setRateDescription("Rate of Product 1");
		rate.setRateEffectiveDate(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		rate.setRateExpirationDate(calendar.getTime());
		rate.setAmount(10);
		return rate;
	}

}
