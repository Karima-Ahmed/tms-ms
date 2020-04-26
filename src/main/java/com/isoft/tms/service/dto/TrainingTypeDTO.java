package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.TrainingType} entity.
 */
@ApiModel(description = "TrainingType (tms_training_type) entity.")
public class TrainingTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nameEn;

    private String nameAr;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingTypeDTO trainingTypeDTO = (TrainingTypeDTO) o;
        if (trainingTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainingTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainingTypeDTO{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            "}";
    }
}
