package vn.unigap.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateStringValidation implements ConstraintValidator<DateString, String> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void initialize(DateString constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Null or empty values are considered valid to allow for @NotNull validation
        }

        try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern(DATE_FORMAT));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
