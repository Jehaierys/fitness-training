package com.fitnesstraining.utils;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.entity.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    GetCoachResponse toGetCoachResponse(Coach coach);

    UserSignUpResponse toUserSignUpResponse(Coach coach);

    Coach toCoach(GetCoachResponse getCoachResponse);

    void toEntity(CoachSignUpRequest dto, @MappingTarget Coach coach);
}
