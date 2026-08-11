package com.fitnesstraining.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.api.CoachController;
import com.fitnesstraining.config.test.JacksonTestConfig;
import com.fitnesstraining.config.test.TestSecurityConfiguration;
import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.request.coach.UpdateCoachRequest;
import com.fitnesstraining.service.CoachService;
import com.fitnesstraining.utils.TestMapper;
import com.fitnesstraining.utils.dto.RegisterCoachRequests;
import com.fitnesstraining.utils.dto.UpdateCoachRequests;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
@Import({TestSecurityConfiguration.class, TestMapper.class, JacksonTestConfig.class})
public class CoachControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CoachService service;

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

            when(service.register(any(RegisterCoachRequest.class)))
                    .thenReturn(null);

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
                    .andExpect(jsonPath("$.details.specializationIds")
                            .value(ValidationErrorMessages.SPECIALIZATION_CANNOT_BE_EMPTY));

            verify(service, never()).register(any(RegisterCoachRequest.class));
        }


        @Test
        void tooShort() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.tooShort();

            when(service.register(any(RegisterCoachRequest.class)))
                    .thenReturn(null);

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

            verify(service, never()).register(any(RegisterCoachRequest.class));
        }

        @Test
        void tooLong() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.tooLong();

            when(service.register(any(RegisterCoachRequest.class)))
                    .thenReturn(null);

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

            verify(service, never()).register(any(RegisterCoachRequest.class));
        }

        @Test
        void forbiddenCharacter() throws Exception {
            registerRequest = RegisterCoachRequests.Invalid.forbiddenCharacters();

            when(service.register(any(RegisterCoachRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));

            verify(service, never()).register(any(RegisterCoachRequest.class));
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

            when(service.update(any(UpdateCoachRequest.class)))
                    .thenReturn(null);

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
                    .andExpect(jsonPath("$.details.specializationIds")
                            .value(ValidationErrorMessages.SPECIALIZATION_CANNOT_BE_EMPTY));

            verify(service, never()).update(any(UpdateCoachRequest.class));
        }

        @Test
        void tooShort() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.tooShort();

            when(service.update(any(UpdateCoachRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE));

            verify(service, never()).update(any(UpdateCoachRequest.class));
        }

        @Test
        void tooLong() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.tooLong();

            when(service.update(any(UpdateCoachRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE));

            verify(service, never()).update(any(UpdateCoachRequest.class));
        }

        @Test
        void forbiddenCharacter() throws Exception {
            updateRequest = UpdateCoachRequests.Invalid.forbiddenCharacters();

            when(service.update(any(UpdateCoachRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));

            verify(service, never()).update(any(UpdateCoachRequest.class));
        }
    }
}
