package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.User;


public interface UserService {

    User create(User coach);

    User findByUsername(String username);

    User update(User user);

    boolean existsByUsername(String finalUsername);

}
