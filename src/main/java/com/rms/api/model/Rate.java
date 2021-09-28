package com.rms.api.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {

	private Long rateId;
	private String rateDescription;
	private Date rateEffectiveDate;
	private Date rateExpirationDate;
	private Integer amount;
	private Surcharge surcharge;

}
