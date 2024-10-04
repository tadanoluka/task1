package ru.tadanoluka.task1.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.tadanoluka.task1.validation.validator.PageSizeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PageSizeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageSize {
    String message() default "{validation.pageSize}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
