package com.sample.app.model;

import java.util.Date;

public class Product1 {

	private Date manufacturingDate;

	public Date getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	@Override
	public String toString() {
		return "Product1 [manufacturingDate=" + manufacturingDate + "]";
	}

}
