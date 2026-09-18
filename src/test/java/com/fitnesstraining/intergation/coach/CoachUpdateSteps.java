package com.fitnesstraining.intergation.coach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.dto.request.coach.UpdateCoachRequest;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.utils.dto.UpdateCoachRequests;
import com.fitnesstraining.utils.entity.Users;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


public class CoachUpdateSteps {

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

    private UpdateCoachRequest updateRequest;

    private Exception exception;

    private int responseStatus;

    private Cookie jwtCookie;


    @Before
    @Transactional
    public void setUp() {
        entityManager.createQuery("DELETE FROM Coach").executeUpdate();
        entityManager.clear();
        updateRequest = null;
        exception = null;
        responseStatus = 0;
        jwtCookie = null;
    }


    @Transactional
    @Given("an authenticated coach and a coach update request with full data")
    public void authenticatedCoachAndUpdateRequestWithFullData() {
        updateRequest = UpdateCoachRequests.valid();

        final Coach coach = Users.coachJohn();

        updateRequest.setUsername(coach.getUsername());

        final String password = coach.getPassword();

        coach.setPassword(passwordEncoder.encode(coach.getPassword()));

        entityManager.persist(coach);
        entityManager.flush();
        entityManager.clear();

        jwtCookie = jwtTestHelper.createAuthenticationCookie(coach, password);
    }

    @Given("an authenticated non-existing coach and a coach update request with full data")
    public void authenticatedNonExistingCoachAndUpdateRequestWithFullData() {
        updateRequest = UpdateCoachRequests.valid();

        final String username = "non.existing.coach";
        updateRequest.setUsername(username);

        jwtCookie = jwtTestHelper.createAuthenticationCookie(username);
    }

    @When("I update a coach by sending a PUT request to {string}")
    public void updateCoachBySendingPutRequestTo(String url) {
        try {
            responseStatus = mockMvc.perform(
                            put(url)
                                    .cookie(jwtCookie)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updateRequest))
                    )
                    .andReturn()
                    .getResponse()
                    .getStatus();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the coach update response status should be {int}")
    public void coachUpdateResponseStatusShouldBe(int expectedStatus) {

        if (exception != null) {
            Assertions.fail(exception);
        }

        assertThat(responseStatus).isEqualTo(expectedStatus);
    }

    @Then("the coach update should indicate coach not found")
    public void coachUpdateShouldIndicateCoachNotFound() {
        // todo: we need another Exception here
        if (exception != null) {
            assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
        } else {
            assertThat(responseStatus).isEqualTo(404);
        }
    }

    @Transactional
    @Then("the coach should be updated in the database")
    public void coachShouldBeUpdatedInDatabase() {
        entityManager.clear();

        final Coach coach = entityManager
                .createQuery(
                        "SELECT c FROM Coach c WHERE c.username = :username",
                        Coach.class
                )
                .setParameter("username", updateRequest.getUsername())
                .getSingleResult();

        assertThat(coach.getFirstName())
                .isEqualTo(updateRequest.getFirstName());

        assertThat(coach.getLastName())
                .isEqualTo(updateRequest.getLastName());

        assertThat(coach.getUsername())
                .isEqualTo(updateRequest.getUsername());

        assertThat(coach.isActive())
                .isEqualTo(updateRequest.getIsActive());
    }
}
