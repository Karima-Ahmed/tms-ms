package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.AttendeesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendees} and its DTO {@link AttendeesDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrainingClassMapper.class})
public interface AttendeesMapper extends EntityMapper<AttendeesDTO, Attendees> {

    @Mapping(source = "trainingClass.id", target = "trainingClassId")
    AttendeesDTO toDto(Attendees attendees);

    @Mapping(source = "trainingClassId", target = "trainingClass")
    Attendees toEntity(AttendeesDTO attendeesDTO);

    default Attendees fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendees attendees = new Attendees();
        attendees.setId(id);
        return attendees;
    }
}
