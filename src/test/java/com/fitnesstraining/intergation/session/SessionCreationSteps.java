package com.fitnesstraining.intergation.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.message.CoachWorkload;
import com.fitnesstraining.utils.entity.Users;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
public class SessionCreationSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTestHelper jwtTestHelper;

    private String coachUsername;
    private String traineeUsername;
    private SessionRegistrationRequest sessionCreationRequest;

    private Cookie jwtCookie;

    private int responseStatus;
    private Exception exception;

    private CoachWorkload receivedCoachWorkload;
    private Session persistedSession;


    @Before
    @Transactional
    public void clearDatabase() {
        entityManager.createQuery("DELETE FROM Session").executeUpdate();
        entityManager.createQuery("DELETE FROM Trainee").executeUpdate();
        entityManager.createQuery("DELETE FROM Coach").executeUpdate();
        entityManager.clear();
    }


    @Transactional
    @Given("a trainee and a coach prepared in the database")
    public void traineeAndACoachPreparedInTheDatabase() {
        final Trainee trainee = Users.traineeEthan();
        trainee.setPassword(passwordEncoder.encode(trainee.getPassword()));
        entityManager.persist(trainee);

        final Coach coach = Users.coachJohn();
        final String coachPassword = coach.getPassword();
        coach.setPassword(passwordEncoder.encode(coach.getPassword()));
        entityManager.persist(coach);

        entityManager.flush();

        jwtCookie = jwtTestHelper.createAuthenticationCookie(
                coach,
                coachPassword
        );

        traineeUsername = trainee.getUsername();
        coachUsername = coach.getUsername();

    }

    @Given("a valid session creation request")
    public void validSessionCreationRequest() {
        sessionCreationRequest = SessionRegistrationRequest.builder()
                .coachUsername(coachUsername)
                .traineeUsername(traineeUsername)
                .date(LocalDateTime.now().plusDays(1))
                .duration(Duration.of(60, MINUTES))
                .sessionTypeName("Cardio")
                .name("Morning Cardio Session")
                .build();
    }

    @When("I send a POST request to {string} with the session creation request")
    public void sendPostRequestToWithSessionCreationRequest(String url) throws Exception {
        try {
            responseStatus = mockMvc.perform(post(url)
                            .cookie(jwtCookie)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sessionCreationRequest)))
                    .andReturn()
                    .getResponse()
                    .getStatus();
        } catch (Exception e) {
            exception = e;
        }
    }


    @Then("the response status should be {int}")
    public void responseStatusShouldBe(int expectedStatus) {

        if (exception != null) {
            Assertions.fail(exception);
        }

        assertEquals(expectedStatus, responseStatus);
    }

}
