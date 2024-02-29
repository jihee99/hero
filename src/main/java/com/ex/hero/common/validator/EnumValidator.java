package com.ex.hero.common.validator;

import com.ex.hero.common.annotation.Enum;
import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, java.lang.Enum> {
	@Override
	public boolean isValid(java.lang.Enum value, ConstraintValidatorContext context) {
		if (value == null) return false; // null 값 허용 여부
		Class<?> reflectionEnumClass = value.getDeclaringClass();
		return Arrays.asList(reflectionEnumClass.getEnumConstants()).contains(value);
	}
}
