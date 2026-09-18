package com.fitnesstraining.intergation.coach;

import com.fitnesstraining.config.test.JwtTestHelper;
import com.fitnesstraining.domain.entity.Coach;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CoachGetSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTestHelper jwtTestHelper;

    private String usernameToFetch;

    private Cookie jwtCookie;

    private Exception exception;

    private MvcResult mvcResult;

    @Before
    @Transactional
    public void setUp() {
        entityManager.createQuery("DELETE FROM Coach").executeUpdate();
        entityManager.clear();
        usernameToFetch = null;
        jwtCookie = null;
        exception = null;
        mvcResult = null;
    }

    @Transactional
    @Given("an existing coach")
    public void anExistingCoach() {
        final String password = "password123";
        final Coach coach = Coach.builder()
                .firstName("Anna")
                .lastName("Kowalski")
                .username("anna.kowalski")
                .password(passwordEncoder.encode(password))
                .isActive(true)
                .build();

        entityManager.persist(coach);
        entityManager.flush();

        usernameToFetch = coach.getUsername();
        jwtCookie = jwtTestHelper.createAuthenticationCookie(coach, password);
    }

    @Given("a username that does not belong to any coach")
    public void aUsernameThatDoesNotBelongToAnyCoach() {
        usernameToFetch = "no.such.coach";
        jwtCookie = jwtTestHelper.createAuthenticationCookie(usernameToFetch);
    }

    @When("I fetch the coach by sending a GET request to {string} with its username")
    public void fetchCoachBySendingGetRequestWithThatUsername(String url) {
        performGet(url);
    }

    private void performGet(String url) {
        try {
            mvcResult = mockMvc.perform(
                            get(url)
                                    .param("username", usernameToFetch)
                                    .cookie(jwtCookie)
                    )
                    .andReturn();
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the get coach response status should be {int}")
    public void getCoachResponseStatusShouldBe(int expectedStatus) {
        if (exception != null) {
            Assertions.fail(exception);
        }
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus);
    }

    @Then("the response should contain the coach's data")
    public void theResponseShouldContainTheCoachsData() throws Exception {
        assertThat(mvcResult.getResponse().getContentAsString())
                .contains(usernameToFetch);
    }

    @Then("the get coach should indicate coach not found")
    public void getCoachShouldIndicateCoachNotFound() {
        // todo: assign another Exception
        if (exception != null) {
            assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
        } else {
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
        }
    }
}
