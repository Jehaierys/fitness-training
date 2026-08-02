package com.fitnesstraining.logic.processor;

import com.fitnesstraining.config.security.BruteForceProtector;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.repository.UserRepository;
import com.fitnesstraining.utils.dto.UsernamePasswordAuthenticationRequests;
import com.fitnesstraining.utils.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class JwtAuthenticatorTest {

    @InjectMocks
    private JwtAuthenticator authenticator;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private BruteForceProtector protector;


    private UsernamePasswordAuthenticationRequest request;

    private UserDetails userDetails;

    private ResponseCookie response;


    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();

        request = null;
        userDetails = null;
        response = null;

        ReflectionTestUtils.setField(authenticator,"jwtSecret","abcdefghifdmkhytgcjbvgibrolopqrstuvwxyz123456");
        ReflectionTestUtils.setField(authenticator, "jwtExpiration", Duration.ofHours(1));
        ReflectionTestUtils.setField(authenticator, "jwtResponseCookieSecure", false);
    }


    @Test
    void authenticateSuccessfully() throws Exception {

        userDetails = Users.traineeSophia();
        request = UsernamePasswordAuthenticationRequests.create();

        doNothing().when(protector).checkAttempts(any(UsernamePasswordAuthenticationRequest.class));
        doReturn(userDetails).when(repository).findByUsername(request.getUsername());
        doReturn(true).when(encoder).matches(any(String.class), any(String.class));



        authenticator.authenticate(request);



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());
        assertEquals(userDetails, authentication.getPrincipal());
        assertEquals(userDetails.getAuthorities(), authentication.getAuthorities());

        verify(protector).checkAttempts(request);
        verify(protector).onSuccessfulLogin(request);
        verify(repository).findByUsername(request.getUsername());
        verify(encoder).matches(anyString(), anyString());

        verifyNoMoreInteractions(protector, repository, encoder);
    }

    @Test
    void authenticationFailsOnNullUserDetails() throws Exception {

        request = UsernamePasswordAuthenticationRequests.create();
        userDetails = null;

        doNothing().when(protector).checkAttempts(request);
        doReturn(null).when(repository).findByUsername(request.getUsername());
        doNothing().when(protector).blockHost(request.getIp());



        assertThrows(UsernameNotFoundException.class, () -> authenticator.authenticate(request));



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertNull(authentication);

        verify(protector).checkAttempts(request);
        verify(repository).findByUsername(request.getUsername());
        verify(protector).blockHost(request.getIp());

        verifyNoMoreInteractions(protector, repository, encoder);
    }

    @Test
    void authenticationFailsOnPasswordMismatch() throws Exception {

        request = UsernamePasswordAuthenticationRequests.create();
        userDetails = Users.traineeSophia();

        doNothing().when(protector).checkAttempts(request);
        doReturn(userDetails).when(repository).findByUsername(request.getUsername());
        doNothing().when(protector).incrementAttempts(request);
        doReturn(false).when(encoder).matches(any(String.class), any(String.class));



        assertThrows(BadCredentialsException.class, () -> authenticator.authenticate(request));



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertNull(authentication);

        verify(protector).checkAttempts(request);
        verify(repository).findByUsername(request.getUsername());
        verify(encoder).matches(anyString(), anyString());
        verify(protector).incrementAttempts(request);

        verifyNoMoreInteractions(protector, repository, encoder);
    }
}
