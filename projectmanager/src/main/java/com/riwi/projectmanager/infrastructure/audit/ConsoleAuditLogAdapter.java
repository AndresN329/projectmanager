package com.riwi.projectmanager.infrastructure.audit;

import com.riwi.projectmanager.domain.ports.out.AuditLogPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsoleAuditLogAdapter implements AuditLogPort {
    @Override
    public void register(String action, UUID entityId) {
        System.out.println("[AUDIT] " + action + " -> " + entityId);
    }
}