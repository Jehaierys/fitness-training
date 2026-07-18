package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.trainee.request.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.response.GetTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.RegisterTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeResponse;
import com.fitnesstraining.domain.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    RegisterTraineeResponse toRegisterTraineeResponse(Trainee trainee);

    @Mapping(target = "password", ignore = true)
    void toEntity(RegisterTraineeRequest dto, @MappingTarget Trainee trainee);

    GetTraineeResponse toGetTraineeResponse(Trainee trainee);

    @Mapping(target = "coaches", ignore = true)
    UpdateTraineeResponse toUpdateTraineeResponse(Trainee trainee);

    @Mapping(target = "username", ignore = true)
    void toEntity(UpdateTraineeRequest dto, @MappingTarget Trainee trainee);
}
