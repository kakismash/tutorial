package com.kaki.tuto.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaki.tuto.model.ERole;
import com.kaki.tuto.model.Role;
import com.kaki.tuto.model.User;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link com.kaki.tuto.model.User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserDto implements Serializable {
    Long id;
    String firstname;
    String lastname;
    String email;
    String password;
    Collection<ERole> roles;

    public static ResponseUserDto fromEntityWithoutPassword(User user) {
        if (user == null) {
            return null;
        }

        ResponseUserDto responseUserDto = new ResponseUserDto();

        if (user.getId() != null) {
            responseUserDto.setId(user.getId());
        }

        if (StringUtils.isNotEmpty(user.getEmail())) {
            responseUserDto.setEmail(user.getEmail());
        }

        if (StringUtils.isNotEmpty(user.getFirstname())) {
            responseUserDto.setFirstname(user.getFirstname());
        }

        if (StringUtils.isNotEmpty(user.getLastname())) {
            responseUserDto.setLastname(user.getLastname());
        }

        if (user.getRoles() != null) {
            responseUserDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        }

        return responseUserDto;
    }

    public User toEntity() {
        User user = new User();

        if (StringUtils.isNoneEmpty(this.email)) {
            user.setEmail(this.email);
        } else {
            throw new IllegalArgumentException("Email is required");
        }

        if (StringUtils.isNoneEmpty(this.password)) {
            user.setPassword(this.password);
        } else {
            throw new IllegalArgumentException("Password is required");
        }

        if (StringUtils.isNoneEmpty(this.firstname)) {
            user.setFirstname(this.firstname);
        } else {
            throw new IllegalArgumentException("Firstname is required");
        }

        if (StringUtils.isNoneEmpty(this.lastname)) {
            user.setLastname(this.lastname);
        } else {
            throw new IllegalArgumentException("Lastname is required");
        }

        return user;
    }

    public static ResponseUserDto fromEntity(User user) {
        if (user == null) {
            return null;
        }

        ResponseUserDto responseUserDto = new ResponseUserDto();

        if (user.getId() != null) {
            responseUserDto.setId(user.getId());
        }

        if (StringUtils.isNotEmpty(user.getEmail())) {
            responseUserDto.setEmail(user.getEmail());
        }

        if (StringUtils.isNotEmpty(user.getFirstname())) {
            responseUserDto.setFirstname(user.getFirstname());
        }

        if (StringUtils.isNotEmpty(user.getLastname())) {
            responseUserDto.setLastname(user.getLastname());
        }

        if (StringUtils.isNotEmpty(user.getPassword())) {
            responseUserDto.setPassword(user.getPassword());
        }

        if (user.getRoles() != null) {
            responseUserDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        }

        return responseUserDto;
    }

}