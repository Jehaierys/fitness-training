package com.fitnesstraining.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UsernamePasswordAuthenticationRequest {

    private String username;
    private String password;
    private String ip;

}