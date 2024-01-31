package com.kaki.tuto.dto;

import com.kaki.tuto.exceptions.MissingParamException;
import com.kaki.tuto.model.User;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.tuto.model.User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto implements Serializable {
    String firstname;
    String lastname;
    String email;
    String password;

    public User toEntity() {
        User user = new User();

        if (StringUtils.isNoneEmpty(this.email)) {
            user.setEmail(this.email);
        } else {
            throw new MissingParamException("Email is required");
        }

        if (StringUtils.isNoneEmpty(this.password)) {
            user.setPassword(this.password);
        } else {
            throw new MissingParamException("Password is required");
        }

        if (StringUtils.isNoneEmpty(this.firstname)) {
            user.setFirstname(this.firstname);
        } else {
            throw new MissingParamException("Firstname is required");
        }

        if (StringUtils.isNoneEmpty(this.lastname)) {
            user.setLastname(this.lastname);
        } else {
            throw new MissingParamException("Lastname is required");
        }

        return user;
    }

}