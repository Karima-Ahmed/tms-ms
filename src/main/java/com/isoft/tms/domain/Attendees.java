package com.isoft.tms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Attendees (tms_attendees) entity.
 */
@Entity
@Table(name = "tms_attendees")
public class Attendees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "applicant_id")
    private Long applicantId;

    @ManyToOne
    @JsonIgnoreProperties("attendees")
    private TrainingClass trainingClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public Attendees applicantId(Long applicantId) {
        this.applicantId = applicantId;
        return this;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public TrainingClass getTrainingClass() {
        return trainingClass;
    }

    public Attendees trainingClass(TrainingClass trainingClass) {
        this.trainingClass = trainingClass;
        return this;
    }

    public void setTrainingClass(TrainingClass trainingClass) {
        this.trainingClass = trainingClass;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendees)) {
            return false;
        }
        return id != null && id.equals(((Attendees) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Attendees{" +
            "id=" + getId() +
            ", applicantId=" + getApplicantId() +
            "}";
    }
}
