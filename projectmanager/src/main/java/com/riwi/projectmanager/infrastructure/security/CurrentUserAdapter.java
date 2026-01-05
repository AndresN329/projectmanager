package com.riwi.projectmanager.infrastructure.security;

import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.ports.out.CurrentUserPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserAdapter implements CurrentUserPort {

    @Override
    public UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedActionException("Unauthenticated user");
        }

        Object principal = auth.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)) {
            throw new UnauthorizedActionException("Unauthenticated user");
        }

        if (principal instanceof UUID uuid) return uuid;
        if (principal instanceof String s) return UUID.fromString(s);

        throw new IllegalStateException("Invalid principal type: " + principal.getClass());
    }
}
