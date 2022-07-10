package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.KhatedayStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Khatedar.
 */
@Entity
@Table(name = "landsys_khatedar")
public class Khatedar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "case_file_no")
    private String caseFileNo;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "notice_file")
    private byte[] noticeFile;

    @Column(name = "notice_file_content_type")
    private String noticeFileContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private KhatedayStatus status;

    @OneToMany(mappedBy = "khatedar")
    @JsonIgnoreProperties(value = { "landCompensations", "khatedar", "projectLand" }, allowSetters = true)
    private Set<Survey> surveys = new HashSet<>();

    @OneToMany(mappedBy = "khatedar")
    @JsonIgnoreProperties(value = { "paymentAdvice", "khatedar", "survey", "projectLand" }, allowSetters = true)
    private Set<LandCompensation> landCompensations = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "khatedars", "bankBranch" }, allowSetters = true)
    private Citizen citizen;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "land", "khatedars", "surveys", "landCompensations", "paymentAdvices", "project" }, allowSetters = true)
    private ProjectLand projectLand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Khatedar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseFileNo() {
        return this.caseFileNo;
    }

    public Khatedar caseFileNo(String caseFileNo) {
        this.setCaseFileNo(caseFileNo);
        return this;
    }

    public void setCaseFileNo(String caseFileNo) {
        this.caseFileNo = caseFileNo;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Khatedar remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getNoticeFile() {
        return this.noticeFile;
    }

    public Khatedar noticeFile(byte[] noticeFile) {
        this.setNoticeFile(noticeFile);
        return this;
    }

    public void setNoticeFile(byte[] noticeFile) {
        this.noticeFile = noticeFile;
    }

    public String getNoticeFileContentType() {
        return this.noticeFileContentType;
    }

    public Khatedar noticeFileContentType(String noticeFileContentType) {
        this.noticeFileContentType = noticeFileContentType;
        return this;
    }

    public void setNoticeFileContentType(String noticeFileContentType) {
        this.noticeFileContentType = noticeFileContentType;
    }

    public KhatedayStatus getStatus() {
        return this.status;
    }

    public Khatedar status(KhatedayStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(KhatedayStatus status) {
        this.status = status;
    }

    public Set<Survey> getSurveys() {
        return this.surveys;
    }

    public void setSurveys(Set<Survey> surveys) {
        if (this.surveys != null) {
            this.surveys.forEach(i -> i.setKhatedar(null));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.setKhatedar(this));
        }
        this.surveys = surveys;
    }

    public Khatedar surveys(Set<Survey> surveys) {
        this.setSurveys(surveys);
        return this;
    }

    public Khatedar addSurvey(Survey survey) {
        this.surveys.add(survey);
        survey.setKhatedar(this);
        return this;
    }

    public Khatedar removeSurvey(Survey survey) {
        this.surveys.remove(survey);
        survey.setKhatedar(null);
        return this;
    }

    public Set<LandCompensation> getLandCompensations() {
        return this.landCompensations;
    }

    public void setLandCompensations(Set<LandCompensation> landCompensations) {
        if (this.landCompensations != null) {
            this.landCompensations.forEach(i -> i.setKhatedar(null));
        }
        if (landCompensations != null) {
            landCompensations.forEach(i -> i.setKhatedar(this));
        }
        this.landCompensations = landCompensations;
    }

    public Khatedar landCompensations(Set<LandCompensation> landCompensations) {
        this.setLandCompensations(landCompensations);
        return this;
    }

    public Khatedar addLandCompensation(LandCompensation landCompensation) {
        this.landCompensations.add(landCompensation);
        landCompensation.setKhatedar(this);
        return this;
    }

    public Khatedar removeLandCompensation(LandCompensation landCompensation) {
        this.landCompensations.remove(landCompensation);
        landCompensation.setKhatedar(null);
        return this;
    }

    public Citizen getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Khatedar citizen(Citizen citizen) {
        this.setCitizen(citizen);
        return this;
    }

    public ProjectLand getProjectLand() {
        return this.projectLand;
    }

    public void setProjectLand(ProjectLand projectLand) {
        this.projectLand = projectLand;
    }

    public Khatedar projectLand(ProjectLand projectLand) {
        this.setProjectLand(projectLand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Khatedar)) {
            return false;
        }
        return id != null && id.equals(((Khatedar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Khatedar{" +
            "id=" + getId() +
            ", caseFileNo='" + getCaseFileNo() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", noticeFile='" + getNoticeFile() + "'" +
            ", noticeFileContentType='" + getNoticeFileContentType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
