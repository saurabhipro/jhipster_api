package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PaymentFileRecon.
 */
@Entity
@Table(name = "landsys_payment_file_recon")
public class PaymentFileRecon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "primary_holder_name", nullable = false)
    private String primaryHolderName;

    @NotNull
    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotNull
    @Column(name = "utr_number", nullable = false)
    private String utrNumber;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @JsonIgnoreProperties(
        value = {
            "landCompensation", "projectLand", "survey", "citizen", "paymentFile", "paymentFileRecon", "land", "paymentAdviceDetails",
        },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private PaymentAdvice paymentAdvice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentFileRecon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryHolderName() {
        return this.primaryHolderName;
    }

    public PaymentFileRecon primaryHolderName(String primaryHolderName) {
        this.setPrimaryHolderName(primaryHolderName);
        return this;
    }

    public void setPrimaryHolderName(String primaryHolderName) {
        this.primaryHolderName = primaryHolderName;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public PaymentFileRecon paymentAmount(Double paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return this.paymentDate;
    }

    public PaymentFileRecon paymentDate(LocalDate paymentDate) {
        this.setPaymentDate(paymentDate);
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getUtrNumber() {
        return this.utrNumber;
    }

    public PaymentFileRecon utrNumber(String utrNumber) {
        this.setUtrNumber(utrNumber);
        return this;
    }

    public void setUtrNumber(String utrNumber) {
        this.utrNumber = utrNumber;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public PaymentFileRecon referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentFileRecon paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentAdvice getPaymentAdvice() {
        return this.paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvice = paymentAdvice;
    }

    public PaymentFileRecon paymentAdvice(PaymentAdvice paymentAdvice) {
        this.setPaymentAdvice(paymentAdvice);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFileRecon)) {
            return false;
        }
        return id != null && id.equals(((PaymentFileRecon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileRecon{" +
            "id=" + getId() +
            ", primaryHolderName='" + getPrimaryHolderName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", utrNumber='" + getUtrNumber() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
