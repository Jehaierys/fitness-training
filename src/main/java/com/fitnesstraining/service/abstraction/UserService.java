package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User create(User user);

    public User getById(Long id);

    public User update(User user) throws UserNotFoundException;

    public void delete(Long id);

    boolean existsByUsername(String finalUsername);
}
