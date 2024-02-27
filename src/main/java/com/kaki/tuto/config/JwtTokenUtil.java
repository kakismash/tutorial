package com.kaki.tuto.config;

import com.kaki.tuto.exceptions.NotFoundException;
import com.kaki.tuto.model.Role;
import com.kaki.tuto.model.User;
import com.kaki.tuto.repo.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// The JwtTokenUtil class is used to generate and validate JWT tokens.
// The class is annotated with @Component, which means that it is a Spring bean that is automatically registered in the Spring application context.
// The class has a constructor that takes a UserRepository as a parameter.
// The UserRepository is used to retrieve user details from the database.
@Component
public class JwtTokenUtil {

    // The LoggerFactory is a utility class that is part of the SLF4J API.
    // It is used to create a logger instance for a specific class.
    // The getLogger() method of the LoggerFactory class is used to create a logger instance.
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // The @Value annotation is used to inject values from properties files into Spring beans.
    // The annotation can be used to inject values into fields, methods, or constructor parameters.
    // The annotation takes a single parameter, which is the key of the property to be injected.
    // The key is specified using the SpEL expression ${key}.
    @Value("${jwt.secret}")
    private String jwtSecret;

    // The @Value annotation is used to inject values from properties files into Spring beans.
    // The annotation can be used to inject values into fields, methods, or constructor parameters.
    // The annotation takes a single parameter, which is the key of the property to be injected.
    // The key is specified using the SpEL expression ${key}.
    @Value("${jwt.expirationMls}")
    private int jwtExpirationMs;

    // The UserRepository is used to retrieve user details from the database.
    private final UserRepository userRepository;

    // The constructor takes a UserRepository as a parameter and initializes the userRepository field.
    // The UserRepository is used to retrieve user details from the database.
    // The @Autowired annotation is used to automatically inject an instance of the UserRepository into the JwtTokenUtil bean.
    // The annotation can be used on fields, setter methods, and constructors.
    // When the annotation is used on a field, Spring will automatically inject the UserRepository bean into the field.
    public JwtTokenUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // The generateJwtToken() method takes an Authentication object as a parameter and returns a JWT token.
    // The method is used to generate a JWT token for the authenticated user.
    // The Authentication object contains the details of the authenticated user, including the username and authorities.
    // The method first retrieves the UserDetailsImpl object from the Authentication object.
    // The UserDetailsImpl object contains the details of the authenticated user, including the username, password, and authorities.
    public String generateJwtToken(Authentication auth) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new NotFoundException("User not Found"));

        Claims claims = Jwts.claims();
        claims.put("firstName", user.getFirstname());
        claims.put("lastName", user.getLastname());
//        claims.put("address", AddressDto.fromEntity(user.getAddress()));
        claims.put("roles", user.getRoles().stream().map(Role::getName).toList());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // The key() method is used to create a secret key from the JWT secret.
    // The method first removes any non-alphanumeric characters from the JWT secret using the replaceAll() method.
    // The method then decodes the JWT secret using the Decoders.BASE64.decode() method.
    // The method then creates a secret key from the decoded JWT secret using the Keys.hmacShaKeyFor() method.
    private Key key() {
        String jwt = jwtSecret.replaceAll("[^A-Za-z0-9+/=]", "");
        byte[] bytes = Decoders.BASE64.decode(jwt);

        return Keys.hmacShaKeyFor(bytes);
    }

    // The extractEmailFromToken() method takes a JWT token as a parameter and returns the email address of the user.
    public String extractEmailFromToken(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // The generateTokenForEmail() method takes an email address as a parameter and returns a JWT token.
    // The method is used to generate a JWT token for the specified email address.
    // The method first retrieves the user details from the database using the UserRepository.
    // The method then creates a Claims object and sets the user details as claims in the object.
    // The method then creates a JWT token using the Jwts.builder() method and signs the token using the secret key.
    public String generateTokenForEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not Found"));

        Claims claims = Jwts.claims();
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
//        claims.put("address", AddressDto.fromEntity(user.getAddress()));
        claims.put("roles", user.getRoles().stream().map(Role::getName).toList());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // The validateToken() method takes a JWT token as a parameter and returns a boolean value.
    // The method is used to validate the specified JWT token.
    // The method first parses the JWT token using the Jwts.parser() method and the secret key.
    // The method then checks if the token has expired using the Claims.getExpiration() method.
    // If the token has expired, the method returns false.
    // If the token has not expired, the method returns true.
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // The validateJwtToken() method takes a JWT token as a parameter and returns a boolean value.
    // The method is used to validate the specified JWT token.
    // The method first parses the JWT token using the Jwts.parser() method and the secret key.
    // The method then checks if the token has expired using the Claims.getExpiration() method.
    // If the token has expired, the method returns false.
    // If the token has not expired, the method returns true.
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


}
