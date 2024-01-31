package com.kaki.tuto.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDto {
    private int status;
    private String message;
    private String source;
}
