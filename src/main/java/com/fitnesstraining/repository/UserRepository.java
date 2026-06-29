package com.fitnesstraining.repository;


import com.fitnesstraining.domain.Session;
import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final Map<Long, User> userStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User save(User user) {
        assignIdIfRequired(user);
        userStorage.put(user.getId(), user);
        return user;
    }

    private void assignIdIfRequired(User user) {
        if (user.getId() == null || user.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            user.setId(id);
        }
    }

    public User findById(Long id) {
        try {
            checkUserExist(id);
        } catch (UserNotFoundException ignored) { }
        return userStorage.get(id);
    }

    private void checkUserExist(Long id) throws UserNotFoundException {
        if (!existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public boolean existsById(Long id) {
        return userStorage.containsKey(id);
    }

    public void deleteById(Long id) {
        userStorage.remove(id);
    }

    public boolean existsByUsername(String username) {
        User user = userStorage.values().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        return user != null;
    }
}
