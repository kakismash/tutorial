package com.kaki.tuto.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean isAdmin() {
        return this.name == ERole.ROLE_ADMIN;
    }

    public boolean isSupervisor() {
        return this.name == ERole.ROLE_SUPERVISOR;
    }

    public boolean isManager() {
        return this.name == ERole.ROLE_MANAGER;
    }

    public boolean isSecurity() {
        return this.name == ERole.ROLE_SECURITY;
    }

    public boolean isEmployee() {
        return this.name == ERole.ROLE_EMPLOYEE;
    }

    public boolean isResident() {
        return this.name == ERole.ROLE_RESIDENT;
    }

    public boolean isOwner() {
        return this.name == ERole.ROLE_OWNER;
    }

    public boolean isTenant() {
        return this.name == ERole.ROLE_TENANT;
    }

}
