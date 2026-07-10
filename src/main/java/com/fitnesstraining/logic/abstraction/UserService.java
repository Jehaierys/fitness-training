package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.User;


public interface UserService {

    User create(User coach);

    User findByUsername(String username);

    User getById(Long id);

    User update(User user);

    void newPassword(Long id, String newPassword);

    boolean existsByUsername(String finalUsername);

}
