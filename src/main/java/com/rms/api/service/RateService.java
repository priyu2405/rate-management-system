package com.rms.api.service;

import com.rms.api.model.Rate;

public interface RateService {

	public Rate searchById(Long rateId) throws Exception;

	public Rate createRate(Rate rate) throws Exception;

	public Rate updateRate(Rate rate) throws Exception;

	public void deleteRate(Long rateId) throws Exception;

}
