package com.rms.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rms.api.entity.RateEntity;
import com.rms.api.exceptions.DataValidationExceptionHandler;
import com.rms.api.exceptions.NotFoundExceptionHandler;
import com.rms.api.model.Rate;
import com.rms.api.repository.RateRepository;
import com.rms.api.service.RateService;
import com.rms.api.utils.RateApiCommonConstants;
import com.rms.api.validation.RmsValidator;

@Service
@Transactional
public class RateServiceImpl extends RmsValidator implements RateService {

	private static final Logger logger = LoggerFactory.getLogger(RateServiceImpl.class);

	@Autowired
	private RateRepository rateRepository;

	public Rate searchById(Long rateId) throws Exception {
		logger.info(" Search Rate By Id :  {}", rateId);
		Optional<RateEntity> rateEntity = rateRepository.findById(rateId);
		if (!rateEntity.isPresent()) {
			logger.error("RateId {} not found.", rateId);
			throw new NotFoundExceptionHandler(
					String.format(RateApiCommonConstants.NOT_FOUND, RateApiCommonConstants.RATE_ID));
		}
		Rate rate = new Rate();
		BeanUtils.copyProperties(rateEntity.get(), rate);
		return rate;
	}

	public Rate createRate(Rate rate) throws Exception {
		logger.info(" Create rate {}.", rate.getRateDescription());
		List<String> validationErrors = validateRate(rate);
		if (validationErrors.size() > 0) {
			logger.info("Rate {} create failed.", rate.getRateDescription());
			throw new DataValidationExceptionHandler(validationErrors);
		}

		RateEntity rateEntity = new RateEntity();
		BeanUtils.copyProperties(rate, rateEntity);
		RateEntity savedRateEntity = rateRepository.save(rateEntity);
		BeanUtils.copyProperties(savedRateEntity, rate);
		logger.info(" Rate created successfully {}", rate.getRateId());
		return rate;
	}

	public Rate updateRate(Rate rate) throws Exception {
		logger.info(" Update rate {}.", rate.getRateId());
		List<String> validationErrors = validateRate(rate);
		if (validationErrors.size() > 0) {
			logger.info("Rate {} update failed.", rate.getRateDescription());
			throw new DataValidationExceptionHandler(validationErrors);
		}
		RateEntity rateEntity = new RateEntity();
		Optional<RateEntity> savedRateEntity = rateRepository.findById(rate.getRateId());
		if (!savedRateEntity.isPresent()) {
			throw new NotFoundExceptionHandler(
					String.format(RateApiCommonConstants.NOT_FOUND, RateApiCommonConstants.RATE_ID));
		}
		BeanUtils.copyProperties(rate, rateEntity);
		rateEntity = rateRepository.save(rateEntity);
		BeanUtils.copyProperties(savedRateEntity, rate);
		logger.info(" Rate {} updated successfully.", rate.getRateDescription());
		return rate;
	}

	public void deleteRate(Long rateId) throws Exception {
		logger.info(" Delete rate {}.", rateId);
		Optional<RateEntity> savedRateEntity = rateRepository.findById(rateId);
		if (!savedRateEntity.isPresent()) {
			throw new NotFoundExceptionHandler(
					String.format(RateApiCommonConstants.NOT_FOUND, RateApiCommonConstants.RATE_ID));
		}
		rateRepository.deleteById(rateId);
		logger.info(" {} rate deleted successfully.", rateId);
	}

}
