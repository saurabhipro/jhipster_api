package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @NotNull
    @Column(name = "case_file_no", nullable = false, unique = true)
    private String caseFileNo;

    @NotNull
    @Column(name = "remarks", nullable = false, unique = true)
    private String remarks;

    @Column(name = "khatedar_status")
    private String khatedarStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "land",
            "project",
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
    private ProjectLand projectLand;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bankBranch", "khatedars" }, allowSetters = true)
    private Citizen citizen;

    @JsonIgnoreProperties(
        value = { "khatedar", "landCompensation", "projectLand", "survey", "paymentFileRecon", "paymentFile", "paymentAdviceDetails" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "khatedar")
    private PaymentAdvice paymentAdvice;

    @JsonIgnoreProperties(
        value = { "khatedar", "paymentAdvice", "projectLand", "survey", "bank", "bankBranch", "landCompensation" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "khatedar")
    private PaymentFile paymentFile;

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

    public String getKhatedarStatus() {
        return this.khatedarStatus;
    }

    public Khatedar khatedarStatus(String khatedarStatus) {
        this.setKhatedarStatus(khatedarStatus);
        return this;
    }

    public void setKhatedarStatus(String khatedarStatus) {
        this.khatedarStatus = khatedarStatus;
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

    public PaymentAdvice getPaymentAdvice() {
        return this.paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdvice paymentAdvice) {
        if (this.paymentAdvice != null) {
            this.paymentAdvice.setKhatedar(null);
        }
        if (paymentAdvice != null) {
            paymentAdvice.setKhatedar(this);
        }
        this.paymentAdvice = paymentAdvice;
    }

    public Khatedar paymentAdvice(PaymentAdvice paymentAdvice) {
        this.setPaymentAdvice(paymentAdvice);
        return this;
    }

    public PaymentFile getPaymentFile() {
        return this.paymentFile;
    }

    public void setPaymentFile(PaymentFile paymentFile) {
        if (this.paymentFile != null) {
            this.paymentFile.setKhatedar(null);
        }
        if (paymentFile != null) {
            paymentFile.setKhatedar(this);
        }
        this.paymentFile = paymentFile;
    }

    public Khatedar paymentFile(PaymentFile paymentFile) {
        this.setPaymentFile(paymentFile);
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
            ", khatedarStatus='" + getKhatedarStatus() + "'" +
            "}";
    }
}
