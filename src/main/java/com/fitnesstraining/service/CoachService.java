package com.fitnesstraining.service;

import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.request.coach.UpdateCoachRequest;
import com.fitnesstraining.domain.dto.response.coach.CoachDto;
import com.fitnesstraining.domain.dto.response.coach.GetCoachResponse;
import com.fitnesstraining.domain.dto.response.coach.RegisterCoachResponse;
import com.fitnesstraining.domain.dto.response.coach.UpdateCoachResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.repository.SessionTypeRepository;
import com.fitnesstraining.service.mapper.CoachMapper;
import com.fitnesstraining.service.utils.CoachSearcher;
import com.fitnesstraining.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository repository;
    private final SessionTypeRepository sessionTypeRepository;
    private final CoachMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CoachSearcher searcher;


    @Transactional
    public RegisterCoachResponse register(RegisterCoachRequest request) {

        final UUID transactionUuid;
        Coach coach;
        final RegisterCoachResponse response;


        transactionUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, attempt's UUID: {}",
                request.getFirstName(), request.getLastName(), transactionUuid);


        coach = new Coach();
        mapper.toEntity(request, coach);

        coach.setPassword(passwordEncoder.encode(request.getPassword()));

        coach.setActive(true);

        coach.setSpecialization(
                sessionTypeRepository.findAllById(request.getSpecializationIds())
        );

        // todo: check user exists by username
        coach = repository.create(coach);

        response = mapper.toRegisterCoachResponse(coach);


        log.info("Successfully created coach: {} {}, userId: {} process's UUID: {}",
                request.getFirstName(), request.getLastName(), coach.getId(), transactionUuid);

        return response;
    }


    @Transactional
    public UpdateCoachResponse update(UpdateCoachRequest request) {

        final UUID transactionUuid;
        Coach coach;
        final UpdateCoachResponse response;


        // todo: message
        transactionUuid = UUID.randomUUID();
        log.info("Updating coach: {} {}, attempt's UUID: {}",
                request.getFirstName(), request.getLastName(), transactionUuid);


        coach = repository.findByUsername(request.getUsername());

        // todo: check whether new username is taken
        coach.setUsername(request.getUsername());

        mapper.toEntity(request, coach);

        coach.setSpecialization(
                sessionTypeRepository.findAllById(request.getSpecializationIds())
        );

        coach = repository.update(coach);

        response = mapper.toUpdateCoachResponse(coach);


        // todo: message
        log.info("Successfully updated coach: {} {}, specialization: {}, userId: {} process's UUID: {}",
                request.getFirstName(), request.getLastName(), request.getSpecializationIds(), coach.getId(), transactionUuid);

        return response;
    }


    public GetCoachResponse findByUsername(String username) {
        return mapper.toGetCoachResponse(repository.findByUsername(username));
    }


    public List<CoachDto> findAvailable(Long traineeId) {
        return searcher.findAvailable(traineeId);
    }

}