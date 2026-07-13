package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachProfileRequest;
import com.fitnesstraining.domain.dto.coach.response.CoachDto;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.domain.entity.Coach;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    GetCoachResponse toGetCoachResponse(Coach coach);

    UserSignUpResponse toUserSignUpResponse(Coach coach);

    void toEntity(CoachSignUpRequest dto, @MappingTarget Coach coach);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "active", source = "isActive")
    void toEntity(UpdateCoachProfileRequest dto, @MappingTarget Coach coach);

    @Mapping(target = "isActive", source = "active") // Idk how this works
    UpdateCoachProfileResponse toUpdateCoachProfileResponse(Coach coach);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "specialization", source = "specialization")
    CoachDto toCoachDto(Coach coach);
}