package com.kaki.tuto.dto;

import com.kaki.tuto.model.ERole;
import com.kaki.tuto.model.User;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

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