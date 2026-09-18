package com.fitnesstraining.intergation.trainee;

import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.utils.entity.Users;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class TraineeDeleteSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTestHelper jwtTestHelper;

    private long traineeId;
    private String username;
    private Cookie jwtCookie;

    private int responseStatus;
    private Exception exception;

    @Before
    public void setUp() {
        responseStatus = 0;
        exception = null;
        jwtCookie = null;
    }

    @Transactional
    @Given("an authenticated trainee with full data")
    public void authenticatedTraineeWithFullData() {

        final Trainee trainee = Users.traineeEthan();

        final String password = trainee.getPassword();

        trainee.setPassword(passwordEncoder.encode(password));

        entityManager.persist(trainee);
        entityManager.flush();

        traineeId = trainee.getId();
        username = trainee.getUsername();

        entityManager.clear();

        jwtCookie = jwtTestHelper.createAuthenticationCookie(
                trainee,
                password
        );
    }

    @When("I send a DELETE request to {string}")
    public void sendDeleteRequestTo(String url) {

        try {
            responseStatus = mockMvc
                    .perform(delete(url)
                            .cookie(jwtCookie))
                    .andReturn()
                    .getResponse()
                    .getStatus();

        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the delete response status should be {int}")
    public void deleteResponseStatusShouldBe(int expectedStatus) {

        if (exception != null) {
            Assertions.fail(exception);
        }

        assertThat(responseStatus)
                .isEqualTo(expectedStatus);
    }

    @Transactional
    @Then("the trainee should be deleted from the database")
    public void traineeShouldBeDeletedFromDatabase() {

        entityManager.clear();

        final Trainee trainee = entityManager.find(
                Trainee.class,
                traineeId
        );

        assertThat(trainee).isNull();
    }
}