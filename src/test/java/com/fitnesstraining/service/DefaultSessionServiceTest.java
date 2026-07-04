package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.Session;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.SessionSearchCriteria;
import com.fitnesstraining.repository.SessionRepository;
import com.fitnesstraining.service.exception.SessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultSessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private DefaultSessionService sessionService;

    private Session session;
    private User testUser;
    private Coach testCoach;
    private Trainee testTrainee;
    private SessionType testSessionType;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(100L)
                .firstName("Test")
                .lastName("User")
                .username("test.user")
                .password("pass")
                .isActive(true)
                .build();

        testCoach = Coach.builder()
                .id(200L)
                .user(testUser)
                .specialization(Set.of(SessionType.builder().id(1L).name("Fitness").build()))
                .build();

        testTrainee = Trainee.builder()
                .id(300L)
                .user(testUser)
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Trainee St")
                .build();

        testSessionType = SessionType.builder()
                .id(400L)
                .name("Intro Session Type")
                .build();

        session = Session.builder()
                .id(1L)
                .name("Intro Session")
                .coach(testCoach)
                .trainee(testTrainee)
                .sessionType(testSessionType)
                .date(LocalDateTime.now())
                .duration(Duration.ofHours(1))
                .build();
    }

    @Test
    void create_ShouldReturnSavedSession() {
        when(sessionRepository.create(session)).thenReturn(session);
        Session result = sessionService.create(session);
        assertNotNull(result);
        assertEquals("Intro Session", result.getName());
        assertEquals(testCoach, result.getCoach());
        assertEquals(testTrainee, result.getTrainee());
        assertEquals(testSessionType, result.getSessionType());
        verify(sessionRepository, times(1)).create(session);
    }

    @Test
    void getById_WhenExists_ShouldReturnSession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        Session result = sessionService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(testCoach, result.getCoach());
        assertEquals(testTrainee, result.getTrainee());
        assertEquals(testSessionType, result.getSessionType());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowSessionNotFoundException() {
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(SessionNotFoundException.class, () -> sessionService.getById(99L));
        verify(sessionRepository, times(1)).findById(99L);
    }

    @Test
    void searchSessions_WithTraineeUsername_ShouldReturnFilteredSessions() {
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .traineeUsername("test.user")
                .build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithCoachUsername_ShouldReturnFilteredSessions() {
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .coachUsername("test.user")
                .build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithFromAndToDate_ShouldReturnFilteredSessions() {
        LocalDate fromDate = LocalDate.now().minusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(1);
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithCoachFirstNameAndLastName_ShouldReturnFilteredSessions() {
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .coachFirstName("Test")
                .coachLastName("User")
                .build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithSessionType_ShouldReturnFilteredSessions() {
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .sessionType("Intro Session Type")
                .build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithCombinedCriteria_ShouldReturnFilteredSessions() {
        LocalDate fromDate = LocalDate.now().minusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(1);
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .traineeUsername("test.user")
                .coachUsername("test.user")
                .fromDate(fromDate)
                .toDate(toDate)
                .coachFirstName("Test")
                .coachLastName("User")
                .sessionType("Intro Session Type")
                .build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithNoCriteria_ShouldReturnAllSessions() {
        SessionSearchCriteria criteria = SessionSearchCriteria.builder().build();
        List<Session> expectedSessions = Collections.singletonList(session);
        when(sessionRepository.searchSessions(criteria)).thenReturn(expectedSessions);

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }

    @Test
    void searchSessions_WithNoMatchingCriteria_ShouldReturnEmptyList() {
        SessionSearchCriteria criteria = SessionSearchCriteria.builder()
                .traineeUsername("nonexistent.user")
                .build();
        when(sessionRepository.searchSessions(criteria)).thenReturn(Collections.emptyList());

        List<Session> result = sessionService.searchSessions(criteria);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(sessionRepository, times(1)).searchSessions(criteria);
    }
}