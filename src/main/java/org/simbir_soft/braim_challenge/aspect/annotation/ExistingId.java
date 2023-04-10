package org.simbir_soft.braim_challenge.aspect.annotation;

import org.simbir_soft.braim_challenge.service.CrudService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExistingId {
    Class<? extends CrudService<?, Long>> validator();
}
