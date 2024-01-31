package com.kaki.tuto.service;

import com.kaki.tuto.config.UserDetailsImpl;
import com.kaki.tuto.exceptions.NotFoundException;
import com.kaki.tuto.model.User;
import com.kaki.tuto.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // The LoggerFactory is a utility class that is part of the SLF4J API.
    // It is used to create a logger instance for a specific class.
    // The getLogger() method of the LoggerFactory class is used to create a logger instance.
    private final Logger logger = LoggerFactory.getLogger(AuthService.class.getName());

    private final UserRepository userRepository;

    // Get the authenticated user from the SecurityContextHolder and return it
    // The SecurityContextHolder is a holder for the security context of the current thread.
    // The security context is used to store the details of the principal currently interacting with the application.
    // The security context is stored in a ThreadLocal object, which means that it is specific to the current thread.
    // The SecurityContextHolder is a helper class that provides access to the security context.
    public User getAuthUser() {
        logger.info("AuthService.getAuthUser"); // Log the method name

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication(); // Get the authentication token from the SecurityContextHolder

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); // Get the principal from the authentication token

        return userRepository.findById(userDetails.getId()).orElseThrow(); // Get the user details from the repository using the user ID from the principal. If the user is not found, throw an exception.
    }

}
