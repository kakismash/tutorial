package com.kaki.tuto.controller;

import com.kaki.tuto.dto.response.ErrorResponseDto;
import com.kaki.tuto.dto.RegisterUserDto;
import com.kaki.tuto.dto.ResponseUserDto;
import com.kaki.tuto.dto.UpdateUserDto;
import com.kaki.tuto.exceptions.MissingParamException;
import com.kaki.tuto.repo.UserRepository;
import com.kaki.tuto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserRestController.class.getName());

    @GetMapping()
    public ResponseEntity<Page<ResponseUserDto>> findAllUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        logger.info("UserRestController.findAllUsers");
        return ResponseEntity.ok(userService.findAllUsers(email, firstname, lastname, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> findUserById(@PathVariable Long id) {
        logger.info("UserRestController.findUserById");
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("UserRestController.deleteUser");
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user) {
        logger.info("UserRestController.updateUser");
        return ResponseEntity.ok(userService.saveUser(id, user));
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody RegisterUserDto user) {
        logger.info("UserRestController.createUser");
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (MissingParamException e) {
            logger.error("Error creating user: {}", e.getMessage());
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage(e.getMessage());
            errorResponseDto.setStatus(400);
            errorResponseDto.setSource("Validating user creation");
            return ResponseEntity.badRequest().body(errorResponseDto);
        }
    }

    @GetMapping("/email/{emailWord}")
    public ResponseEntity<?> findUserByEmail(@PathVariable("emailWord") String email) {
        logger.info("UserRestController.findUserByEmail");
        return ResponseEntity.ok(userService.findAllByEmailContainingIgnoreCase(email));
    }

}
