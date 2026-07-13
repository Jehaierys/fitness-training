package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeProfileRequest;
import com.fitnesstraining.domain.dto.trainee.response.GetTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeProfileResponse;
import com.fitnesstraining.domain.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    UserSignUpResponse toUserSignUpResponse(Trainee trainee);

    void toEntity(TraineeSignUpRequest dto, @MappingTarget Trainee trainee);

    GetTraineeResponse toGetTraineeResponse(Trainee trainee);

    @Mapping(target = "coaches", ignore = true)
    UpdateTraineeProfileResponse toUpdateTraineeProfileResponse(Trainee trainee);

    @Mapping(target = "username", ignore = true)
    void toEntity(UpdateTraineeProfileRequest dto, @MappingTarget Trainee trainee);
}
