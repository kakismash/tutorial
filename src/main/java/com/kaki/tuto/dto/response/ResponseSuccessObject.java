package com.kaki.tuto.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseSuccessObject {

    private String message;
    private String status;
    private Object data;
}
