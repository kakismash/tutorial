package com.kaki.tuto.service;

import com.kaki.tuto.dto.RegisterUserDto;
import com.kaki.tuto.dto.ResponseUserDto;
import com.kaki.tuto.dto.UpdateUserDto;
import com.kaki.tuto.exceptions.MissingParamException;
import com.kaki.tuto.exceptions.NotFoundException;
import com.kaki.tuto.model.User;
import com.kaki.tuto.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {

        // This annotation is useful because it helps to eliminate the boilerplate code that is usually written in the constructor.
        // The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor with required arguments.
        // The constructor is generated for the final fields of the class.
        // Otherwise, it will require to create a constructor manually or use the @Autowired annotation.
        private final UserRepository userRepository;

        private User getUserByEmail(String email) {
            return userRepository.findByEmail(email).orElseThrow();
        }

        protected User getUserById(Long id) {
            return userRepository.findById(id).orElseThrow();
        }

        protected User saveUser(User user) {
            return userRepository.save(user);
        }

        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }

        // Get all users with pagination
        // The Pageable interface is used to add pagination to the query methods.
        // It provides methods to retrieve information about the number of the current page, the total number of pages, and the total number of records.
        protected Page<User> getAllUsers(String email, String firstname, String lastname, Pageable pageable) {
            return userRepository.findAll(email, firstname, lastname, pageable);
        }

        public Page<ResponseUserDto> findAllUsers(String email, String firstname, String lastname, Pageable pageable) {
            Page<User> users = getAllUsers(email, firstname, lastname, pageable);
            return users.map(ResponseUserDto::fromEntity);
        }

        public ResponseUserDto findUserById(Long id) {
            return ResponseUserDto.fromEntity(getUserById(id));
        }

        public ResponseUserDto findUserByEmail(String email) {
            return ResponseUserDto.fromEntity(getUserByEmail(email));
        }

        public ResponseUserDto saveUser(Long userId, UpdateUserDto userDTO) {
            User user = getUserById(userId);

            if (user == null) {
                throw new NotFoundException("User not found");
            }

            if (StringUtils.isNoneEmpty(userDTO.getFirstname())) {
                user.setFirstname(userDTO.getFirstname());
            }

            if (StringUtils.isNoneEmpty(userDTO.getLastname())) {
                user.setLastname(userDTO.getLastname());
            }

            user.setId(userId);
            return ResponseUserDto.fromEntity(saveUser(user));
        }

        public ResponseUserDto createUser(RegisterUserDto userDTO) throws MissingParamException {
            User user = userDTO.toEntity();
            return ResponseUserDto.fromEntity(saveUser(user));
        }

        public Collection<ResponseUserDto> findAllByEmailContainingIgnoreCase(String email) {
            Collection<User> users = userRepository.findAllByEmailContainingIgnoreCase(email);

            Collection<ResponseUserDto> usersDTO = new ArrayList<>();

            for (User user : users) {
                usersDTO.add(ResponseUserDto.fromEntityWithoutPassword(user));
            }

            return usersDTO;
        }

}
