package com.kaki.tuto.repo;

import com.kaki.tuto.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // The @Query annotation is used to define a custom query using JPQL or native SQL.
    @Query("SELECT u FROM User u " +
            "WHERE (:email IS NULL OR LOWER(u.email) LIKE CONCAT('%', LOWER(:email), '%'))  " +
            "AND (:firstname IS NULL OR LOWER(u.firstname) LIKE CONCAT('%', LOWER(:firstname), '%'))  " +
            "AND (:lastname IS NULL OR LOWER(u.lastname) LIKE CONCAT('%', LOWER(:lastname), '%')) ")
    // The @Param annotation is used to bind the method parameter to the query parameter.
    // It is used to specify the parameter name to be used in the query.
    Page<User> findAll(
            @Param("email") String email,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            Pageable pageable);

}
