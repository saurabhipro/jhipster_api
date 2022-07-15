package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PaymentFileHeader.
 */
@Entity
@Table(name = "landsys_payment_file_header")
public class PaymentFileHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "grand_total_payment_amount", nullable = false)
    private Double grandTotalPaymentAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentAdviceType paymentMode;

    @Column(name = "approver_remarks")
    private String approverRemarks;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectLands" }, allowSetters = true)
    private Project project;

    @OneToMany(mappedBy = "paymentFileHeader")
    @JsonIgnoreProperties(
        value = {
            "khatedar", "paymentAdvice", "projectLand", "survey", "bank", "bankBranch", "landCompensation", "paymentFileHeader", "project",
        },
        allowSetters = true
    )
    private Set<PaymentFile> paymentFiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentFileHeader id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrandTotalPaymentAmount() {
        return this.grandTotalPaymentAmount;
    }

    public PaymentFileHeader grandTotalPaymentAmount(Double grandTotalPaymentAmount) {
        this.setGrandTotalPaymentAmount(grandTotalPaymentAmount);
        return this;
    }

    public void setGrandTotalPaymentAmount(Double grandTotalPaymentAmount) {
        this.grandTotalPaymentAmount = grandTotalPaymentAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentFileHeader paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentAdviceType getPaymentMode() {
        return this.paymentMode;
    }

    public PaymentFileHeader paymentMode(PaymentAdviceType paymentMode) {
        this.setPaymentMode(paymentMode);
        return this;
    }

    public void setPaymentMode(PaymentAdviceType paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getApproverRemarks() {
        return this.approverRemarks;
    }

    public PaymentFileHeader approverRemarks(String approverRemarks) {
        this.setApproverRemarks(approverRemarks);
        return this;
    }

    public void setApproverRemarks(String approverRemarks) {
        this.approverRemarks = approverRemarks;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PaymentFileHeader project(Project project) {
        this.setProject(project);
        return this;
    }

    public Set<PaymentFile> getPaymentFiles() {
        return this.paymentFiles;
    }

    public void setPaymentFiles(Set<PaymentFile> paymentFiles) {
        if (this.paymentFiles != null) {
            this.paymentFiles.forEach(i -> i.setPaymentFileHeader(null));
        }
        if (paymentFiles != null) {
            paymentFiles.forEach(i -> i.setPaymentFileHeader(this));
        }
        this.paymentFiles = paymentFiles;
    }

    public PaymentFileHeader paymentFiles(Set<PaymentFile> paymentFiles) {
        this.setPaymentFiles(paymentFiles);
        return this;
    }

    public PaymentFileHeader addPaymentFile(PaymentFile paymentFile) {
        this.paymentFiles.add(paymentFile);
        paymentFile.setPaymentFileHeader(this);
        return this;
    }

    public PaymentFileHeader removePaymentFile(PaymentFile paymentFile) {
        this.paymentFiles.remove(paymentFile);
        paymentFile.setPaymentFileHeader(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFileHeader)) {
            return false;
        }
        return id != null && id.equals(((PaymentFileHeader) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileHeader{" +
            "id=" + getId() +
            ", grandTotalPaymentAmount=" + getGrandTotalPaymentAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", approverRemarks='" + getApproverRemarks() + "'" +
            "}";
    }
}
