package com.riwi.projectmanager.infrastructure.notification;

import com.riwi.projectmanager.domain.ports.out.NotificationPort;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificationAdapter implements NotificationPort {
    @Override
    public void notify(String message) {
        System.out.println("[NOTIFY] " + message);
    }
}
