package org.simbir_soft.braim_challenge.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.CrudService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {
    private final ApplicationContext context;

    @Around("@annotation(org.simbir_soft.braim_challenge.aspect.annotation.ExistingId) &&" +
            "@annotation(annotation)")
    public Object validateId(ProceedingJoinPoint joinPoint, ExistingId annotation) throws Throwable {
        CrudService<?, Long> validator = context.getBean(annotation.validator());

        Long id = (Long) joinPoint.getArgs()[0];

        if (!validator.existsById(id)) {
            throw new DataMissingException();
        }
        return joinPoint.proceed();
    }
}
