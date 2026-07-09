package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.User;


public interface UserService {

    boolean existsByUsername(String finalUsername);

    User findByUsername(String username);

    User getById(Long id);

    void setActive(Long id, boolean isActive);

    void newPassword(Long id, String newPassword);

}
