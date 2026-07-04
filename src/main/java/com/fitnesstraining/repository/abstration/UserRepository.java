package com.fitnesstraining.repository.abstration;

import com.fitnesstraining.domain.User;

import java.util.Optional;

public interface UserRepository {

    User create(User user);

    User update(User user);

    void delete(User user);

    void deleteById(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsById(Long id);

    boolean existByUsername(String username);

}
