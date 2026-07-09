package com.fitnesstraining.service.abstraction;


public interface TraineeService extends UserService {

    void delete(Long id);

    void deleteByUsername(String username);
}
