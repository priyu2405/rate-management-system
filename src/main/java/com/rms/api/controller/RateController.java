package com.rms.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rms.api.model.Rate;
import com.rms.api.model.Surcharge;
import com.rms.api.service.RateService;

@RestController
@RequestMapping("/ratemanagementsystem")
public class RateController {

	private static final Logger logger = LoggerFactory.getLogger(RateController.class);

	private static final String URI_SURCHARGE = "https://surcharge.free.beeceptor.com/surcharge";

	@Autowired
	private RateService rateService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/rate/{rateId}")
	public Rate searchRateById(@PathVariable("rateId") Long rateId) throws Exception {
		logger.info("Search Rate By Id :  {}", rateId);
		Rate rate = rateService.searchById(rateId);
		addSurcharge(rate);
		return rate;
	}

	@PostMapping("/createrate")
	public Rate createRate(@RequestBody Rate rate) throws Exception {
		logger.info("Create rate : {}", rate.getRateDescription());
		Rate createdRate = rateService.createRate(rate);
		addSurcharge(createdRate);
		return createdRate;
	}

	@PutMapping("/updaterate")
	public Rate updateRate(@RequestBody Rate rate) throws Exception {
		logger.info("Update rate : {}", rate.getRateId());
		Rate updatedRate = rateService.updateRate(rate);
		addSurcharge(updatedRate);
		return updatedRate;
	}

	@DeleteMapping("/rate/{rateId}")
	public void deleteRateById(@PathVariable("rateId") Long rateId) throws Exception {
		logger.info("Delete rate : {}", rateId);
		rateService.deleteRate(rateId);
		logger.info("Rate {} deleted successfully.", rateId);
	}

	private void addSurcharge(Rate rate) {
		if (rate != null) {
			Surcharge surcharge = restTemplate.getForObject(URI_SURCHARGE, Surcharge.class);
			rate.setSurcharge(surcharge);
		}
	}
}
