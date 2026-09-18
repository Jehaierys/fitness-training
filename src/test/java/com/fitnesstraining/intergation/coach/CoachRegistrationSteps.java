package com.fitnesstraining.intergation.coach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.utils.dto.RegisterCoachRequests;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


// todo: cut @Transactional areas
public class CoachRegistrationSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private RegisterCoachRequest registerRequest;

    private MvcResult mvcResult;


    @Before
    @Transactional
    public void clearDatabase() {
        entityManager.createQuery("DELETE FROM Coach").executeUpdate();
        entityManager.clear();
        registerRequest = null;
        mvcResult = null;
    }


    @Given("a coach registration request with full data")
    public void coachRegistrationRequestWithFullData() {
        registerRequest = RegisterCoachRequests.valid();
    }

    @Given("a coach registration request with a blank username")
    public void coachRegistrationRequestWithBlankUsername() {
        registerRequest = RegisterCoachRequests.Invalid.missingCrucialData();
    }

    @When("I register a coach by sending a POST request to {string}")
    public void registerCoachBySendingPostRequestTo(String url) throws Exception {
        mvcResult = mockMvc.perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest))
                )
                .andReturn();
    }

    @Then("the coach registration response status should be {int}")
    public void coachRegistrationResponseStatusShouldBe(int expectedStatus) {
        assertThat(mvcResult.getResponse().getStatus())
                .isEqualTo(expectedStatus);
    }

    @Transactional
    @Then("the coach should be saved in the database")
    public void coachShouldBeSavedInDatabase() {
        entityManager.flush();
        entityManager.clear();

        final Coach coach = entityManager
                .createQuery(
                        "SELECT c FROM Coach c WHERE c.username = :username",
                        Coach.class
                )
                .setParameter("username", registerRequest.getUsername())
                .getSingleResult();

        assertThat(coach.getUsername())
                .isEqualTo(registerRequest.getUsername());

        assertThat(coach.getFirstName())
                .isEqualTo(registerRequest.getFirstName());

        assertThat(coach.getLastName())
                .isEqualTo(registerRequest.getLastName());

        assertThat(coach.isActive())
                .isTrue();
    }
}
