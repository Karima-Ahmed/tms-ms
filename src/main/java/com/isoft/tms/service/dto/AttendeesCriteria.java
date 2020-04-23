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

/**
 * Criteria class for the {@link com.isoft.tms.domain.Attendees} entity. This class is used
 * in {@link com.isoft.tms.web.rest.AttendeesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attendees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendeesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter applicantId;

    private LongFilter trainingClassId;

    public AttendeesCriteria() {
    }

    public AttendeesCriteria(AttendeesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
        this.trainingClassId = other.trainingClassId == null ? null : other.trainingClassId.copy();
    }

    @Override
    public AttendeesCriteria copy() {
        return new AttendeesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(LongFilter applicantId) {
        this.applicantId = applicantId;
    }

    public LongFilter getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(LongFilter trainingClassId) {
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
        final AttendeesCriteria that = (AttendeesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicantId, that.applicantId) &&
            Objects.equals(trainingClassId, that.trainingClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicantId,
        trainingClassId
        );
    }

    @Override
    public String toString() {
        return "AttendeesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
                (trainingClassId != null ? "trainingClassId=" + trainingClassId + ", " : "") +
            "}";
    }

}
