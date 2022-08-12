package com.sample.app.enums;

public enum Gender {
	MALE('M'), FEMALE('F');

	private final char code;

	Gender(char code) {
		this.code = code;
	}

	public static Gender fromCode(char genderCode) {
		if (genderCode == 'F' || genderCode == 'f') {
			return FEMALE;
		}

		if (genderCode == 'M' || genderCode == 'm') {
			return MALE;
		}

		throw new UnsupportedOperationException("The code " + genderCode + " is not supported!");
	}

	public char getCode() {
		return code;
	}
}
