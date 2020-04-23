package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.Attendees} entity.
 */
@ApiModel(description = "Attendees (tms_attendees) entity.")
public class AttendeesDTO implements Serializable {
    
    private Long id;

    private Long applicantId;


    private Long trainingClassId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(Long trainingClassId) {
        this.trainingClassId = trainingClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendeesDTO attendeesDTO = (AttendeesDTO) o;
        if (attendeesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendeesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendeesDTO{" +
            "id=" + getId() +
            ", applicantId=" + getApplicantId() +
            ", trainingClassId=" + getTrainingClassId() +
            "}";
    }
}
