package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.User;


public interface UserService {

    User create(User coach);

    User findByUsername(String username);

    User getById(Long id);

    User update(User user);

    void setActive(Long id, boolean isActive);

    void newPassword(Long id, String newPassword);

    boolean existsByUsername(String finalUsername);

}
