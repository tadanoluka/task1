package ru.tadanoluka.task1.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.tadanoluka.task1.validation.validator.PostcodeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PostcodeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Postcode {
    String message() default "{validation.postcode}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
