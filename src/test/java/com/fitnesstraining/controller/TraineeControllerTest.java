package com.fitnesstraining.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fitnesstraining.api.TraineeController;
import com.fitnesstraining.config.JacksonTestConfig;
import com.fitnesstraining.domain.dto.trainee.request.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeRequest;
import com.fitnesstraining.logic.facade.TraineeFacade;
import com.fitnesstraining.testUtils.TestMapper;
import com.fitnesstraining.testUtils.dto.RegisterTraineeRequests;
import com.fitnesstraining.testUtils.dto.UpdateTraineeRequests;
import com.fitnesstraining.utils.ValidationErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


// Checks proper validation
// And correct response status

// register, update operations are covered
// todo: delete and findByUsername are up to be covered

// CHECKSTYLE.OFF
@WebMvcTest(TraineeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestMapper.class, JacksonTestConfig.class})
public class TraineeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TraineeFacade facade;

    private RegisterTraineeRequest registerRequest;
    private UpdateTraineeRequest updateRequest;


    @BeforeEach
    void clear() {
        registerRequest = null;
        updateRequest = null;
    }


    //todo: maybe add response check?
    @Nested
    class ValidInput_ShouldNotThrowException {

        @Test
        void fullData_registrationRequest() throws Exception {
            registerRequest = RegisterTraineeRequests.Valid.fullData();

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        void missingOptional_registrationRequest() throws Exception {
            registerRequest = RegisterTraineeRequests.Valid.withoutAddressAndBirthdate();

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        void fullData_updateRequest() throws Exception {
            updateRequest = UpdateTraineeRequests.Valid.fullData();

            mockMvc.perform(put("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());
        }

        @Test
        void missingOptional_updateRequest() throws Exception {
            updateRequest = UpdateTraineeRequests.Valid.withoutAddressAndBirthdate();

            mockMvc.perform(put("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());
        }
    }


    @Nested
    class InvalidRegistrationRequest_ExceptionsExpected {

        private RequestBuilder template(RegisterTraineeRequest request) throws JsonProcessingException {
            return post("/v1-0-0/trainees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        void cannotBeBlank() throws Exception {
            registerRequest = RegisterTraineeRequests.Invalid.missingCrucialData();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.password")
                            .value(ValidationErrorMessages.Password.CANNOT_BE_BLANK));
        }

        // Idea generated this entire test on its own with 1 attempt
        @Test
        void tooShort() throws Exception {
            registerRequest = RegisterTraineeRequests.Invalid.tooShort();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE))
                    .andExpect(jsonPath("$.details.password")
                            .value(ValidationErrorMessages.Password.SIZE))
                    .andExpect(jsonPath("$.details.address")
                            .value(ValidationErrorMessages.Address.SIZE));
        }

        @Test
        void tooLong() throws Exception {
            registerRequest = RegisterTraineeRequests.Invalid.tooLong();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE))
                    .andExpect(jsonPath("$.details.password")
                            .value(ValidationErrorMessages.Password.SIZE))
                    .andExpect(jsonPath("$.details.address")
                            .value(ValidationErrorMessages.Address.SIZE));
        }

        @Test
        void forbiddenCharacter() throws Exception {
            registerRequest = RegisterTraineeRequests.Invalid.forbiddenCharacters();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));
        }

        @Test
        void invalidDate() throws Exception {
            registerRequest = RegisterTraineeRequests.Invalid.invalidDate();

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.birthDate")
                            .value(ValidationErrorMessages.BirthDate.PAST));
        }
    }

    @Nested
    class InvalidUpdateRequest_ExceptionsExpected {

        private RequestBuilder template(UpdateTraineeRequest request) throws JsonProcessingException {
            return put("/v1-0-0/trainees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        void cannotBeBlank() throws Exception {
            updateRequest = UpdateTraineeRequests.Invalid.missingCrucialData();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.CANNOT_BE_BLANK));
        }

        @Test
        void tooShort() throws Exception {
            updateRequest = UpdateTraineeRequests.Invalid.tooShort();

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
            updateRequest = UpdateTraineeRequests.Invalid.tooLong();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE))
                    .andExpect(jsonPath("$.details.address")
                            .value(ValidationErrorMessages.Address.SIZE));
        }

        @Test
        void forbiddenCharacter() throws Exception {
            updateRequest = UpdateTraineeRequests.Invalid.forbiddenCharacters();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));
        }


        @Test
        void futureBirthdate() throws Exception {
            updateRequest = UpdateTraineeRequests.Invalid.futureBirthdate();

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.birthDate")
                            .value(ValidationErrorMessages.BirthDate.PAST));
        }
    }
}
