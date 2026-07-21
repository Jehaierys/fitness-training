package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.dto.request.RegisterUserRequest;
import com.fitnesstraining.domain.dto.request.UpdateUserRequest;
import com.fitnesstraining.domain.dto.response.GetUserResponse;
import com.fitnesstraining.domain.dto.response.RegisterUserResponse;
import com.fitnesstraining.domain.dto.response.UpdateUserResponse;

public interface UserFacade {

    RegisterUserResponse register(RegisterUserRequest request);

    UpdateUserResponse update(UpdateUserRequest request);

    GetUserResponse findByUsername(String username);

}
