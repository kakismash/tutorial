package com.kaki.tuto.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaki.tuto.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// The UserDetails interface is used to retrieve the user's details.
// The interface is part of the Spring Security framework.
// The interface has several methods that need to be implemented, including getAuthorities(), getPassword(), and getUsername().
// The UserDetailsImpl class implements the UserDetails interface.
public class UserDetailsImpl implements UserDetails {

    // The @Serial annotation is used to declare the serial version of a class.
    // The annotation is used to specify the serial version of a class.
    // The serial version is used to ensure that the class can be serialized and deserialized correctly.
    @Serial
    private static final long serialVersionUID = 1L;

    // The id field is used to store the user's ID.
    private final Long id;

    // The email field is used to store the user's email address.
    private final String email;

    // The password field is used to store the user's password.
    // The @JsonIgnore annotation is used to indicate that the field should be ignored during serialization and deserialization.
    @JsonIgnore
    private String password;

    // The authorities field is used to store the user's authorities.
    // The authorities are used to determine the user's access rights.
    private final Collection<? extends GrantedAuthority> authorities;

    // The constructor takes the user's ID, email, password, and authorities as parameters and initializes the fields of the class.
    public UserDetailsImpl(Long id, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // The build() method is used to create a UserDetailsImpl object from a User object.
    // The method takes a User object as a parameter and returns a UserDetailsImpl object.
    // The method first retrieves the user's authorities from the User object.
    // The method then creates a list of GrantedAuthority objects from the user's authorities.
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    // The getAuthorities() method is used to retrieve the user's authorities.
    // The method returns a collection of GrantedAuthority objects that represent the user's authorities.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
