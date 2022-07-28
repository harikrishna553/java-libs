package com.sample.app.dto;

public sealed abstract class Address permits TemporaryAddress,PermanentAddress {

	public abstract String getAddress();

}
