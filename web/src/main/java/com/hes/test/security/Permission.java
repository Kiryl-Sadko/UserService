package com.hes.test.security;

public enum Permission {

    USER_LIST("user:list"),
    USER_DETAILS("user:details"),
    USER_CREATE("user:create"),
    USER_EDIT("user:edit");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
