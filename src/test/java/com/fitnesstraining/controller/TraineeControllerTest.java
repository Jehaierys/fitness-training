package com.fitnesstraining.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fitnesstraining.api.TraineeController;
import com.fitnesstraining.config.test.JacksonTestConfig;
import com.fitnesstraining.config.test.TestSecurityConfiguration;
import com.fitnesstraining.domain.dto.request.trainee.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.request.trainee.UpdateTraineeRequest;
import com.fitnesstraining.logic.facade.TraineeFacade;
import com.fitnesstraining.utils.TestMapper;
import com.fitnesstraining.utils.dto.RegisterTraineeRequests;
import com.fitnesstraining.utils.dto.UpdateTraineeRequests;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
@Import({TestSecurityConfiguration.class, TestMapper.class, JacksonTestConfig.class})
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

            when(facade.register(any(RegisterTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());

            verify(facade, only()).register(any(RegisterTraineeRequest.class));

        }

        @Test
        void missingOptional_registrationRequest() throws Exception {

            registerRequest = RegisterTraineeRequests.Valid.withoutAddressAndBirthdate();

            when(facade.register(any(RegisterTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(post("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated());

            verify(facade, only()).register(any(RegisterTraineeRequest.class));
        }

        @Test
        void fullData_updateRequest() throws Exception {

            updateRequest = UpdateTraineeRequests.Valid.fullData();

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(put("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());

            verify(facade, only()).update(any(UpdateTraineeRequest.class));
        }

        @Test
        void missingOptional_updateRequest() throws Exception {

            updateRequest = UpdateTraineeRequests.Valid.withoutAddressAndBirthdate();

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);
            mockMvc.perform(put("/v1-0-0/trainees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());

            verify(facade, only()).update(any(UpdateTraineeRequest.class));
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

            when(facade.register(any(RegisterTraineeRequest.class)))
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
                            .value(ValidationErrorMessages.Password.CANNOT_BE_BLANK));

            verify(facade, never()).register(any(RegisterTraineeRequest.class));
        }

        // Idea generated this entire test on its own with 1 attempt
        @Test
        void tooShort() throws Exception {

            registerRequest = RegisterTraineeRequests.Invalid.tooShort();

            when(facade.register(any(RegisterTraineeRequest.class)))
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
                            .value(ValidationErrorMessages.Password.SIZE))
                    .andExpect(jsonPath("$.details.address")
                            .value(ValidationErrorMessages.Address.SIZE));

            verify(facade, never()).register(any(RegisterTraineeRequest.class));
        }

        @Test
        void tooLong() throws Exception {

            registerRequest = RegisterTraineeRequests.Invalid.tooLong();

            when(facade.register(any(RegisterTraineeRequest.class)))
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
                            .value(ValidationErrorMessages.Password.SIZE))
                    .andExpect(jsonPath("$.details.address")
                            .value(ValidationErrorMessages.Address.SIZE));

            verify(facade, never()).register(any(RegisterTraineeRequest.class));
        }

        @Test
        void forbiddenCharacter() throws Exception {

            registerRequest = RegisterTraineeRequests.Invalid.forbiddenCharacters();

            when(facade.register(any(RegisterTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));

            verify(facade, never()).register(any(RegisterTraineeRequest.class));
        }

        @Test
        void invalidDate() throws Exception {

            registerRequest = RegisterTraineeRequests.Invalid.invalidDate();

            when(facade.register(any(RegisterTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(registerRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.birthDate")
                            .value(ValidationErrorMessages.BirthDate.PAST));

            verify(facade, never()).register(any(RegisterTraineeRequest.class));
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

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.CANNOT_BE_BLANK))
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.CANNOT_BE_BLANK));

            verify(facade, never()).update(any(UpdateTraineeRequest.class));
        }

        @Test
        void tooShort() throws Exception {

            updateRequest = UpdateTraineeRequests.Invalid.tooShort();

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.SIZE))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.SIZE));

            verify(facade, never()).update(any(UpdateTraineeRequest.class));
        }

        @Test
        void tooLong() throws Exception {

            updateRequest = UpdateTraineeRequests.Invalid.tooLong();

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);

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

            verify(facade, never()).update(any(UpdateTraineeRequest.class));
        }

        @Test
        void forbiddenCharacter() throws Exception {

            updateRequest = UpdateTraineeRequests.Invalid.forbiddenCharacters();

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.PATTERN))
                    .andExpect(jsonPath("$.details.firstName")
                            .value(ValidationErrorMessages.FirstName.PATTERN))
                    .andExpect(jsonPath("$.details.lastName")
                            .value(ValidationErrorMessages.LastName.PATTERN));

            verify(facade, never()).update(any(UpdateTraineeRequest.class));
        }


        @Test
        void futureBirthdate() throws Exception {

            updateRequest = UpdateTraineeRequests.Invalid.futureBirthdate();

            when(facade.update(any(UpdateTraineeRequest.class)))
                    .thenReturn(null);

            mockMvc.perform(template(updateRequest))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.birthDate")
                            .value(ValidationErrorMessages.BirthDate.PAST));

            verify(facade, never()).update(any(UpdateTraineeRequest.class));
        }
    }

    @Nested
    class deleteRequest_ExpectsCorrectStatusAndFacadeCalling {

        private RequestBuilder template(String username) {
            return delete("/v1-0-0/trainees")
                    .param("username", username);
        }

        private ResultActions expectBadRequest(String username) throws Exception {
            return mockMvc.perform(template(username))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void validUsername() throws Exception {

            final String validUsername = "validUsername213._";

            doNothing().when(facade).deleteByUsername(any(String.class));

            mockMvc.perform(template(validUsername))
                    .andExpect(status().isOk());

            verify(facade, times(1)).deleteByUsername(validUsername);
        }

        @Test
        void tooShortUsername() throws Exception {

            final String tooShort = "gfv";

            doNothing().when(facade).deleteByUsername(any(String.class));

            expectBadRequest(tooShort)
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE));

            verify(facade, times(0)).deleteByUsername(any(String.class));
        }

        @Test
        void tooLongUsername() throws Exception {

            final int blueBorderPlusOne = 30 + 1;

            final String tooLong = "g".repeat(blueBorderPlusOne);

            doNothing().when(facade).deleteByUsername(any(String.class));

            expectBadRequest(tooLong)
                    .andExpect(jsonPath("$.details.username")
                            .value(ValidationErrorMessages.Username.SIZE));

            verify(facade, times(0)).deleteByUsername(any(String.class));
        }

        @Test
        void forbiddenCharacterUsername() throws Exception {

            final String[] invalidUsernames= {
                    "username@",
                    "username#",
                    "username!",
                    "username%"
            };

            doNothing().when(facade).deleteByUsername(any(String.class));

            for (String invalidUsername : invalidUsernames) {
                expectBadRequest(invalidUsername)
                        .andExpect(jsonPath("$.details.username")
                        .value(ValidationErrorMessages.Username.PATTERN));
            }

            verify(facade, times(0)).deleteByUsername(any(String.class));
        }
    }
}
