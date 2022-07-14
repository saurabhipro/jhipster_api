package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.NoticeStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A NoticeStatusInfo.
 */
@Entity
@Table(name = "landsys_notice_status")
public class NoticeStatusInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NoticeStatus status;

    @OneToMany(mappedBy = "noticeStatusInfo")
    @JsonIgnoreProperties(
        value = {
            "land",
            "project",
            "citizen",
            "noticeStatusInfo",
            "survey",
            "landCompensation",
            "paymentAdvices",
            "paymentAdviceDetails",
            "paymentFiles",
            "khatedars",
        },
        allowSetters = true
    )
    private Set<ProjectLand> projectLands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NoticeStatusInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NoticeStatus getStatus() {
        return this.status;
    }

    public NoticeStatusInfo status(NoticeStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(NoticeStatus status) {
        this.status = status;
    }

    public Set<ProjectLand> getProjectLands() {
        return this.projectLands;
    }

    public void setProjectLands(Set<ProjectLand> projectLands) {
        if (this.projectLands != null) {
            this.projectLands.forEach(i -> i.setNoticeStatusInfo(null));
        }
        if (projectLands != null) {
            projectLands.forEach(i -> i.setNoticeStatusInfo(this));
        }
        this.projectLands = projectLands;
    }

    public NoticeStatusInfo projectLands(Set<ProjectLand> projectLands) {
        this.setProjectLands(projectLands);
        return this;
    }

    public NoticeStatusInfo addProjectLand(ProjectLand projectLand) {
        this.projectLands.add(projectLand);
        projectLand.setNoticeStatusInfo(this);
        return this;
    }

    public NoticeStatusInfo removeProjectLand(ProjectLand projectLand) {
        this.projectLands.remove(projectLand);
        projectLand.setNoticeStatusInfo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticeStatusInfo)) {
            return false;
        }
        return id != null && id.equals(((NoticeStatusInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeStatusInfo{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
