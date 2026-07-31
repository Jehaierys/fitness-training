package com.fitnesstraining.testUtils.dto;

import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;

public final class UsernamePasswordAuthenticationRequests {

    public static UsernamePasswordAuthenticationRequest create() {
        return UsernamePasswordAuthenticationRequest.builder()
                .username("sophia.miller")
                .password("Password!123")
                .ip("676.767.676.767")
                .build();
    }
}
