package com.sample.app.model;

import java.util.Date;

public class Product3 {
	private Date manufacturingDate;
	private Date expiryDate;

	public Product3() {
	}

	public Product3(Date manufacturingDate, Date expiryDate) {
		this.manufacturingDate = manufacturingDate;
		this.expiryDate = expiryDate;
	}

	public Date getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "Product3 [manufacturingDate=" + manufacturingDate + ", expiryDate=" + expiryDate + "]";
	}

}
