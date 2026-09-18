package com.fitnesstraining.intergation.session;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.dto.request.session.SessionSearchCriteria;
import com.fitnesstraining.domain.dto.response.SessionDto;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.utils.SessionTypes;
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
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// todo: fix the test яnd enable in features
@Slf4j
public class SessionsSteps {

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

    @Autowired
    private SessionTypes sessionTypes;

    private String coachUsername;
    private String traineeUsername;
    private Coach coach;
    private Trainee trainee;

    private SessionSearchCriteria searchCriteria;

    private Cookie jwtCookie;

    private int responseStatus;
    private List<SessionDto> responseBody;

    private Exception exception;


    @Before
    @Transactional
    public void clearDatabase() {
        entityManager.createQuery("DELETE FROM Session").executeUpdate();
        entityManager.createQuery("DELETE FROM Trainee").executeUpdate();
        entityManager.createQuery("DELETE FROM Coach").executeUpdate();
        entityManager.clear();
    }


    @Transactional
    @Given("an authenticated coach with sessions in the database")
    public void authenticatedCoachWithSessionsInDatabase() {
        try {
            final Trainee trainee = Users.traineeEthan();
            trainee.setPassword(passwordEncoder.encode(trainee.getPassword()));
            entityManager.persist(trainee);

            coach = Users.coachJohn();
            final String coachPassword = coach.getPassword();
            coach.setPassword(passwordEncoder.encode(coachPassword));
            entityManager.persist(coach);

            entityManager.flush();

            coachUsername = coach.getUsername();
            traineeUsername = trainee.getUsername();

            final Session session = Session.builder()
                    .coach(coach)
                    .trainee(trainee)
                    .date(LocalDateTime.now().plusDays(1))
                    .duration(Duration.of(60, MINUTES))
                    .sessionType(sessionTypes.cardio())
                    .name("Morning Cardio Session")
                    .build();
            entityManager.persist(session);

            jwtCookie = jwtTestHelper.createAuthenticationCookie(
                    coach,
                    coachPassword
            );
        } catch (Exception e) {
            exception = e;
            throw e;
        }
    }

    @Given("an empty session search criteria")
    public void emptySessionSearchCriteria() {
        searchCriteria = SessionSearchCriteria.builder().build();
        searchCriteria.setRequestSenderId(coach.getId());
    }

    @When("I send a GET request to {string} with the session search criteria")
    public void sendGetRequestWithSessionSearchCriteria(String url) throws Exception {
        try {
            responseStatus = mockMvc
                    .perform(
                            get(url)
                                    .cookie(jwtCookie)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(searchCriteria))
                    )
                    .andReturn()
                    .getResponse()
                    .getStatus();
        } catch (Exception e) {
            exception = e;
            throw e;
        }
    }

    @Then("the searching response status should be {int}")
    public void responseStatusShouldBe(int expectedStatus) {

        if (exception != null) {
            log.error("Exception occurred during session search: {}", exception.getMessage());
            Assertions.fail(exception);
        }

        assertThat(responseStatus)
                .isEqualTo(expectedStatus);
    }
}