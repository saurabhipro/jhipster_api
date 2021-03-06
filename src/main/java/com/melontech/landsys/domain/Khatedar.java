package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.KhatedarStatus;
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

    @NotNull
    @Column(name = "case_file_no", nullable = false, unique = true)
    private String caseFileNo;

    @NotNull
    @Column(name = "remarks", nullable = false, unique = true)
    private String remarks;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hissa_type", nullable = false)
    private HissaType hissaType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "khatedar_status", nullable = false)
    private KhatedarStatus khatedarStatus;

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
            "paymentFileHeaders",
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
        value = { "khatedar", "paymentAdvice", "projectLand", "survey", "bank", "bankBranch", "landCompensation", "paymentFileHeader" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "khatedar")
    private PaymentFile paymentFile;

    @OneToMany(mappedBy = "khatedar")
    @JsonIgnoreProperties(value = { "paymentAdvice", "projectLand", "khatedar" }, allowSetters = true)
    private Set<PaymentAdviceDetails> paymentAdviceDetails = new HashSet<>();

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

    public HissaType getHissaType() {
        return this.hissaType;
    }

    public Khatedar hissaType(HissaType hissaType) {
        this.setHissaType(hissaType);
        return this;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public KhatedarStatus getKhatedarStatus() {
        return this.khatedarStatus;
    }

    public Khatedar khatedarStatus(KhatedarStatus khatedarStatus) {
        this.setKhatedarStatus(khatedarStatus);
        return this;
    }

    public void setKhatedarStatus(KhatedarStatus khatedarStatus) {
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

    public Set<PaymentAdviceDetails> getPaymentAdviceDetails() {
        return this.paymentAdviceDetails;
    }

    public void setPaymentAdviceDetails(Set<PaymentAdviceDetails> paymentAdviceDetails) {
        if (this.paymentAdviceDetails != null) {
            this.paymentAdviceDetails.forEach(i -> i.setKhatedar(null));
        }
        if (paymentAdviceDetails != null) {
            paymentAdviceDetails.forEach(i -> i.setKhatedar(this));
        }
        this.paymentAdviceDetails = paymentAdviceDetails;
    }

    public Khatedar paymentAdviceDetails(Set<PaymentAdviceDetails> paymentAdviceDetails) {
        this.setPaymentAdviceDetails(paymentAdviceDetails);
        return this;
    }

    public Khatedar addPaymentAdviceDetails(PaymentAdviceDetails paymentAdviceDetails) {
        this.paymentAdviceDetails.add(paymentAdviceDetails);
        paymentAdviceDetails.setKhatedar(this);
        return this;
    }

    public Khatedar removePaymentAdviceDetails(PaymentAdviceDetails paymentAdviceDetails) {
        this.paymentAdviceDetails.remove(paymentAdviceDetails);
        paymentAdviceDetails.setKhatedar(null);
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
            ", hissaType='" + getHissaType() + "'" +
            ", khatedarStatus='" + getKhatedarStatus() + "'" +
            "}";
    }
}
