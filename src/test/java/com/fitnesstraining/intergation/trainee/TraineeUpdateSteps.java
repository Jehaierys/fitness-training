package com.fitnesstraining.intergation.trainee;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.dto.request.trainee.UpdateTraineeRequest;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.utils.dto.UpdateTraineeRequests;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

// todo: cut @Transactional areas
public class TraineeUpdateSteps {

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

    private UpdateTraineeRequest updateRequest;

    private Exception exception;

    private int responseStatus;

    private long traineeId;

    private Cookie jwtCookie;

    @Before
    public void setUp() {
        updateRequest = null;
        exception = null;
        responseStatus = 0;
        jwtCookie = null;
    }

    @Transactional
    @Given("an authenticated trainee and a trainee update request with full data")
    public void traineeUpdateRequestWithFullData() {

        updateRequest = UpdateTraineeRequests.Valid.fullData();

        final String password = "password123";

        final Trainee trainee = Trainee.builder()
                .firstName("John")
                .lastName("Doe")
                .username("sophia.miller")
                .password(passwordEncoder.encode(password))
                .isActive(false)
                .birthDate(LocalDate.of(1998, 5, 15))
                .address("12 Old Street")
                .build();


        entityManager.persist(trainee);
        entityManager.flush();

        traineeId = trainee.getId();

        jwtCookie = jwtTestHelper.createAuthenticationCookie(
                trainee,
                password
        );
    }

    @Given("an authenticated non-existing trainee and a trainee update request with full data")
    public void nonExistingTraineeUpdateRequestWithFullData() {

        updateRequest = UpdateTraineeRequests.Valid.fullData();

        final String username = "non.existing.trainee";

        updateRequest.setUsername(username);

        jwtCookie = jwtTestHelper.createAuthenticationCookie(username);
    }


    @When("I send a PUT request to {string}")
    public void sendPutRequestTo(String url) {

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

    @Then("the update should throw user not found exception")
    public void updateShouldThrowUserNotFoundException() {

        assertThat(exception)
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Then("the update response status should be {int}")
    public void responseStatusShouldBe(int expectedStatus) {

        if (exception != null) {
            Assertions.fail(exception);
        }

        assertThat(responseStatus)
                .isEqualTo(expectedStatus);
    }

    @Transactional
    @Then("the trainee should be updated in the database")
    public void traineeShouldBeUpdatedInDatabase() {

        entityManager.clear();

        final Trainee trainee = entityManager
                .createQuery(
                        "SELECT t FROM Trainee t WHERE t.id = :id",
                        Trainee.class
                )
                .setParameter("id", traineeId)
                .getSingleResult();

        assertThat(trainee.getFirstName())
                .isEqualTo(updateRequest.getFirstName());

        assertThat(trainee.getLastName())
                .isEqualTo(updateRequest.getLastName());

        assertThat(trainee.getUsername())
                .isEqualTo(updateRequest.getUsername());

        assertThat(trainee.getBirthDate())
                .isEqualTo(updateRequest.getBirthDate());

        assertThat(trainee.getAddress())
                .isEqualTo(updateRequest.getAddress());
    }
}

