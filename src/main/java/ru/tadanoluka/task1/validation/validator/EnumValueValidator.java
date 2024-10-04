package ru.tadanoluka.task1.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tadanoluka.task1.validation.annotation.EnumValue;

import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .anyMatch(e -> e.equalsIgnoreCase(value));
    }
}
