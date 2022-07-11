package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PaymentFile.
 */
@Entity
@Table(name = "landsys_payment_file")
public class PaymentFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "payment_file_id", nullable = false)
    private Double paymentFileId;

    @NotNull
    @Column(name = "total_payment_amount", nullable = false)
    private Double totalPaymentAmount;

    @Column(name = "payment_file_date")
    private LocalDate paymentFileDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @OneToMany(mappedBy = "paymentFile")
    @JsonIgnoreProperties(value = { "projectLand", "landCompensation", "paymentFile", "paymentFileRecon" }, allowSetters = true)
    private Set<PaymentAdvice> paymentAdvices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPaymentFileId() {
        return this.paymentFileId;
    }

    public PaymentFile paymentFileId(Double paymentFileId) {
        this.setPaymentFileId(paymentFileId);
        return this;
    }

    public void setPaymentFileId(Double paymentFileId) {
        this.paymentFileId = paymentFileId;
    }

    public Double getTotalPaymentAmount() {
        return this.totalPaymentAmount;
    }

    public PaymentFile totalPaymentAmount(Double totalPaymentAmount) {
        this.setTotalPaymentAmount(totalPaymentAmount);
        return this;
    }

    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public LocalDate getPaymentFileDate() {
        return this.paymentFileDate;
    }

    public PaymentFile paymentFileDate(LocalDate paymentFileDate) {
        this.setPaymentFileDate(paymentFileDate);
        return this;
    }

    public void setPaymentFileDate(LocalDate paymentFileDate) {
        this.paymentFileDate = paymentFileDate;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentFile paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBankName() {
        return this.bankName;
    }

    public PaymentFile bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public PaymentFile ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Set<PaymentAdvice> getPaymentAdvices() {
        return this.paymentAdvices;
    }

    public void setPaymentAdvices(Set<PaymentAdvice> paymentAdvices) {
        if (this.paymentAdvices != null) {
            this.paymentAdvices.forEach(i -> i.setPaymentFile(null));
        }
        if (paymentAdvices != null) {
            paymentAdvices.forEach(i -> i.setPaymentFile(this));
        }
        this.paymentAdvices = paymentAdvices;
    }

    public PaymentFile paymentAdvices(Set<PaymentAdvice> paymentAdvices) {
        this.setPaymentAdvices(paymentAdvices);
        return this;
    }

    public PaymentFile addPaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvices.add(paymentAdvice);
        paymentAdvice.setPaymentFile(this);
        return this;
    }

    public PaymentFile removePaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvices.remove(paymentAdvice);
        paymentAdvice.setPaymentFile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFile)) {
            return false;
        }
        return id != null && id.equals(((PaymentFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFile{" +
            "id=" + getId() +
            ", paymentFileId=" + getPaymentFileId() +
            ", totalPaymentAmount=" + getTotalPaymentAmount() +
            ", paymentFileDate='" + getPaymentFileDate() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            "}";
    }
}
