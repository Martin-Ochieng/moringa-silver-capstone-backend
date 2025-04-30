package com.example.demo.util.inputvalidation.validstringlist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ListValueValidator implements ConstraintValidator<ListValue, String> {

    private String[] allowedValues;

    @Override
    public void initialize(ListValue constraintAnnotation) {
        allowedValues = constraintAnnotation.allowedValues().clone();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are handled separately if needed
        }

        return Arrays.asList(allowedValues).contains(value);
    }
}
