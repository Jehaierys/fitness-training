package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.User;

public interface UserService {

    User create(User user);

    User getById(Long id);

    User update(User user);

    void delete(Long id);

    boolean existsByUsername(String finalUsername);

    void setActive(Long id, boolean isActive);

    void checkCredentials(String username, String password);
}
