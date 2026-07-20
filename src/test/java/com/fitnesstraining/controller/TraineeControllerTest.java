package com.fitnesstraining.controller;

import com.fitnesstraining.api.TraineeController;
import com.fitnesstraining.config.JacksonTestConfig;
import com.fitnesstraining.domain.dto.abstraction.Activated;
import com.fitnesstraining.domain.dto.trainee.request.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.response.RegisterTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


// Checks proper validation
// And correct response status

// CHECKSTYLE.OFF
@WebMvcTest(TraineeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestMapper.class, JacksonTestConfig.class})
public class TraineeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestMapper testMapper;

    @MockitoBean
    private TraineeFacade facade;

    private RegisterTraineeRequest registerRequest;
    private RegisterTraineeResponse registerResponse;

    private UpdateTraineeRequest updateRequest;
    private UpdateTraineeResponse updateResponse;

    private Activated activated;


    @BeforeEach
    void setUp() {
        registerResponse = null;
        registerRequest = null;

        updateRequest = null;
        updateResponse = null;

        activated = null;
    }


    @Nested
    class ValidInput_ShouldNotThrowException {

        @Test
        void fullData_registrationRequest() throws Exception {
            registerRequest = RegisterTraineeRequests.Valid.fullData();
            registerResponse = testMapper.toResponse(registerRequest);

            when(facade.register(any(RegisterTraineeRequest.class))).thenReturn(registerResponse);

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        void missingOptional_registrationRequest() throws Exception {
            registerRequest = RegisterTraineeRequests.Valid.withoutAddressAndBirthdate();
            registerResponse = testMapper.toResponse(registerRequest);

            when(facade.register(any(RegisterTraineeRequest.class))).thenReturn(registerResponse);

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        void fullData_updateRequest() throws Exception {
            updateRequest = UpdateTraineeRequests.Valid.fullData();
            updateResponse = testMapper.toResponse(updateRequest);

            when(facade.update(any(UpdateTraineeRequest.class))).thenReturn(updateResponse);

            mockMvc.perform(put("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());
        }

        @Test
        void missingOptional_updateRequest() throws Exception {
            updateRequest = UpdateTraineeRequests.Valid.withoutAddressAndBirthdate();
            updateResponse = testMapper.toResponse(updateRequest);

            when(facade.update(any(UpdateTraineeRequest.class))).thenReturn(updateResponse);

            mockMvc.perform(put("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());
        }

        // should be moved to the upcoming UserController
        @Test
        void setAvailable() throws Exception {
            activated = Activated.builder().isActive(true).username("sophia.miller").build();

            mockMvc.perform(patch("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(activated)))
                    .andExpect(status().isOk());
        }
    }


    @Nested
    class InvalidInput_ShouldNotThrowException {

        @Test
        void testing() throws Exception {
            registerRequest = RegisterTraineeRequests.Invalid.missingCrucialData();

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
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
    }
}
