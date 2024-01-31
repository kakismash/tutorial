package com.kaki.tuto.dto;

import com.kaki.tuto.model.ERole;
import lombok.Value;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link com.kaki.tuto.model.User}
 */
@Value
public class UpdateUserDto implements Serializable {
    String firstname;
    String lastname;
    String email;
    String password;
    Collection<ERole> roles;

}