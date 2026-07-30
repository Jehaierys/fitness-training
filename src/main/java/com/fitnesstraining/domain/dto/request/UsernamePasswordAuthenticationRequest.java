package com.fitnesstraining.domain.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsernamePasswordAuthenticationRequest {

    private String username;
    private String password;
    private String ip;

}