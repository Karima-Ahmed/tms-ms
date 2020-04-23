package com.isoft.tms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.tms.domain.TrainingClass} entity. This class is used
 * in {@link com.isoft.tms.web.rest.TrainingClassResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /training-classes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrainingClassCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter slotId;

    private StringFilter descEn;

    private StringFilter descAr;

    private InstantFilter timeFrom;

    private InstantFilter timeTo;

    private LongFilter centerId;

    private LongFilter faclitatorId;

    private LongFilter trainingTypeId;

    public TrainingClassCriteria() {
    }

    public TrainingClassCriteria(TrainingClassCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.slotId = other.slotId == null ? null : other.slotId.copy();
        this.descEn = other.descEn == null ? null : other.descEn.copy();
        this.descAr = other.descAr == null ? null : other.descAr.copy();
        this.timeFrom = other.timeFrom == null ? null : other.timeFrom.copy();
        this.timeTo = other.timeTo == null ? null : other.timeTo.copy();
        this.centerId = other.centerId == null ? null : other.centerId.copy();
        this.faclitatorId = other.faclitatorId == null ? null : other.faclitatorId.copy();
        this.trainingTypeId = other.trainingTypeId == null ? null : other.trainingTypeId.copy();
    }

    @Override
    public TrainingClassCriteria copy() {
        return new TrainingClassCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSlotId() {
        return slotId;
    }

    public void setSlotId(LongFilter slotId) {
        this.slotId = slotId;
    }

    public StringFilter getDescEn() {
        return descEn;
    }

    public void setDescEn(StringFilter descEn) {
        this.descEn = descEn;
    }

    public StringFilter getDescAr() {
        return descAr;
    }

    public void setDescAr(StringFilter descAr) {
        this.descAr = descAr;
    }

    public InstantFilter getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(InstantFilter timeFrom) {
        this.timeFrom = timeFrom;
    }

    public InstantFilter getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(InstantFilter timeTo) {
        this.timeTo = timeTo;
    }

    public LongFilter getCenterId() {
        return centerId;
    }

    public void setCenterId(LongFilter centerId) {
        this.centerId = centerId;
    }

    public LongFilter getFaclitatorId() {
        return faclitatorId;
    }

    public void setFaclitatorId(LongFilter faclitatorId) {
        this.faclitatorId = faclitatorId;
    }

    public LongFilter getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(LongFilter trainingTypeId) {
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
        final TrainingClassCriteria that = (TrainingClassCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(slotId, that.slotId) &&
            Objects.equals(descEn, that.descEn) &&
            Objects.equals(descAr, that.descAr) &&
            Objects.equals(timeFrom, that.timeFrom) &&
            Objects.equals(timeTo, that.timeTo) &&
            Objects.equals(centerId, that.centerId) &&
            Objects.equals(faclitatorId, that.faclitatorId) &&
            Objects.equals(trainingTypeId, that.trainingTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        slotId,
        descEn,
        descAr,
        timeFrom,
        timeTo,
        centerId,
        faclitatorId,
        trainingTypeId
        );
    }

    @Override
    public String toString() {
        return "TrainingClassCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (slotId != null ? "slotId=" + slotId + ", " : "") +
                (descEn != null ? "descEn=" + descEn + ", " : "") +
                (descAr != null ? "descAr=" + descAr + ", " : "") +
                (timeFrom != null ? "timeFrom=" + timeFrom + ", " : "") +
                (timeTo != null ? "timeTo=" + timeTo + ", " : "") +
                (centerId != null ? "centerId=" + centerId + ", " : "") +
                (faclitatorId != null ? "faclitatorId=" + faclitatorId + ", " : "") +
                (trainingTypeId != null ? "trainingTypeId=" + trainingTypeId + ", " : "") +
            "}";
    }

}
