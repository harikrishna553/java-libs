package com.sample.app.model;

public class Product4 {
	private String manufacturingDateString;
	private String expiryDateString;

	public Product4() {
	}

	public Product4(String manufacturingDateString, String expiryDateString) {
		this.manufacturingDateString = manufacturingDateString;
		this.expiryDateString = expiryDateString;
	}

	public String getManufacturingDateString() {
		return manufacturingDateString;
	}

	public void setManufacturingDateString(String manufacturingDateString) {
		this.manufacturingDateString = manufacturingDateString;
	}

	public String getExpiryDateString() {
		return expiryDateString;
	}

	public void setExpiryDateString(String expiryDateString) {
		this.expiryDateString = expiryDateString;
	}

	@Override
	public String toString() {
		return "Product4 [manufacturingDateString=" + manufacturingDateString + ", expiryDateString=" + expiryDateString
				+ "]";
	}

}
