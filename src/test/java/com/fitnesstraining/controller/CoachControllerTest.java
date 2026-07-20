package com.fitnesstraining.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.api.CoachController;
import com.fitnesstraining.config.JacksonTestConfig;
import com.fitnesstraining.domain.dto.coach.request.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachRequest;
import com.fitnesstraining.logic.facade.CoachFacade;
import com.fitnesstraining.testUtils.TestMapper;
import com.fitnesstraining.testUtils.dto.RegisterCoachRequests;
import com.fitnesstraining.testUtils.dto.UpdateCoachRequests;
import com.fitnesstraining.utils.ValidationErrorMessages;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Checks proper validation
// And correct response status

// register, update operations are covered
// todo: findAvailableCoaches and findByUsername are up to be covered

// CHECKSTYLE.OFF
@WebMvcTest(CoachController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestMapper.class, JacksonTestConfig.class})
public class CoachControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CoachFacade facade;

    private RegisterCoachRequest registerRequest;
    private UpdateCoachRequest updateRequest;


    @Nested
    class Valid {

        @Test
        void registrationRequest_shouldNotThrowExceptions() throws Exception {
            registerRequest = RegisterCoachRequests.valid();

            mockMvc.perform(post("/v1-0-0/coaches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        void updateRequest_shouldNotThrowExceptions() throws Exception {
            updateRequest = UpdateCoachRequests.valid();

            mockMvc.perform(put("/v1-0-0/coaches")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());
        }


    }


    @Nested
    class InvalidRegistrationRequest_ShouldThrowExceptions {

        private RequestBuilder template(RegisterCoachRequest request) throws JsonProcessingException {
            return post("/v1-0-0/coaches")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        void missingCrucialData() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.missingCrucialData();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.password")
                            .value(ValidationErrorMessages.Password.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.specialization")
                            .value(ValidationErrorMessages.SPECIALIZATION_CANNOT_BE_EMPTY));
        }


        @Test
        void tooShort() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.tooShort();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE))
                    .andExpect(jsonPath("$.details.password")
                            .value(ValidationErrorMessages.Password.SIZE));
        }

        @Test
        void tooLong() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.tooLong();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE))
                    .andExpect(jsonPath("$.details.password")
                            .value(ValidationErrorMessages.Password.SIZE));
        }

        @Test
        void forbiddenCharacter() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.forbiddenCharacters();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));
        }
    }

    @Nested
    class InvalidUpdateRequest_ShouldThrowExceptions {

        private RequestBuilder template(UpdateCoachRequest request) throws JsonProcessingException {
            return put("/v1-0-0/coaches")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        void missingCrucialData() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.missingCrucialData();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.isActive")
                            .value(ValidationErrorMessages.IS_ACTIVE_CANNOT_BE_NULL))
                    .andExpect(jsonPath("$.details.specialization")
                            .value(ValidationErrorMessages.SPECIALIZATION_CANNOT_BE_EMPTY));
        }

        @Test
        void tooShort() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.tooShort();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE));
        }

        @Test
        void tooLong() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.tooLong();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE));
        }

        @Test
        void forbiddenCharacter() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.forbiddenCharacters();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));
        }
    }
}
