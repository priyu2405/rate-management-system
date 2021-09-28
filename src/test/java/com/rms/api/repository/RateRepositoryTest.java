package com.rms.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.rms.api.entity.RateEntity;

@DataJpaTest
@Rollback(false)
@RunWith(SpringRunner.class)
class RateRepositoryTest {

	@Autowired
	private RateRepository rateRepository;

	@BeforeEach
	public void setUp() {

		RateEntity rateEntity = getRateEntity();
		rateRepository.save(rateEntity);

	}

	@Test
	@DisplayName("Create Rate")
	void testCreateRate() {
		RateEntity rateEntity = getRateEntity();
		RateEntity createdRate = rateRepository.save(rateEntity);
		assertNotNull(createdRate);
		assertThat(createdRate.getRateId()).isGreaterThan(0);
	}

	private RateEntity getRateEntity() {
		Calendar calendar = Calendar.getInstance();
		RateEntity rateEntity = new RateEntity();
		rateEntity.setRateDescription("Rate of Product 1");
		rateEntity.setRateEffectiveDate(calendar.getTime());
		calendar.add(Calendar.MONTH, 12);
		rateEntity.setRateExpirationDate(calendar.getTime());
		rateEntity.setAmount(10);
		return rateEntity;
	}

	@Test
	@DisplayName("Search Rate")
	void testFindByRateId() {
		Optional<RateEntity> foundRates = rateRepository.findById(1L);
		RateEntity foundRate = null;
		if (foundRates.isPresent()) {
			foundRate = foundRates.get();
		}
		assertNotNull(foundRate);
		assertEquals(1L, foundRate.getRateId());
	}

	@Test
	@DisplayName("Delete Rate")
	void testDeleteById() {
		rateRepository.deleteById(1L);
	}

}
