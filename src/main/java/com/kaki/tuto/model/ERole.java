package com.kaki.tuto.model;

public enum ERole {

    ROLE_OWNER,
    ROLE_TENANT,
    ROLE_RESIDENT,
    ROLE_EMPLOYEE,
    ROLE_SECURITY,
    ROLE_MANAGER,
    ROLE_SUPERVISOR,
    ROLE_ADMIN;

    public static ERole fromString(String name) {
        for (ERole role : ERole.values()) {
            if (role.name().equals(name)) {
                return role;
            }
        }
        return null;
    }

}
