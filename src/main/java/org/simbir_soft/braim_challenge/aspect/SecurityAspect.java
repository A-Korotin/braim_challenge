package org.simbir_soft.braim_challenge.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.simbir_soft.braim_challenge.aspect.annotation.CheckAuth;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.UserRole;
import org.simbir_soft.braim_challenge.exception.AccessForbiddenException;
import org.simbir_soft.braim_challenge.repository.AccountRepository;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {
    private final AccountRepository repository;

    private Optional<Account> getCurrentAccount() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmail(name);
    }

    private boolean userIsInvalid(Long targetId) {
        Account account = getCurrentAccount().orElseThrow(AccessForbiddenException::new);

        return account.getRole() != UserRole.ADMIN || !account.getId().equals(targetId);
    }

    @Around("@annotation(org.simbir_soft.braim_challenge.aspect.annotation.CheckAuth)")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        Long targetId = (Long) joinPoint.getArgs()[0];
        if (userIsInvalid(targetId)) {
            throw new AccessForbiddenException();
        }

        return joinPoint.proceed();
    }
}
