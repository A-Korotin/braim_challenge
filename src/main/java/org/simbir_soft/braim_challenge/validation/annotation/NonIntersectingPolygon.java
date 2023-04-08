package org.simbir_soft.braim_challenge.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.simbir_soft.braim_challenge.validation.PolygonValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PolygonValidator.class})
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NonIntersectingPolygon {
    String message() default "Invalid area points";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
