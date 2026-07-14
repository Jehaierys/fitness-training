package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachProfileRequest;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CoachMapperTest {

    private CoachMapper coachMapper;

    @BeforeEach
    void setUp() {
        coachMapper = Mappers.getMapper(CoachMapper.class);
    }

    @Test
    void toGetCoachResponse_ShouldMapAllFieldsFromInheritedUserAndCoach() {
        // Given
        SessionType sessionType1 = SessionType.builder().id(10L).name("Yoga").build();
        SessionType sessionType2 = SessionType.builder().id(11L).name("Pilates").build();
        Trainee trainee1 = Trainee.builder().id(20L).build();
        Trainee trainee2 = Trainee.builder().id(21L).build();

        Coach coach = Coach.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("pass123")
                .isActive(true)
                .specialization(Arrays.asList(sessionType1, sessionType2))
                .trainees(new HashSet<>(Arrays.asList(trainee1, trainee2)))
                .build();

        // When
        GetCoachResponse response = coachMapper.toGetCoachResponse(coach);

        // Then
        assertNotNull(response);
        assertEquals(coach.getUsername(), response.getUsername());
        assertEquals(coach.getPassword(), response.getPassword());
        assertEquals(coach.isActive(), response.isActive());
        assertEquals(coach.getSpecialization().size(), response.getSpecialization().size());
        assertTrue(response.getSpecialization().contains(sessionType1));
        assertTrue(response.getSpecialization().contains(sessionType2));
        assertEquals(coach.getTrainees().size(), response.getTrainees().size());
        assertTrue(response.getTrainees().contains(trainee1));
        assertTrue(response.getTrainees().contains(trainee2));
    }

    @Test
    void toUserSignUpResponse_ShouldMapUsernameAndPasswordFromInheritedUser() {
        // Given
        Coach coach = Coach.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("pass123")
                .isActive(true)
                .build();

        // When
        UserSignUpResponse response = coachMapper.toUserSignUpResponse(coach);

        // Then
        assertNotNull(response);
        assertEquals(coach.getUsername(), response.getUsername());
        assertEquals(coach.getPassword(), response.getPassword());
    }

    @Test
    void toEntity_CoachSignUpRequest_ShouldUpdateInheritedUserFieldsAndCoachSpecificFields() {
        // Given
        SessionType sessionType1 = SessionType.builder().id(10L).name("Yoga").build();
        SessionType sessionType2 = SessionType.builder().id(11L).name("Pilates").build();
        List<SessionType> newSpecialization = Arrays.asList(sessionType1, sessionType2);

        CoachSignUpRequest dto = new CoachSignUpRequest();
        dto.setFirstName("NewJohn");
        dto.setLastName("NewDoe");
        dto.setUsername("new.username");
        dto.setPassword("newpass");
        dto.setSpecialization(newSpecialization);

        Coach existingCoach = Coach.builder()
                .id(100L)
                .firstName("OldJohn")
                .lastName("OldDoe")
                .username("old.user")
                .password("oldpass")
                .isActive(false)
                .specialization(new ArrayList<>(List.of(
                        SessionType.builder().id(5L).name("Old").build())))
                .build();

        // When
        coachMapper.toEntity(dto, existingCoach);

        // Then
        assertEquals("NewJohn", existingCoach.getFirstName());
        assertEquals("NewDoe", existingCoach.getLastName());
        assertEquals(newSpecialization.size(), existingCoach.getSpecialization().size());
        assertTrue(existingCoach.getSpecialization().containsAll(newSpecialization));
        assertEquals(100L, existingCoach.getId());
        assertEquals("new.username", existingCoach.getUsername());
        assertEquals("newpass", existingCoach.getPassword());
        // Other inherited user fields should remain unchanged as they are not in CoachSignUpRequest
        assertFalse(existingCoach.isActive());
    }

    @Test
    void toEntity_UpdateCoachProfileRequest_ShouldUpdateInheritedUserFieldsAndCoachSpecificFieldsAndIgnoreUsername() {
        // Given
        SessionType sessionType1 = SessionType.builder().id(10L).name("Yoga").build();
        Set<SessionType> newSpecialization = new HashSet<>(List.of(sessionType1));

        UpdateCoachProfileRequest dto = new UpdateCoachProfileRequest();
        dto.setFirstName("UpdatedJohn");
        dto.setLastName("UpdatedDoe");
        dto.setUsername("new.username"); // This should be ignored
        dto.setIsActive(true);
        dto.setSpecialization(newSpecialization);

        Coach existingCoach = Coach.builder()
                .id(100L)
                .firstName("OldJohn")
                .lastName("OldDoe")
                .username("old.user")
                .password("oldpass")
                .isActive(false)
                .specialization(new ArrayList<>(List.of(
                        SessionType.builder().id(5L).name("Old").build())))
                .build();

        // When
        coachMapper.toEntity(dto, existingCoach);

        // Then
        assertEquals("UpdatedJohn", existingCoach.getFirstName());
        assertEquals("UpdatedDoe", existingCoach.getLastName());
        assertTrue(existingCoach.isActive());
        assertEquals(newSpecialization.size(), existingCoach.getSpecialization().size());
        assertTrue(existingCoach.getSpecialization().containsAll(newSpecialization));
        // Verify username was ignored
        assertEquals("old.user", existingCoach.getUsername());
        // Other inherited user fields should remain unchanged
        assertEquals(100L, existingCoach.getId());
        assertEquals("oldpass", existingCoach.getPassword());
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

        UpdateCoachProfileResponse response = coachMapper.toUpdateCoachProfileResponse(coach);

        assertNotNull(response);
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