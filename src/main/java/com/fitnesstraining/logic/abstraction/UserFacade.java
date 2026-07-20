package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.dto.abstraction.*;

public interface UserFacade {

    RegisterUserResponse register(RegisterUserRequest request);

    UpdateUserResponse update(UpdateUserRequest request);

    GetUserResponse findByUsername(String username);

}
