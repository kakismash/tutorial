package com.kaki.tuto.controller;

import com.kaki.tuto.config.JwtTokenUtil;
import com.kaki.tuto.config.UserDetailsImpl;
import com.kaki.tuto.dto.JwtResponse;
import com.kaki.tuto.dto.LoginRequestDto;
import com.kaki.tuto.dto.ResponseUserDto;
import com.kaki.tuto.model.User;
import com.kaki.tuto.repo.UserRepository;
import com.kaki.tuto.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token = jwtTokenUtil.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            Collection<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            User condoUser = userRepository.findById(userDetails.getId()).get();

            return ResponseEntity.ok(new JwtResponse(
                    ResponseUserDto.fromEntity(condoUser),
                    token));

        } catch (DisabledException e) {
            return ResponseEntity.badRequest().body("User is disabled");
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

    }
}
