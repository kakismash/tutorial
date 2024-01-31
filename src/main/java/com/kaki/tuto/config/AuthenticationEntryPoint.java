package com.kaki.tuto.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

// The AuthenticationEntryPoint interface is used to commence an authentication scheme.
// The commence() method is called whenever an authentication exception occurs.
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    // The LoggerFactory is a utility class that is part of the SLF4J API.
    // It is used to create a logger instance for a specific class.
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    // The commence() method is called whenever an authentication exception occurs.
    // It sends an error response to the client.
    // The method takes three parameters:
    // - HttpServletRequest request: the request that was made
    // - HttpServletResponse response: the response that will be sent
    // - AuthenticationException authException: the authentication exception that occurred
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
