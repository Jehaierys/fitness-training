package com.fitnesstraining.service;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.repository.SessionTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fitnesstraining.utils.ExceptionSuppliers.SessionTypeNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionTypeService {

    private final SessionTypeRepository repository;


    public List<SessionType> findAll() {
        return repository.findAll();
    }

    public SessionType findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(SessionTypeNotFound("SessionType not found with name: " + name));
    }

    public SessionType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(SessionTypeNotFound("SessionType not found with id: " + id));
    }


    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public boolean existByName(String name) {
        return repository.existByName(name);
    }
}