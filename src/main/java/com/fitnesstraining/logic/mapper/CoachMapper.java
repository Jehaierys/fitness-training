package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachProfileRequest;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.domain.entity.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    GetCoachResponse toGetCoachResponse(Coach coach);

    UserSignUpResponse toUserSignUpResponse(Coach coach);

    Coach toCoach(GetCoachResponse getCoachResponse);

    void toEntity(CoachSignUpRequest dto, @MappingTarget Coach coach);

    @Mapping(target = "username", ignore = true)
    void toEntity(UpdateCoachProfileRequest dto, @MappingTarget Coach coach);

    UpdateCoachProfileResponse toUpdateCoachProfileResponse(Coach coach);
}
