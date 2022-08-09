package com.sample.app.entity;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	private Integer accountId;

	private Double principal;
	private Double timeInYears;
	private Double rateOfIntrest;

	@Formula(value = "(principal * timeInYears * rateOfIntrest ) /100")
	private Double interestToPay;

	public Account(Integer accountId, Double principal, Double timeInYears, Double rateOfIntrest) {
		this.accountId = accountId;
		this.principal = principal;
		this.timeInYears = timeInYears;
		this.rateOfIntrest = rateOfIntrest;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getRateOfIntrest() {
		return rateOfIntrest;
	}

	public void setRateOfIntrest(Double rateOfInrest) {
		this.rateOfIntrest = rateOfInrest;
	}

	public Double getTimeInYears() {
		return timeInYears;
	}

	public void setTimeInYears(Double timeInYears) {
		this.timeInYears = timeInYears;
	}

	public Double getInterestToPay() {
		return interestToPay;
	}

	public void setInterestToPay(Double interestToPay) {
		this.interestToPay = interestToPay;
	}

}
