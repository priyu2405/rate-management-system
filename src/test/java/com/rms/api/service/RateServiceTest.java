package com.rms.api.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rms.api.entity.RateEntity;
import com.rms.api.exceptions.DataValidationExceptionHandler;
import com.rms.api.exceptions.NotFoundExceptionHandler;
import com.rms.api.model.Rate;
import com.rms.api.repository.RateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class RateServiceTest {

	@MockBean
	private RateRepository rateRepository;

	@Autowired
	private RateService rateService;

	@BeforeEach
	public void setUp() {
		RateEntity rateEntity = new RateEntity();
		rateEntity.setRateId(1L);
		rateEntity.setRateDescription("Rate of Product 1");
		Optional<RateEntity> rates = Optional.of(rateEntity);
		when(rateRepository.findById(1L)).thenReturn(rates);
	}

	@Test
	@DisplayName("testSearchById - successfull")
	void testSearchById() throws Exception {

		Rate rate = rateService.searchById(1L);
		assertNotNull(rate);
	}

	@Test
	@DisplayName("testSearchById - NotFound")
	void testSearchByIdNotFound() throws Exception {

		NotFoundExceptionHandler notFoundException = assertThrows(NotFoundExceptionHandler.class, () -> {
			rateService.searchById(2L);
		});

		assertTrue(notFoundException.getMessage().contains("RateId not found in RMS"));
	}

	@Test
	@DisplayName("createRate - success")
	void createRate() throws Exception {
		RateEntity rateEntity = new RateEntity();
		rateEntity.setRateId(1L);
		rateEntity.setRateDescription("Rate of Product 1");

		Rate rate = getRate();

		when(rateRepository.save(Mockito.any(RateEntity.class))).thenReturn(rateEntity);
		Rate createdRate = rateService.createRate(rate);
		assertNotNull(createdRate);
		assertEquals(1L, createdRate.getRateId());
	}

	@Test
	@DisplayName("createRate - failure")
	void createRateFailed() throws Exception {

		Rate rate = getRate();
		rate.setRateEffectiveDate(null);
		rate.setAmount(null);
		rate.setRateExpirationDate(null);
		DataValidationExceptionHandler dataValidationException = assertThrows(DataValidationExceptionHandler.class,
				() -> {
					rateService.createRate(rate);
				});

		assertTrue(dataValidationException.getMessage().contains("Rate EffectiveDate is required."));
		assertTrue(dataValidationException.getMessage().contains("Rate ExpirationDate is required."));
		assertTrue(dataValidationException.getMessage().contains("Amount is required."));
	}

	@Test
	@DisplayName("updateRate - success")
	void updateRate() throws Exception {
		RateEntity rateEntity = new RateEntity();
		rateEntity.setRateId(1L);
		rateEntity.setRateDescription("Rate of Product 1");

		Rate rate = getRate();

		when(rateRepository.save(rateEntity)).thenReturn(rateEntity);
		Rate createdRate = rateService.updateRate(rate);
		assertNotNull(createdRate);
		assertEquals(1L, createdRate.getRateId());
	}

	@Test
	@DisplayName("updateRate - failure")
	void updateRateFail() throws Exception {
		Rate rate = getRate();
		rate.setRateEffectiveDate(null);
		rate.setAmount(null);
		rate.setRateExpirationDate(null);
		DataValidationExceptionHandler dataValidationException = assertThrows(DataValidationExceptionHandler.class,
				() -> {
					rateService.updateRate(rate);
				});

		assertTrue(dataValidationException.getMessage().contains("Rate EffectiveDate is required."));
		assertTrue(dataValidationException.getMessage().contains("Rate ExpirationDate is required."));
		assertTrue(dataValidationException.getMessage().contains("Amount is required."));
	}

	@Test
	@DisplayName("deleteRate")
	void deleteRate() throws Exception {

		doNothing().when(rateRepository).deleteById(1L);
		rateService.deleteRate(1L);

		ArgumentCaptor<Long> captorParam = ArgumentCaptor.forClass(Long.class);
		verify(rateRepository, times(1)).deleteById(captorParam.capture());

		Long actualVal = captorParam.getValue();
		assertEquals(1, actualVal);
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
