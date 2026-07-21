package com.fitnesstraining.logic.mapper;

import com.fitnesstraining.domain.dto.request.trainee.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.request.trainee.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.response.trainee.GetTraineeResponse;
import com.fitnesstraining.domain.dto.response.trainee.RegisterTraineeResponse;
import com.fitnesstraining.domain.dto.response.trainee.UpdateTraineeResponse;
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
