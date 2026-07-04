package com.fitnesstraining.service;

import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.repository.abstration.SessionTypeRepository;
import com.fitnesstraining.service.abstraction.SessionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.SessionTypeNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSessionTypeService implements SessionTypeService {

    private final SessionTypeRepository sessionTypeRepository;

    @Override
    public SessionType getById(Long id) {
        return sessionTypeRepository.findById(id)
                .orElseThrow(SessionTypeNotFound("SessionType not found with id: " + id));
    }

    @Override
    public SessionType create(SessionType sessionType) {
        return sessionTypeRepository.create(sessionType);
    }

    @Override
    public SessionType update(SessionType sessionType) {
        return sessionTypeRepository.update(sessionType);
    }

    @Override
    public void delete(SessionType sessionType) {
        sessionTypeRepository.delete(sessionType);
    }

    @Override
    public void deleteById(Long id) {
        sessionTypeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return sessionTypeRepository.existsById(id);
    }

    @Override
    public boolean existByName(String name) {
        return sessionTypeRepository.existByName(name);
    }
}