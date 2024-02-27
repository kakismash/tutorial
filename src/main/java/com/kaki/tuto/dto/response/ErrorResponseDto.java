package com.kaki.tuto.dto.response;

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
