package com.kaki.tuto.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.kaki.tuto.model.User}
 */
@Value
public class LoginRequestDto implements Serializable {
    String email;
    String password;
}