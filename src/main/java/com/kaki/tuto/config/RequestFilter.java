package com.kaki.tuto.config;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collection;

// The RequestFilter class is used to intercept incoming HTTP requests and extract the JWT token from the Authorization header.
// The class is annotated with @Component, which means that it is a Spring bean that is automatically registered in the Spring application context.
// The class extends the OncePerRequestFilter class, which is a filter that guarantees that the doFilterInternal() method is only called once per request.
// The class has a constructor that takes a JwtTokenUtil and a JwtUserDetailService as parameters.
@Component
public class RequestFilter extends OncePerRequestFilter {

    // The JwtTokenUtil is used to generate and validate JWT tokens.
    // The JwtUserDetailService is used to load user details from the database.
    // The @Autowired annotation is used to automatically inject instances of the JwtTokenUtil and JwtUserDetailService beans into the RequestFilter bean.
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // The LoggerFactory is a utility class that is part of the SLF4J API.
    // It is used to create a logger instance for a specific class.
    // The getLogger() method of the LoggerFactory class is used to create a logger instance.
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);


    @Autowired
    private JwtUserDetailService userDetailsService;

    // The doFilterInternal() method is called for each incoming HTTP request.
    // The method takes three parameters:
    // - HttpServletRequest request: the request that was made
    // - HttpServletResponse response: the response that will be sent
    // - FilterChain filterChain: the filter chain that the request is part of
    // The method is used to intercept incoming HTTP requests and extract the JWT token from the Authorization header.
    // The method first extracts the JWT token from the Authorization header of the request.
    // If a JWT token is found and it is valid, the method retrieves the user details from the database using the JwtUserDetailService.
    // The method then creates an authentication token using the user details and sets it in the SecurityContextHolder.
    // The method then calls the doFilter() method of the filter chain to continue processing the request.
    // If an exception occurs, the method logs an error message.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
                String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                Collection<GrantedAuthority> d =  authentication.getAuthorities();
                logger.info("Authorities: {}", d);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }

    // The parseJwt() method takes an HttpServletRequest as a parameter and returns the JWT token from the Authorization header.
    // The method is used to extract the JWT token from the Authorization header of the request.
    // The method first retrieves the Authorization header from the request.
    // If the header is not null and it starts with "Bearer ", the method returns the substring of the header after "Bearer ".
    // Otherwise, the method returns null.
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
