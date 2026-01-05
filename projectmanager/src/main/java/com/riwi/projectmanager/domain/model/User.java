package com.riwi.projectmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    private final UUID id;
    private final String username;
    private final String email;
    private final String password;
}
