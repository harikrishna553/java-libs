package com.sample.app.converters;

import com.sample.app.enums.Gender;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Character> {

	@Override
	public Character convertToDatabaseColumn(Gender gender) {
		if (gender == null) {
			return null;
		}

		return gender.getCode();
	}

	@Override
	public Gender convertToEntityAttribute(Character genderCode) {
		if (genderCode == null) {
			return null;
		}

		return Gender.fromCode(genderCode);
	}
}