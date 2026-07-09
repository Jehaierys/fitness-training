package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.repository.abstration.SessionTypeRepository;
import com.fitnesstraining.logic.abstraction.SessionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.SessionTypeNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSessionTypeService implements SessionTypeService {

    private final SessionTypeRepository sessionTypeRepository;


    public SessionType getById(Long id) {
        return sessionTypeRepository.findById(id)
                .orElseThrow(SessionTypeNotFound("SessionType not found with id: " + id));
    }

    public void delete(SessionType sessionType) {
        sessionTypeRepository.delete(sessionType);
    }

    public void deleteById(Long id) {
        sessionTypeRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return sessionTypeRepository.existsById(id);
    }

    public boolean existByName(String name) {
        return sessionTypeRepository.existByName(name);
    }
}