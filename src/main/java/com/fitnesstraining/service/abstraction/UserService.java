package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.User;

public interface UserService {

    public User create(User user);

    public User getById(Long id);

    public User update(User user);

    public void delete(Long id);

    boolean existsByUsername(String finalUsername);
}
