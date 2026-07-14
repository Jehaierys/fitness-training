package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.User;


public interface UserService {


    User findByUsername(String username);

    User update(User user);

    boolean existsByUsername(String finalUsername);

}
