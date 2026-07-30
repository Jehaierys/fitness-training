package com.fitnesstraining.controller;


import com.fitnesstraining.api.AuthenticationController;
import com.fitnesstraining.config.JacksonTestConfig;
import com.fitnesstraining.config.TestSecurityConfiguration;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.facade.UserFacade;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.fitnesstraining.utils.Paths.BASE_AUTHENTICATION_CONTROLLER_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestSecurityConfiguration.class, JacksonTestConfig.class})
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserFacade facade;


    @Nested
    class SetActiveByUsername {

        @Test
        void onValidActivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(facade).setActive(any(String.class), any(Boolean.class), any(User.class));

            mockMvc.perform(patch(BASE_AUTHENTICATION_CONTROLLER_URL)
                            .param("username", "username123")
                            .param("active", "true"))
                    .andExpect(status().isOk());

            verify(facade).setActive(
                    eq("username123"),
                    eq(true),
                    isNull()
            );
        }

        @Test
        void onValidDeactivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(facade).setActive(any(String.class), any(Boolean.class), any(User.class));

            mockMvc.perform(patch(BASE_AUTHENTICATION_CONTROLLER_URL)
                            .param("username", "username123")
                            .param("active", "false"))
                    .andExpect(status().isOk());

            verify(facade).setActive(
                    eq("username123"),
                    eq(false),
                    isNull()
            );
        }
    }


    @Nested
    class SetActiveById {

        @Test
        void onValidActivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(facade).setActive(any(Long.class), any(Boolean.class), any(User.class));

            mockMvc.perform(patch(BASE_AUTHENTICATION_CONTROLLER_URL + "/100")
                            .param("active", "true"))
                    .andExpect(status().isOk());

            verify(facade).setActive(
                    eq(100L),
                    eq(true),
                    isNull()
            );
        }

        @Test
        void onValidDeactivation_ExpectsOkAndCallsServiceWithCorrectArguments() throws Exception {

            doNothing().when(facade).setActive(any(Long.class), any(Boolean.class), any(User.class));

            mockMvc.perform(patch(BASE_AUTHENTICATION_CONTROLLER_URL + "/100")
                            .param("active", "false"))
                    .andExpect(status().isOk());

            verify(facade).setActive(
                    eq(100L),
                    eq(false),
                    isNull()
            );
        }

    }


    // todo
    @Disabled
    @Test
    void principal() throws Exception {

        Trainee principal = Trainee.builder()
                .id(1L)
                .username("username")
                .build();

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                principal,
                null,
                Collections.emptyList()
        );

        mockMvc.perform(patch(BASE_AUTHENTICATION_CONTROLLER_URL + "/100")
                        .param("active", "true")
                        .principal(authentication))
                .andExpect(status().isOk());

        ArgumentCaptor<Trainee> captor = ArgumentCaptor.forClass(Trainee.class);

        verify(facade).setActive(
                eq(100L),
                eq(true),
                captor.capture()
        );

        assertEquals("username", captor.getValue().getUsername());
        assertEquals(1L, captor.getValue().getId());
    }
}
