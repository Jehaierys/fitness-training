package com.fitnesstraining.utils;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.trainee.response.GetTraineeResponse;
import com.fitnesstraining.domain.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    UserSignUpResponse toUserSignUpResponse(Trainee trainee);

    void toEntity(TraineeSignUpRequest dto, @MappingTarget Trainee trainee);

    GetTraineeResponse toGetTraineeResponse(Trainee trainee);
}
