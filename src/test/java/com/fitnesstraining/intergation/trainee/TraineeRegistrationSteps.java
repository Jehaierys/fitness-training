package com.fitnesstraining.intergation.trainee;


import com.fitnesstraining.domain.dto.request.trainee.RegisterTraineeRequest;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.repository.TraineeRepository;
import com.fitnesstraining.utils.dto.RegisterTraineeRequests;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


// todo: cut @Transactional areas
public class TraineeRegistrationSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private RegisterTraineeRequest registerRequest;

    private MvcResult mvcResult;

    @Before
    @Transactional
    public void clearDatabase() {
        entityManager.createQuery("DELETE FROM Session").executeUpdate();
        entityManager.createQuery("DELETE FROM Trainee").executeUpdate();

        entityManager.clear();

        registerRequest = null;
        mvcResult = null;
    }

    @Given("a trainee registration request with full data")
    public void traineeRegistrationRequestWithFullData() {
        registerRequest = RegisterTraineeRequests.Valid.fullData();
    }

    @When("I send a POST request to {string}")
    public void sendPostRequestTo(String url) throws Exception {

        mvcResult = mockMvc.perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest))
                )
                .andReturn();
    }

    @Then("the registration response status should be {int}")
    public void responseStatusShouldBe(int expectedStatus) {

        assertThat(mvcResult.getResponse().getStatus())
                .isEqualTo(expectedStatus);
    }

    @Transactional
    @Then("the trainee should be saved in the database")
    public void traineeShouldBeSavedInDatabase() {

        entityManager.flush();
        entityManager.clear();

        Trainee trainee = entityManager
                .createQuery(
                        "SELECT t FROM Trainee t WHERE t.username = :username",
                        Trainee.class
                )
                .setParameter("username", registerRequest.getUsername())
                .getSingleResult();

        assertThat(trainee.getUsername())
                .isEqualTo(registerRequest.getUsername());

        assertThat(trainee.getFirstName())
                .isEqualTo(registerRequest.getFirstName());

        assertThat(trainee.getLastName())
                .isEqualTo(registerRequest.getLastName());
    }
}
