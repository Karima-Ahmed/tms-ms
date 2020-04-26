package com.isoft.tms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * TrainingType (tms_training_type) entity.
 */
@Entity
@Table(name = "tms_training_type")
public class TrainingType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trtyp_seq")
    @SequenceGenerator(name = "trtyp_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_ar")
    private String nameAr;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public TrainingType nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public TrainingType nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingType)) {
            return false;
        }
        return id != null && id.equals(((TrainingType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            "}";
    }
}
