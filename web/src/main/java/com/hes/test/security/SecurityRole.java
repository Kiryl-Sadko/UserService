package com.hes.test.security;

import com.hes.test.dto.RoleDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.hes.test.security.Permission.*;

public enum SecurityRole {

    ADMIN(Set.of(USER_LIST,
            USER_DETAILS,
            USER_CREATE,
            USER_EDIT)
    ),

    USER(Set.of(USER_LIST,
            USER_DETAILS));

    private final Set<Permission> permissions;

    SecurityRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    private static SecurityRole fromRoleDto(RoleDto roleDto) {
        return SecurityRole.valueOf(roleDto.getName());
    }

    public static Set<SimpleGrantedAuthority> getAuthorities(RoleDto roleDto) {
        SecurityRole securityRole = fromRoleDto(roleDto);
        return securityRole.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
