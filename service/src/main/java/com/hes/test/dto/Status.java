package com.hes.test.dto;

public enum Status {

    ACTIVE("active", true),
    INACTIVE("inactive", false);

    private final String name;
    private final boolean isActive;

    Status(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    public static Status fromBoolean(boolean isActive) {
        if (isActive) {
            return ACTIVE;
        }
        return INACTIVE;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }
}
