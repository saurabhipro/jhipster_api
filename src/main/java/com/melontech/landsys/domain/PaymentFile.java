package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentAdviceType paymentMode;

    @JsonIgnoreProperties(value = { "projectLand", "citizen", "paymentFile", "paymentAdvice" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Khatedar khatedar;

    @JsonIgnoreProperties(
        value = {
            "khatedars",
            "landCompensation",
            "projectLand",
            "survey",
            "citizen",
            "paymentFileRecon",
            "paymentFile",
            "land",
            "paymentAdviceDetails",
        },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private PaymentAdvice paymentAdvice;

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
    @JsonIgnoreProperties(value = { "projectLand", "landCompensation", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    private Survey survey;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bankBranches", "paymentFiles" }, allowSetters = true)
    private Bank bank;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bank", "citizens", "paymentFiles" }, allowSetters = true)
    private BankBranch bankBranch;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectLand", "survey", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    private LandCompensation landCompensation;

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

    public PaymentAdviceType getPaymentMode() {
        return this.paymentMode;
    }

    public PaymentFile paymentMode(PaymentAdviceType paymentMode) {
        this.setPaymentMode(paymentMode);
        return this;
    }

    public void setPaymentMode(PaymentAdviceType paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Khatedar getKhatedar() {
        return this.khatedar;
    }

    public void setKhatedar(Khatedar khatedar) {
        this.khatedar = khatedar;
    }

    public PaymentFile khatedar(Khatedar khatedar) {
        this.setKhatedar(khatedar);
        return this;
    }

    public PaymentAdvice getPaymentAdvice() {
        return this.paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvice = paymentAdvice;
    }

    public PaymentFile paymentAdvice(PaymentAdvice paymentAdvice) {
        this.setPaymentAdvice(paymentAdvice);
        return this;
    }

    public ProjectLand getProjectLand() {
        return this.projectLand;
    }

    public void setProjectLand(ProjectLand projectLand) {
        this.projectLand = projectLand;
    }

    public PaymentFile projectLand(ProjectLand projectLand) {
        this.setProjectLand(projectLand);
        return this;
    }

    public Survey getSurvey() {
        return this.survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public PaymentFile survey(Survey survey) {
        this.setSurvey(survey);
        return this;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public PaymentFile bank(Bank bank) {
        this.setBank(bank);
        return this;
    }

    public BankBranch getBankBranch() {
        return this.bankBranch;
    }

    public void setBankBranch(BankBranch bankBranch) {
        this.bankBranch = bankBranch;
    }

    public PaymentFile bankBranch(BankBranch bankBranch) {
        this.setBankBranch(bankBranch);
        return this;
    }

    public LandCompensation getLandCompensation() {
        return this.landCompensation;
    }

    public void setLandCompensation(LandCompensation landCompensation) {
        this.landCompensation = landCompensation;
    }

    public PaymentFile landCompensation(LandCompensation landCompensation) {
        this.setLandCompensation(landCompensation);
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
            ", paymentMode='" + getPaymentMode() + "'" +
            "}";
    }
}
