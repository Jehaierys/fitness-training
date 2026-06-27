package com.fitnesstraining.service;


import com.fitnesstraining.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {

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

    public boolean existsByUsername(String username) {
        return userStorage.values().stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }


    public User update(User user) {
        if (userRepository.existById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }




    public User update(User user) {
        if (userRepository.existById(user.getId())) }
return userRepository.save(user);
} else {
        throw new UserNotFoundException();
}
        }
public void delete(Long id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("User not found with id: " + id);
    }
    userRepository.deleteById(id);}
}