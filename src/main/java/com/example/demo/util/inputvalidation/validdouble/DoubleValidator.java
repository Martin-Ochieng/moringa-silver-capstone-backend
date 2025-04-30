package com.example.demo.util.inputvalidation.validdouble;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


@SuppressWarnings("java:S1186") // Disable "Inheritance tree of class should not be too deep" for this class
public class DoubleValidator implements ConstraintValidator<ValidDouble, Double> {
    @Override
    public void initialize(ValidDouble constraintAnnotation) {}

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null;
    }
}
