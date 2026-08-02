package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.dto.request.trainee.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.request.trainee.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.response.trainee.GetTraineeResponse;
import com.fitnesstraining.domain.dto.response.trainee.RegisterTraineeResponse;
import com.fitnesstraining.domain.dto.response.trainee.UpdateTraineeResponse;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.mapper.TraineeMapper;
import com.fitnesstraining.repository.TraineeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.fitnesstraining.utils.ExceptionSuppliers.TraineeNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class TraineeService {

    private final TraineeRepository repository;
    private final TraineeMapper mapper;
    private final PasswordEncoder passwordEncoder;


    public RegisterTraineeResponse register(RegisterTraineeRequest request) {

        final UUID traineeUuid;
        Trainee trainee;
        final RegisterTraineeResponse response;


        traineeUuid = UUID.randomUUID();
        log.info("Signing up new trainee: {} {}, birth date: {}, address: {}, process's UUID: {}",
                request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), traineeUuid);


        // todo: check user exists by username
        trainee = new Trainee();
        mapper.toEntity(request, trainee);

        trainee.setPassword(passwordEncoder.encode(request.getPassword()));

        trainee.setActive(true);

        trainee = repository.create(trainee);

        response = mapper.toRegisterTraineeResponse(trainee);


        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}",
                request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), trainee.getId(), traineeUuid);


        return response;
    }


    public UpdateTraineeResponse update(UpdateTraineeRequest request) {

        final UUID transactionUuid;
        Trainee trainee;
        final UpdateTraineeResponse response;


        // todo: message
        transactionUuid = UUID.randomUUID();
        log.info("Updating trainee: {} {}, attempt's UUID: {}",
                request.getFirstName(), request.getLastName(), transactionUuid);


        // todo: check username
        trainee = repository.findByUsername(request.getUsername());

        // todo: check whether new username is taken
        trainee.setUsername(request.getUsername());

        mapper.toEntity(request, trainee);

        repository.update(trainee);

        response = mapper.toUpdateTraineeResponse(trainee);


        // todo: message
        log.info("Successfully updated trainee: {} {}, userId: {} process's UUID: {}",
                request.getFirstName(), request.getLastName(), trainee.getId(), transactionUuid);

        return response;
    }


    public GetTraineeResponse findByUsername(String username) {
        return mapper.toGetTraineeResponse(repository.findByUsername(username));
    }

    // todo: Trainee cannot delete another Trainee's account
    @Transactional
    public void deleteByUsername(String username) {
        final Trainee trainee = repository.findByUsername(username);
        repository.delete(trainee);
    }




    public Trainee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(TraineeNotFound("Trainee not found with id: " + id));
    }

    public Trainee update(User trainee) {
        return repository.update((Trainee) trainee);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            log.warn("Trainee not found with id: {} to be deleted", id);
        } else {
            repository.deleteById(id);
        }
    }
    public boolean existsByUsername(String username) {
        return repository.existByUsername(username);
    }

}
