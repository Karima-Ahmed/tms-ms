package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.TrainingTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingType} and its DTO {@link TrainingTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainingTypeMapper extends EntityMapper<TrainingTypeDTO, TrainingType> {



    default TrainingType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrainingType trainingType = new TrainingType();
        trainingType.setId(id);
        return trainingType;
    }
}
