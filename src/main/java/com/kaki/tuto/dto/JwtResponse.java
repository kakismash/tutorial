package com.kaki.tuto.dto;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Value
public class JwtResponse extends ResponseUserDto implements Serializable {
    String token;

    public JwtResponse(@NonNull ResponseUserDto condoUserResponseDto, String token) {
        if (condoUserResponseDto.getId() != null) {
            this.setId(condoUserResponseDto.getId());
        }

        if (condoUserResponseDto.getEmail() != null) {
            this.setEmail(condoUserResponseDto.getEmail());
        }

        if (condoUserResponseDto.getRoles() != null) {
            this.setRoles(condoUserResponseDto.getRoles());
        }

        if (condoUserResponseDto.getFirstname() != null) {
            this.setFirstname(condoUserResponseDto.getFirstname());
        }

        if (condoUserResponseDto.getLastname() != null) {
            this.setLastname(condoUserResponseDto.getLastname());
        }

        this.token = token;
    }
}
