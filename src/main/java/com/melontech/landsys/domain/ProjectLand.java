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
    @JsonIgnoreProperties(value = { "village", "unit", "landType", "state", "projectLands" }, allowSetters = true)
    private Land land;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectLands" }, allowSetters = true)
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties(value = { "projectLands" }, allowSetters = true)
    private NoticeStatusInfo noticeStatusInfo;

    @JsonIgnoreProperties(value = { "projectLand", "landCompensation", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    @OneToOne(mappedBy = "projectLand")
    private Survey survey;

    @JsonIgnoreProperties(value = { "projectLand", "survey", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    @OneToOne(mappedBy = "projectLand")
    private LandCompensation landCompensation;

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(
        value = { "khatedar", "landCompensation", "projectLand", "survey", "paymentFileRecon", "paymentFile", "paymentAdviceDetails" },
        allowSetters = true
    )
    private Set<PaymentAdvice> paymentAdvices = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "paymentAdvice", "projectLand", "khatedar" }, allowSetters = true)
    private Set<PaymentAdviceDetails> paymentAdviceDetails = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(
        value = { "khatedar", "paymentAdvice", "projectLand", "survey", "bank", "bankBranch", "landCompensation", "paymentFileHeader" },
        allowSetters = true
    )
    private Set<PaymentFile> paymentFiles = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "projectLand", "citizen", "paymentAdvice", "paymentFile", "paymentAdviceDetails" }, allowSetters = true)
    private Set<Khatedar> khatedars = new HashSet<>();

    @OneToMany(mappedBy = "projectLand")
    @JsonIgnoreProperties(value = { "projectLand", "paymentFiles" }, allowSetters = true)
    private Set<PaymentFileHeader> paymentFileHeaders = new HashSet<>();

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

    public Survey getSurvey() {
        return this.survey;
    }

    public void setSurvey(Survey survey) {
        if (this.survey != null) {
            this.survey.setProjectLand(null);
        }
        if (survey != null) {
            survey.setProjectLand(this);
        }
        this.survey = survey;
    }

    public ProjectLand survey(Survey survey) {
        this.setSurvey(survey);
        return this;
    }

    public LandCompensation getLandCompensation() {
        return this.landCompensation;
    }

    public void setLandCompensation(LandCompensation landCompensation) {
        if (this.landCompensation != null) {
            this.landCompensation.setProjectLand(null);
        }
        if (landCompensation != null) {
            landCompensation.setProjectLand(this);
        }
        this.landCompensation = landCompensation;
    }

    public ProjectLand landCompensation(LandCompensation landCompensation) {
        this.setLandCompensation(landCompensation);
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

    public Set<PaymentAdviceDetails> getPaymentAdviceDetails() {
        return this.paymentAdviceDetails;
    }

    public void setPaymentAdviceDetails(Set<PaymentAdviceDetails> paymentAdviceDetails) {
        if (this.paymentAdviceDetails != null) {
            this.paymentAdviceDetails.forEach(i -> i.setProjectLand(null));
        }
        if (paymentAdviceDetails != null) {
            paymentAdviceDetails.forEach(i -> i.setProjectLand(this));
        }
        this.paymentAdviceDetails = paymentAdviceDetails;
    }

    public ProjectLand paymentAdviceDetails(Set<PaymentAdviceDetails> paymentAdviceDetails) {
        this.setPaymentAdviceDetails(paymentAdviceDetails);
        return this;
    }

    public ProjectLand addPaymentAdviceDetails(PaymentAdviceDetails paymentAdviceDetails) {
        this.paymentAdviceDetails.add(paymentAdviceDetails);
        paymentAdviceDetails.setProjectLand(this);
        return this;
    }

    public ProjectLand removePaymentAdviceDetails(PaymentAdviceDetails paymentAdviceDetails) {
        this.paymentAdviceDetails.remove(paymentAdviceDetails);
        paymentAdviceDetails.setProjectLand(null);
        return this;
    }

    public Set<PaymentFile> getPaymentFiles() {
        return this.paymentFiles;
    }

    public void setPaymentFiles(Set<PaymentFile> paymentFiles) {
        if (this.paymentFiles != null) {
            this.paymentFiles.forEach(i -> i.setProjectLand(null));
        }
        if (paymentFiles != null) {
            paymentFiles.forEach(i -> i.setProjectLand(this));
        }
        this.paymentFiles = paymentFiles;
    }

    public ProjectLand paymentFiles(Set<PaymentFile> paymentFiles) {
        this.setPaymentFiles(paymentFiles);
        return this;
    }

    public ProjectLand addPaymentFile(PaymentFile paymentFile) {
        this.paymentFiles.add(paymentFile);
        paymentFile.setProjectLand(this);
        return this;
    }

    public ProjectLand removePaymentFile(PaymentFile paymentFile) {
        this.paymentFiles.remove(paymentFile);
        paymentFile.setProjectLand(null);
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

    public Set<PaymentFileHeader> getPaymentFileHeaders() {
        return this.paymentFileHeaders;
    }

    public void setPaymentFileHeaders(Set<PaymentFileHeader> paymentFileHeaders) {
        if (this.paymentFileHeaders != null) {
            this.paymentFileHeaders.forEach(i -> i.setProjectLand(null));
        }
        if (paymentFileHeaders != null) {
            paymentFileHeaders.forEach(i -> i.setProjectLand(this));
        }
        this.paymentFileHeaders = paymentFileHeaders;
    }

    public ProjectLand paymentFileHeaders(Set<PaymentFileHeader> paymentFileHeaders) {
        this.setPaymentFileHeaders(paymentFileHeaders);
        return this;
    }

    public ProjectLand addPaymentFileHeader(PaymentFileHeader paymentFileHeader) {
        this.paymentFileHeaders.add(paymentFileHeader);
        paymentFileHeader.setProjectLand(this);
        return this;
    }

    public ProjectLand removePaymentFileHeader(PaymentFileHeader paymentFileHeader) {
        this.paymentFileHeaders.remove(paymentFileHeader);
        paymentFileHeader.setProjectLand(null);
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
