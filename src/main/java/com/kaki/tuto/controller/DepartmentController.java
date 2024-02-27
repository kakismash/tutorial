package com.kaki.tuto.controller;

import com.google.gson.JsonObject;
import com.kaki.tuto.dto.response.ResponseSuccessObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

    @GetMapping
    public ResponseEntity<?> getDepartments() {
        ResponseSuccessObject responseSuccessObject = new ResponseSuccessObject(
                "Departments retrieved successfully",
                "200",
                null
        );
        return ResponseEntity.ok(responseSuccessObject);
    }

}
