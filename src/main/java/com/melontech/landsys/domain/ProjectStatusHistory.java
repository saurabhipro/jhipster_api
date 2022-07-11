package com.melontech.landsys.domain;

import com.melontech.landsys.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A ProjectStatusHistory.
 */
@Entity
@Table(name = "landsys_project_status_history")
public class ProjectStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "jhi_when")
    private LocalDate when;

    @Column(name = "remarks")
    private String remarks;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectStatusHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectStatus getStatus() {
        return this.status;
    }

    public ProjectStatusHistory status(ProjectStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDate getWhen() {
        return this.when;
    }

    public ProjectStatusHistory when(LocalDate when) {
        this.setWhen(when);
        return this;
    }

    public void setWhen(LocalDate when) {
        this.when = when;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public ProjectStatusHistory remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectStatusHistory)) {
            return false;
        }
        return id != null && id.equals(((ProjectStatusHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectStatusHistory{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", when='" + getWhen() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
