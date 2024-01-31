package com.kaki.tuto.config;

import com.kaki.tuto.model.User;
import com.kaki.tuto.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JwtUserDetailService implements UserDetailsService {

    // The @RequiredArgsConstructor annotation is used to generate a constructor with required fields.
    // The constructor is generated for the final fields of the class.
    // Otherwise, it will require to create a constructor manually or use the @Autowired annotation.
    private final UserRepository userRepository;

    // The loadUserByUsername() method is used to load a user by username.
    // The method takes a username as a parameter and returns a UserDetails object.
    // The method is annotated with @Transactional, which means that it is executed within a transaction.
    // The @Transactional annotation is used to specify that the method is executed within a transaction.
    // The method first retrieves the user from the database using the UserRepository.
    // If the user is not found, the method throws a UsernameNotFoundException.
    // If the user is found, the method returns a UserDetailsImpl object that wraps the user details.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    // The loadUserById() method is used to load a user by ID.
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id: " + id)
        );

        return UserDetailsImpl.build(user);
    }

}
