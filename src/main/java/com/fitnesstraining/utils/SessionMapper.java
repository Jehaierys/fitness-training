package com.fitnesstraining.utils;

import com.fitnesstraining.domain.dto.session.SessionRegistrationRequest;
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
}