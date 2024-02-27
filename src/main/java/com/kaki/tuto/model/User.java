package com.kaki.tuto.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // ManyToMany with Role
    // FetchType.EAGER: load all roles when loading the user
    // CascadeType.ALL: propagate all operations (persist, remove, refresh, merge, detach) to the related entities
    // targetEntity: the entity that is the target of the association
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Role.class)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @ToString.Exclude
    private Collection<Role> roles = new LinkedHashSet<>();

    public void addRole(Role role) {

        if (role == null) {
            return;
        }

        if (roles == null) {
            roles = new LinkedHashSet<>();
        }

        if (roles.contains(role)) {
            return;
        }

        roles.add(role);
    }

    public void removeRole(Role role) {

        if (role == null) {
            return;
        }

        if (roles == null) {
            return;
        }

        if (!roles.contains(role)) {
            return;
        }

        roles.remove(role);
    }


}
