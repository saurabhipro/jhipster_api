package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.HissaType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProjectLand.
 */
@Entity
@Table(name = "landsys_project_land")
public class ProjectLand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "documents")
    private byte[] documents;

    @Column(name = "documents_content_type")
    private String documentsContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "hissa_type")
    private HissaType hissaType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectLands" }, allowSetters = true)
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "state", "village", "unit", "landType", "projectLands" }, allowSetters = true)
    private Land land;

    @ManyToOne
    @JsonIgnoreProperties(value = { "khatedars", "projectLands" }, allowSetters = true)
    private NoticeStatusInfo noticeStatusInfo;

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "citizen", "projectLand", "noticeStatusInfo", "survey", "landCompensations" }, allowSetters = true)
    private Set<Khatedar> khatedars = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "khatedar", "projectLand", "landCompensations" }, allowSetters = true)
    private Set<Survey> surveys = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "khatedar", "survey", "projectLand", "paymentAdvices" }, allowSetters = true)
    private Set<LandCompensation> landCompensations = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "projectLand", "landCompensation", "paymentFileRecon" }, allowSetters = true)
    private Set<PaymentAdvice> paymentAdvices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectLand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public ProjectLand remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getDocuments() {
        return this.documents;
    }

    public ProjectLand documents(byte[] documents) {
        this.setDocuments(documents);
        return this;
    }

    public void setDocuments(byte[] documents) {
        this.documents = documents;
    }

    public String getDocumentsContentType() {
        return this.documentsContentType;
    }

    public ProjectLand documentsContentType(String documentsContentType) {
        this.documentsContentType = documentsContentType;
        return this;
    }

    public void setDocumentsContentType(String documentsContentType) {
        this.documentsContentType = documentsContentType;
    }

    public HissaType getHissaType() {
        return this.hissaType;
    }

    public ProjectLand hissaType(HissaType hissaType) {
        this.setHissaType(hissaType);
        return this;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectLand project(Project project) {
        this.setProject(project);
        return this;
    }

    public Land getLand() {
        return this.land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public ProjectLand land(Land land) {
        this.setLand(land);
        return this;
    }

    public NoticeStatusInfo getNoticeStatusInfo() {
        return this.noticeStatusInfo;
    }

    public void setNoticeStatusInfo(NoticeStatusInfo noticeStatusInfo) {
        this.noticeStatusInfo = noticeStatusInfo;
    }

    public ProjectLand noticeStatusInfo(NoticeStatusInfo noticeStatusInfo) {
        this.setNoticeStatusInfo(noticeStatusInfo);
        return this;
    }

    public Set<Khatedar> getKhatedars() {
        return this.khatedars;
    }

    public void setKhatedars(Set<Khatedar> khatedars) {
        if (this.khatedars != null) {
            this.khatedars.forEach(i -> i.setProjectLand(null));
        }
        if (khatedars != null) {
            khatedars.forEach(i -> i.setProjectLand(this));
        }
        this.khatedars = khatedars;
    }

    public ProjectLand khatedars(Set<Khatedar> khatedars) {
        this.setKhatedars(khatedars);
        return this;
    }

    public ProjectLand addKhatedar(Khatedar khatedar) {
        this.khatedars.add(khatedar);
        khatedar.setProjectLand(this);
        return this;
    }

    public ProjectLand removeKhatedar(Khatedar khatedar) {
        this.khatedars.remove(khatedar);
        khatedar.setProjectLand(null);
        return this;
    }

    public Set<Survey> getSurveys() {
        return this.surveys;
    }

    public void setSurveys(Set<Survey> surveys) {
        if (this.surveys != null) {
            this.surveys.forEach(i -> i.setProjectLand(null));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.setProjectLand(this));
        }
        this.surveys = surveys;
    }

    public ProjectLand surveys(Set<Survey> surveys) {
        this.setSurveys(surveys);
        return this;
    }

    public ProjectLand addSurvey(Survey survey) {
        this.surveys.add(survey);
        survey.setProjectLand(this);
        return this;
    }

    public ProjectLand removeSurvey(Survey survey) {
        this.surveys.remove(survey);
        survey.setProjectLand(null);
        return this;
    }

    public Set<LandCompensation> getLandCompensations() {
        return this.landCompensations;
    }

    public void setLandCompensations(Set<LandCompensation> landCompensations) {
        if (this.landCompensations != null) {
            this.landCompensations.forEach(i -> i.setProjectLand(null));
        }
        if (landCompensations != null) {
            landCompensations.forEach(i -> i.setProjectLand(this));
        }
        this.landCompensations = landCompensations;
    }

    public ProjectLand landCompensations(Set<LandCompensation> landCompensations) {
        this.setLandCompensations(landCompensations);
        return this;
    }

    public ProjectLand addLandCompensation(LandCompensation landCompensation) {
        this.landCompensations.add(landCompensation);
        landCompensation.setProjectLand(this);
        return this;
    }

    public ProjectLand removeLandCompensation(LandCompensation landCompensation) {
        this.landCompensations.remove(landCompensation);
        landCompensation.setProjectLand(null);
        return this;
    }

    public Set<PaymentAdvice> getPaymentAdvices() {
        return this.paymentAdvices;
    }

    public void setPaymentAdvices(Set<PaymentAdvice> paymentAdvices) {
        if (this.paymentAdvices != null) {
            this.paymentAdvices.forEach(i -> i.setProjectLand(null));
        }
        if (paymentAdvices != null) {
            paymentAdvices.forEach(i -> i.setProjectLand(this));
        }
        this.paymentAdvices = paymentAdvices;
    }

    public ProjectLand paymentAdvices(Set<PaymentAdvice> paymentAdvices) {
        this.setPaymentAdvices(paymentAdvices);
        return this;
    }

    public ProjectLand addPaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvices.add(paymentAdvice);
        paymentAdvice.setProjectLand(this);
        return this;
    }

    public ProjectLand removePaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvices.remove(paymentAdvice);
        paymentAdvice.setProjectLand(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectLand)) {
            return false;
        }
        return id != null && id.equals(((ProjectLand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectLand{" +
            "id=" + getId() +
            ", remarks='" + getRemarks() + "'" +
            ", documents='" + getDocuments() + "'" +
            ", documentsContentType='" + getDocumentsContentType() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            "}";
    }
}
