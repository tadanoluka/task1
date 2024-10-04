package ru.tadanoluka.task1.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tadanoluka.task1.validation.annotation.PageSize;

public class PageSizeValidator implements ConstraintValidator<PageSize, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value >= 1;
    }
}
