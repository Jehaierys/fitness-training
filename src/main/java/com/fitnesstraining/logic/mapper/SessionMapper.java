package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.dto.response.SessionDto;
import com.fitnesstraining.domain.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "coach", ignore = true)
    @Mapping(target = "trainee", ignore = true)
    @Mapping(target = "sessionType", ignore = true)
    void toEntity(SessionRegistrationRequest dto, @MappingTarget Session session);
//
//    @Mapping(target = "sessionName", source = "name")
//
//    @Mapping(target = "traineeUsername", source = "trainee.username")
//    @Mapping(target = "traineeFirstName", source = "trainee.firstName")
//    @Mapping(target = "traineeLastName", source = "trainee.lastName")
//
//    @Mapping(target = "coachUsername", source = "coach.username")
//    @Mapping(target = "coachFirstName", source = "coach.firstName")
//    @Mapping(target = "coachLastName", source = "coach.lastName")

    SessionDto toSessionDto(Session session);
}