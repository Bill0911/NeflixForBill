package com.example.netflix.entity;

import java.util.List;

public enum Role {
    JUNIOR("Junior manager with moderate access", List.of("READ_CONTENT", "MANAGE_PROFILES")),
    MEDIOR("Medior manager with extended access", List.of("READ_CONTENT", "MANAGE_PROFILES", "VIEW_ANALYTICS")),
    SENIOR("Senior manager with full access", List.of("READ_CONTENT", "MANAGE_PROFILES", "VIEW_ANALYTICS", "ADMIN_PRIVILEGES"));

    private final String description;
    private final List<String> permissions;

    Role(String description, List<String> permissions) {
        this.description = description;
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}
