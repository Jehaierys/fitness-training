package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserResponse;
import com.fitnesstraining.domain.dto.coach.request.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachRequest;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.testUtils.Specializations;
import com.fitnesstraining.testUtils.dto.RegisterCoachRequests;
import com.fitnesstraining.testUtils.dto.UpdateCoachRequests;
import com.fitnesstraining.testUtils.entity.SessionTypes;
import com.fitnesstraining.testUtils.entity.Users;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Import;

import java.util.*;

import static com.fitnesstraining.testUtils.Specializations.cardioStrengthTraining;
import static com.fitnesstraining.testUtils.Specializations.yogaPilates;
import static com.fitnesstraining.testUtils.entity.SessionTypes.*;
import static org.junit.jupiter.api.Assertions.*;


// CHECKSTYLE.OFF
@Import(Specializations.class)
class CoachMapperTest {

    private final CoachMapper coachMapper = Mappers.getMapper(CoachMapper.class);

    @Test
    void toGetCoachResponse_ShouldMapAllFieldsFromInheritedUserAndCoach() {
        // Given
        Coach coach = Users.coachCarlos();

        List<SessionType> specialization = yogaPilates();

        coach.setSpecialization(yogaPilates());

        Trainee traineeLucius = Users.traineeLucius();
        Trainee traineeSophia = Users.traineeSophia();

        coach.setTrainees(
                Set.of(
                        traineeLucius,
                        traineeSophia
                )
        );

        // When
        GetCoachResponse response = coachMapper.toGetCoachResponse(coach);

        // Then
        assertEquals(coach.getUsername(), response.getUsername());
        assertEquals(coach.isActive(), response.isActive());
        assertEquals(coach.getSpecialization().size(), response.getSpecialization().size());
        assertTrue(response.getSpecialization().containsAll(specialization));
        assertEquals(coach.getTrainees().size(), response.getTrainees().size());
        assertTrue(response.getTrainees().contains(traineeLucius));
//        assertTrue(response.getTrainees().contains(traineeSophia));
    }

    @Test
    void toRegisterCoachResponse_ShouldMapUsernameAndPasswordFromInheritedUser() {
        // Given
        Coach coach = Users.coachCarlos();

        // When
        RegisterUserResponse response = coachMapper.toRegisterCoachResponse(coach);

        // Then
        assertEquals(coach.getUsername(), response.getUsername());
        assertEquals(coach.getPassword(), response.getPassword());
    }

    @Test
    void toEntity_RegisterCoachRequest_ShouldUpdateInheritedUserFieldsAndCoachSpecificFields() {
        // Given
        RegisterCoachRequest request = RegisterCoachRequests.valid(); // Coach Carlos

        request.setSpecialization(yogaPilates());

        Coach entity = Users.coachAlice(); // Coach Alice

        Long oldId = entity.getId();
        String oldPassword = entity.getPassword();
        boolean oldActiveStatus = entity.isActive();
        List<SessionType> oldSpecialization = cardioStrengthTraining();

        entity.setSpecialization(oldSpecialization);

        // When
        coachMapper.toEntity(request, entity);

        // Then
        assertEquals(request.getFirstName(), entity.getFirstName());
        assertEquals(request.getLastName(), entity.getLastName());
        assertEquals(request.getSpecialization().size(), entity.getSpecialization().size());
        assertTrue(entity.getSpecialization().containsAll(request.getSpecialization()));
        assertFalse(entity.getSpecialization().contains(cardio()));
        assertFalse(entity.getSpecialization().contains(strengthTraining()));
        assertEquals(request.getUsername(), entity.getUsername());

        assertEquals(oldId, entity.getId());
        assertEquals(oldPassword, entity.getPassword());
        assertEquals(oldActiveStatus, entity.isActive());
    }

    @Test
    void toEntity_UpdateCoachProfileRequest_ShouldUpdateInheritedUserFieldsAndCoachSpecificFieldsAndIgnoreUsername() {
        // Given

        UpdateCoachRequest request = UpdateCoachRequests.valid(); // Coach Carlos
        request.setSpecialization(yogaPilates());

        Coach entity = Users.coachDavid();


        Long oldId = entity.getId();
        String oldUsername = entity.getUsername();
        String oldPassword = entity.getPassword();
        List<SessionType> oldSpecialization = Specializations.cardio();

        entity.setSpecialization(oldSpecialization);
        entity.setActive(false);

        // When
        coachMapper.toEntity(request, entity);

        // Then
        assertEquals(request.getFirstName(), entity.getFirstName());
        assertEquals(request.getLastName(), entity.getLastName());
        assertTrue(entity.isActive());
        assertEquals(oldSpecialization.size(), entity.getSpecialization().size());
        assertTrue(entity.getSpecialization().containsAll(request.getSpecialization()));

        // Verify username was ignored
        assertEquals(oldUsername, entity.getUsername());

        // Other inherited user fields should remain unchanged
        assertEquals(oldId, entity.getId());
        assertEquals(oldPassword, entity.getPassword());
    }

    @Test
    void toUpdateCoachProfileResponse_ShouldMapAllFieldsFromInheritedUserAndCoach() {
        SessionType sessionType1 = SessionType.builder().id(10L).name("Yoga").build();
        Trainee trainee1 = Trainee.builder().id(20L).build();

        Coach coach = Coach.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("pass123")
                .isActive(true)
                .specialization(Arrays.asList(sessionType1))
                .trainees(new HashSet<>(Arrays.asList(trainee1)))
                .build();

        UpdateCoachResponse response = coachMapper.toUpdateCoachResponse(coach);

        assertEquals(coach.getFirstName(), response.getFirstName());
        assertEquals(coach.getLastName(), response.getLastName());
        assertEquals(coach.getUsername(), response.getUsername());
        assertEquals(coach.isActive(), response.isActive());
        assertEquals(coach.getSpecialization().size(), response.getSpecialization().size());
        assertTrue(response.getSpecialization().contains(sessionType1));
        assertEquals(coach.getTrainees().size(), response.getTrainees().size());
        assertTrue(response.getTrainees().contains(trainee1));
    }
}