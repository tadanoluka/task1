package ru.tadanoluka.task1.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tadanoluka.task1.validation.annotation.Postcode;

public class PostcodeValidator implements ConstraintValidator<Postcode, String> {

    public static final String POSTCODE_REGEX_PATTERN = "^\\d{6}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(POSTCODE_REGEX_PATTERN);
    }
}
