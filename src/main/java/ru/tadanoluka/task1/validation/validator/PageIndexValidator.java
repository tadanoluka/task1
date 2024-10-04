package ru.tadanoluka.task1.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tadanoluka.task1.validation.annotation.PageIndex;

public class PageIndexValidator implements ConstraintValidator<PageIndex, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value >= 0;
    }
}
