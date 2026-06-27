package com.fitnesstraining.service;


import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.UserRepository;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User update(User user) throws UserNotFoundException {
        if (userRepository.existById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}