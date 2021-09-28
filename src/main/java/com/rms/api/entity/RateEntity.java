package com.rms.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RATE")
public class RateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RateId", nullable = false)
	private Long rateId;

	@Column(name = "RateDescription")
	private String rateDescription;

	@Column(name = "RateEffectiveDate", nullable = false)
	private Date rateEffectiveDate;

	@Column(name = "RateExpirationDate", nullable = false)
	private Date rateExpirationDate;

	@Column(name = "Amount", nullable = false)
	private Integer amount;

}
