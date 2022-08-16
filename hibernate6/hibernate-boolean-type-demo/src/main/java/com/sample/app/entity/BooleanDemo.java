package com.sample.app.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "boolean_demo")
public class BooleanDemo {

	@Id
	private Integer id;

	// this will get mapped to CHAR or NCHAR with a conversion
	@Convert(converter = org.hibernate.type.YesNoConverter.class)
	Boolean yesNoField;

	// this will get mapped to CHAR or NCHAR with a conversion
	@Convert(converter = org.hibernate.type.TrueFalseConverter.class)
	Boolean trueFalseField;

	// this will get mapped to TINYINT with a conversion
	@Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
	Boolean numericBooleanField;

	// this will be mapped to boolean type, if the database has a boolean type, else to some numeric like 
	// BIT, TINYINT or SMALLINT.
	Boolean implicitField;

	public BooleanDemo(Integer id, Boolean yesNoField, Boolean trueFalseField, Boolean numericBooleanField,
			Boolean implicitField) {
		this.id = id;
		this.yesNoField = yesNoField;
		this.trueFalseField = trueFalseField;
		this.numericBooleanField = numericBooleanField;
		this.implicitField = implicitField;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getYesNoField() {
		return yesNoField;
	}

	public void setYesNoField(Boolean yesNoField) {
		this.yesNoField = yesNoField;
	}

	public Boolean getTrueFalseField() {
		return trueFalseField;
	}

	public void setTrueFalseField(Boolean trueFalseField) {
		this.trueFalseField = trueFalseField;
	}

	public Boolean getNumericBooleanField() {
		return numericBooleanField;
	}

	public void setNumericBooleanField(Boolean numericBooleanField) {
		this.numericBooleanField = numericBooleanField;
	}

	public Boolean getImplicitField() {
		return implicitField;
	}

	public void setImplicitField(Boolean implicitField) {
		this.implicitField = implicitField;
	}

}