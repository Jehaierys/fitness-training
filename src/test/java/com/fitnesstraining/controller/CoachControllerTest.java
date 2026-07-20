package com.fitnesstraining.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstraining.api.CoachController;
import com.fitnesstraining.config.JacksonTestConfig;
import com.fitnesstraining.domain.dto.coach.request.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachRequest;
import com.fitnesstraining.logic.facade.CoachFacade;
import com.fitnesstraining.testUtils.TestMapper;
import com.fitnesstraining.testUtils.dto.RegisterCoachRequests;
import com.fitnesstraining.testUtils.dto.UpdateCoachRequests;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
