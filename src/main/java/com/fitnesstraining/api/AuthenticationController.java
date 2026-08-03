package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.AuthenticationControllerApi;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.dto.response.JwtAuthenticationResponse;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static com.fitnesstraining.utils.SharedStrings.JWT_COOKIE_NAME;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    private final UserService service;


    public void changePassword(User user, String newPassword) {
        service.changePassword(user, newPassword);
    }

    public ResponseEntity<JwtAuthenticationResponse> login(
            UsernamePasswordAuthenticationRequest request,
            HttpServletRequest servletRequest
    ) {
        request.setIp(servletRequest.getRemoteHost());

        final String cookie = service.authenticate(request).toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }

    public ResponseEntity<Void> setActive(Long id, Boolean active, User principal) {
        service.setActive(id, active, principal);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> setActive(String username, Boolean active, User principal) {
        service.setActive(username, active, principal);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> logout() {

        final ResponseCookie cookie = ResponseCookie.from(JWT_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
