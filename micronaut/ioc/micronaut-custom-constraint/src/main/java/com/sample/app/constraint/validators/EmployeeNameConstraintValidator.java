package com.sample.app.constraint.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sample.app.constraint.EmployeeNameConstraint;

import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Singleton
public class EmployeeNameConstraintValidator implements ConstraintValidator<EmployeeNameConstraint, String> {

	@Override
	public boolean isValid(String value, AnnotationValue<EmployeeNameConstraint> annotationMetadata,
			io.micronaut.validation.validator.constraints.ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}

		Map<String, Object> parameters = annotationMetadata.getConvertibleValues().asMap();

		Integer minimumSize = Integer.parseInt(parameters.get("minSize").toString());
		String regEx = parameters.get("regex").toString();

		if (value.length() < minimumSize) {
			return false;
		}
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}

}