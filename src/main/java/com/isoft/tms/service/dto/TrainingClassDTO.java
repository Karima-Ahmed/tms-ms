package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.TrainingClass} entity.
 */
@ApiModel(description = "TrainingClass (tms_training_class) entity.")
public class TrainingClassDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long slotId;

    @NotNull
    private String descEn;

    private String descAr;

    private Instant timeFrom;

    private Instant timeTo;

    private Long centerId;

    private Long faclitatorId;


    private Long trainingTypeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescAr() {
        return descAr;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    public Instant getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Instant timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Instant getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Instant timeTo) {
        this.timeTo = timeTo;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getFaclitatorId() {
        return faclitatorId;
    }

    public void setFaclitatorId(Long faclitatorId) {
        this.faclitatorId = faclitatorId;
    }

    public Long getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingClassDTO trainingClassDTO = (TrainingClassDTO) o;
        if (trainingClassDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainingClassDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainingClassDTO{" +
            "id=" + getId() +
            ", slotId=" + getSlotId() +
            ", descEn='" + getDescEn() + "'" +
            ", descAr='" + getDescAr() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", centerId=" + getCenterId() +
            ", faclitatorId=" + getFaclitatorId() +
            ", trainingTypeId=" + getTrainingTypeId() +
            "}";
    }
}
