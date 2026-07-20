package com.fitnesstraining.controller;


import com.fitnesstraining.api.UserController;
import com.fitnesstraining.config.JacksonTestConfig;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.UserService;
import com.fitnesstraining.testUtils.TestMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestMapper.class, JacksonTestConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;


    @Nested
    class SetActiveByUsername {

        @Test
        void onValidActivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(service).setActive(eq("username"), eq(true), any(User.class));

            mockMvc.perform(patch("/v1-0-0/users")
                            .param("username", "username")
                            .param("active", "true"))
                    .andExpect(status().isOk());

            verify(service).setActive(
                    eq("username"),
                    eq(true),
                    isNull()
            );
        }

        @Test
        void onValidDeactivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(service).setActive(eq("username"), eq(false), any(User.class));

            mockMvc.perform(patch("/v1-0-0/users")
                            .param("username", "username")
                            .param("active", "false"))
                    .andExpect(status().isOk());

            verify(service).setActive(
                    eq("username"),
                    eq(false),
                    isNull()
            );
        }
    }


    @Nested
    class SetActiveById {

        @Test
        void onValidActivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(service).setActive(eq(100L), eq(true), any(User.class));

            mockMvc.perform(patch("/v1-0-0/users/100")
                            .param("active", "true"))
                    .andExpect(status().isOk());

            verify(service).setActive(
                    eq(100L),
                    eq(true),
                    isNull()
            );
        }

        @Test
        void onValidDeactivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(service).setActive(eq(100L), eq(false), any(User.class));

            mockMvc.perform(patch("/v1-0-0/users/100")
                            .param("active", "false"))
                    .andExpect(status().isOk());

            verify(service).setActive(
                    eq(100L),
                    eq(false),
                    isNull()
            );
        }

    }


    // todo
    @Test
    @Disabled
    void principal() throws Exception {

        Trainee principal = Trainee.builder()
                .id(1L)
                .username("admin")
                .build();

        mockMvc.perform(patch("/v1-0-0/users/100")
                        .param("active", "true")
                        .principal(new TestingAuthenticationToken(principal, null)))
                .andExpect(status().isOk());

        ArgumentCaptor<Trainee> captor = ArgumentCaptor.forClass(Trainee.class);

        verify(service).setActive(
                eq(100L),
                eq(true),
                captor.capture()
        );

        assertEquals("admin", captor.getValue().getUsername());
        assertEquals(1L, captor.getValue().getId());
    }
}
