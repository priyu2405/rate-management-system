package com.rms.api.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rms.api.model.Rate;
import com.rms.api.utils.RateApiCommonConstants;

public class RmsValidator {

	protected List<String> validateRate(Rate rate) throws Exception {

		List<String> errors = new ArrayList<String>();
		if (Objects.isNull(rate.getRateEffectiveDate())) {
			errors.add(
					String.format(RateApiCommonConstants.FIELD_REQUIRED, RateApiCommonConstants.RATE_EFFECTIVE_DATE));
		}
		if (Objects.isNull(rate.getRateExpirationDate())) {
			errors.add(
					String.format(RateApiCommonConstants.FIELD_REQUIRED, RateApiCommonConstants.RATE_EXPIRATION_DATE));
		}
		if (Objects.isNull(rate.getAmount())) {
			errors.add(String.format(RateApiCommonConstants.FIELD_REQUIRED, RateApiCommonConstants.RATE_AMOUNT));
		}
		return errors;
	}

}
