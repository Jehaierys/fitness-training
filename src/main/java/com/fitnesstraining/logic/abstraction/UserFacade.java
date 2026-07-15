package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.dto.abstraction.*;

public interface UserFacade {

    UserSignUpResponse signUp(UserSignUpRequest request);

    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest request);

    GetUserResponse findByUsername(String username);

    void setActive(Activated request);
}
