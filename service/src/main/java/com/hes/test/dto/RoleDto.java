package com.hes.test.dto;

import com.hes.test.entity.Role;

import java.util.Arrays;

public enum RoleDto {

    ADMIN(1L, "ADMIN"),
    USER(2L, "USER");

    private final Long id;
    private final String name;

    RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RoleDto fromString(String role) {
        return Arrays.stream(RoleDto.values())
                .filter(roleDto -> roleDto.getName().equalsIgnoreCase(role))
                .findFirst()
                .orElse(USER);
    }

    public static RoleDto formRole(Role role) {
        return Arrays.stream(RoleDto.values())
                .filter(roleDto -> roleDto.getId().equals(role.getId()))
                .findFirst()
                .orElse(USER);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
