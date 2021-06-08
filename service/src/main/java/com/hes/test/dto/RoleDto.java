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

    public static RoleDto formRole(Role role) {
        Long id = role.getId();

        if (Arrays.stream(RoleDto.values())
                .anyMatch(roleDto -> roleDto.getId().equals(id))) {

            return Arrays.stream(RoleDto.values())
                    .filter(roleDto -> roleDto.getId().equals(id))
                    .findFirst()
                    .orElseThrow();
        }
        return USER;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
