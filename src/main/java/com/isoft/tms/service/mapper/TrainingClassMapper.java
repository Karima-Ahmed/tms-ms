package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.TrainingClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingClass} and its DTO {@link TrainingClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrainingTypeMapper.class})
public interface TrainingClassMapper extends EntityMapper<TrainingClassDTO, TrainingClass> {

    @Mapping(source = "trainingType.id", target = "trainingTypeId")
    TrainingClassDTO toDto(TrainingClass trainingClass);

    @Mapping(source = "trainingTypeId", target = "trainingType")
    TrainingClass toEntity(TrainingClassDTO trainingClassDTO);

    default TrainingClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrainingClass trainingClass = new TrainingClass();
        trainingClass.setId(id);
        return trainingClass;
    }
}
