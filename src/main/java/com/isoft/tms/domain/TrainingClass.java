package com.isoft.tms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * TrainingClass (tms_training_class) entity.
 */
@Entity
@Table(name = "tms_training_class")
public class TrainingClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "desc_en")
    private String descEn;

    @Column(name = "desc_ar")
    private String descAr;

    @Column(name = "time_from")
    private Instant timeFrom;

    @Column(name = "time_to")
    private Instant timeTo;

    @Column(name = "center_id")
    private Long centerId;

    @Column(name = "faclitator_id")
    private Long faclitatorId;

    @ManyToOne
    @JsonIgnoreProperties("trainingClasses")
    private TrainingType trainingType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSlotId() {
        return slotId;
    }

    public TrainingClass slotId(Long slotId) {
        this.slotId = slotId;
        return this;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public String getDescEn() {
        return descEn;
    }

    public TrainingClass descEn(String descEn) {
        this.descEn = descEn;
        return this;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescAr() {
        return descAr;
    }

    public TrainingClass descAr(String descAr) {
        this.descAr = descAr;
        return this;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    public Instant getTimeFrom() {
        return timeFrom;
    }

    public TrainingClass timeFrom(Instant timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(Instant timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Instant getTimeTo() {
        return timeTo;
    }

    public TrainingClass timeTo(Instant timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(Instant timeTo) {
        this.timeTo = timeTo;
    }

    public Long getCenterId() {
        return centerId;
    }

    public TrainingClass centerId(Long centerId) {
        this.centerId = centerId;
        return this;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getFaclitatorId() {
        return faclitatorId;
    }

    public TrainingClass faclitatorId(Long faclitatorId) {
        this.faclitatorId = faclitatorId;
        return this;
    }

    public void setFaclitatorId(Long faclitatorId) {
        this.faclitatorId = faclitatorId;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public TrainingClass trainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
        return this;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingClass)) {
            return false;
        }
        return id != null && id.equals(((TrainingClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TrainingClass{" +
            "id=" + getId() +
            ", slotId=" + getSlotId() +
            ", descEn='" + getDescEn() + "'" +
            ", descAr='" + getDescAr() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", centerId=" + getCenterId() +
            ", faclitatorId=" + getFaclitatorId() +
            "}";
    }
}
