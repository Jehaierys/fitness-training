package com.fitnesstraining.service.mapper;

import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.request.coach.UpdateCoachRequest;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.dto.response.coach.CoachDto;
import com.fitnesstraining.domain.dto.response.coach.GetCoachResponse;
import com.fitnesstraining.domain.dto.response.coach.RegisterCoachResponse;
import com.fitnesstraining.domain.dto.response.coach.UpdateCoachResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    @Mapping(target = "isActive", source = "active") // ???
    GetCoachResponse toGetCoachResponse(Coach coach);

    RegisterCoachResponse toRegisterCoachResponse(Coach coach);

    @Mapping(target = "password", ignore = true)
    void toEntity(RegisterCoachRequest dto, @MappingTarget Coach coach);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "active", source = "isActive")
    void toEntity(UpdateCoachRequest dto, @MappingTarget Coach coach);

    @Mapping(target = "isActive", source = "active") // Idk how this works
    UpdateCoachResponse toUpdateCoachResponse(Coach coach);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "specialization", source = "specialization")
    CoachDto toCoachDto(Coach coach);
}