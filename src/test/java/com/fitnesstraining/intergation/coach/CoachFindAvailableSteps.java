package com.fitnesstraining.intergation.coach;

import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.utils.entity.Users;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class CoachFindAvailableSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTestHelper jwtTestHelper;

    private Cookie jwtCookie;

    private String availableCoachUsername;

    private MvcResult mvcResult;


    @Before
    @Transactional
    public void setUp() {
        entityManager.createQuery("DELETE FROM Session").executeUpdate();
        entityManager.createQuery("DELETE FROM Coach").executeUpdate();
        entityManager.clear();
        jwtCookie = null;
        availableCoachUsername = null;
        mvcResult = null;
    }


    @Transactional
    @Given("an authenticated trainee and an available coach")
    public void authenticatedTraineeAndAvailableCoach() {

        final Trainee trainee = Users.traineeEthan();

        final String password = trainee.getPassword();

        trainee.setPassword(passwordEncoder.encode(password));

        entityManager.persist(trainee);

        final Coach coach = Users.coachJohn();

        coach.setPassword(passwordEncoder.encode(coach.getPassword()));

        entityManager.persist(coach);

        entityManager.flush();

        availableCoachUsername = coach.getUsername();
        jwtCookie = jwtTestHelper.createAuthenticationCookie(trainee, password);
    }

    // todo: unused
    @Transactional
    @Given("an authenticated trainee")
    public void authenticatedTrainee() {

        final Trainee trainee = Users.traineeEthan();

        final String password = trainee.getPassword();
        trainee.setPassword(passwordEncoder.encode(password));

        entityManager.persist(trainee);
        entityManager.flush();

        jwtCookie = jwtTestHelper.createAuthenticationCookie(trainee, password);
    }

    @When("I search for available coaches by sending a GET request to {string} with available set to {string}")
    public void searchForAvailableCoachesBySendingGetRequestWithAvailableSetTo(
            String url,
            String available
    ) throws Exception {
        mvcResult = mockMvc.perform(
                        get(url)
                                .param("available", available)
                                .cookie(jwtCookie)
                )
                .andReturn();
    }

    @Then("the find available coaches response status should be {int}")
    public void findAvailableCoachesResponseStatusShouldBe(int expectedStatus) {
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus);
    }

    @Then("the response should contain the available coach")
    public void theResponseShouldContainTheAvailableCoach() throws Exception {
        assertThat(mvcResult.getResponse().getContentAsString())
                .contains(availableCoachUsername);
    }
}