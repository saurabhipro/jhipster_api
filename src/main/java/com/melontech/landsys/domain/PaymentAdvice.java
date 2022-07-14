package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PaymentAdvice.
 */
@Entity
@Table(name = "landsys_payment_advice")
public class PaymentAdvice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    @NotNull
    @Column(name = "account_holder_bank_name", nullable = false)
    private String accountHolderBankName;

    @NotNull
    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    @NotNull
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "check_number")
    private String checkNumber;

    @Column(name = "micr_code")
    private String micrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_advice_type")
    private PaymentAdviceType paymentAdviceType;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hissa_type", nullable = false)
    private HissaType hissaType;

    @OneToMany(mappedBy = "paymentAdvice")
    @JsonIgnoreProperties(value = { "projectLand", "citizen", "paymentFile", "paymentAdvice" }, allowSetters = true)
    private Set<Khatedar> khatedars = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectLand", "survey", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    private LandCompensation landCompensation;

    @ManyToOne(optional = false)
    @NotNull
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
    private ProjectLand projectLand;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectLand", "landCompensation", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    private Survey survey;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lands", "bankBranch", "projectLands", "paymentAdvices", "khatedars" }, allowSetters = true)
    private Citizen citizen;

    @JsonIgnoreProperties(value = { "paymentAdvice" }, allowSetters = true)
    @OneToOne(mappedBy = "paymentAdvice")
    private PaymentFileRecon paymentFileRecon;

    @JsonIgnoreProperties(
        value = { "khatedar", "paymentAdvice", "projectLand", "survey", "bank", "bankBranch", "landCompensation" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "paymentAdvice")
    private PaymentFile paymentFile;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "paymentAdvices", "village", "unit", "landType", "state", "citizen", "project", "projectLands" },
        allowSetters = true
    )
    private Land land;

    @OneToMany(mappedBy = "paymentAdvice")
    @JsonIgnoreProperties(value = { "paymentAdvice", "projectLand" }, allowSetters = true)
    private Set<PaymentAdviceDetails> paymentAdviceDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentAdvice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    public PaymentAdvice accountHolderName(String accountHolderName) {
        this.setAccountHolderName(accountHolderName);
        return this;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderBankName() {
        return this.accountHolderBankName;
    }

    public PaymentAdvice accountHolderBankName(String accountHolderBankName) {
        this.setAccountHolderBankName(accountHolderBankName);
        return this;
    }

    public void setAccountHolderBankName(String accountHolderBankName) {
        this.accountHolderBankName = accountHolderBankName;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public PaymentAdvice paymentAmount(Double paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public PaymentAdvice bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public PaymentAdvice accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public PaymentAdvice ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCheckNumber() {
        return this.checkNumber;
    }

    public PaymentAdvice checkNumber(String checkNumber) {
        this.setCheckNumber(checkNumber);
        return this;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getMicrCode() {
        return this.micrCode;
    }

    public PaymentAdvice micrCode(String micrCode) {
        this.setMicrCode(micrCode);
        return this;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public PaymentAdviceType getPaymentAdviceType() {
        return this.paymentAdviceType;
    }

    public PaymentAdvice paymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.setPaymentAdviceType(paymentAdviceType);
        return this;
    }

    public void setPaymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.paymentAdviceType = paymentAdviceType;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public PaymentAdvice referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentAdvice paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public HissaType getHissaType() {
        return this.hissaType;
    }

    public PaymentAdvice hissaType(HissaType hissaType) {
        this.setHissaType(hissaType);
        return this;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public Set<Khatedar> getKhatedars() {
        return this.khatedars;
    }

    public void setKhatedars(Set<Khatedar> khatedars) {
        if (this.khatedars != null) {
            this.khatedars.forEach(i -> i.setPaymentAdvice(null));
        }
        if (khatedars != null) {
            khatedars.forEach(i -> i.setPaymentAdvice(this));
        }
        this.khatedars = khatedars;
    }

    public PaymentAdvice khatedars(Set<Khatedar> khatedars) {
        this.setKhatedars(khatedars);
        return this;
    }

    public PaymentAdvice addKhatedar(Khatedar khatedar) {
        this.khatedars.add(khatedar);
        khatedar.setPaymentAdvice(this);
        return this;
    }

    public PaymentAdvice removeKhatedar(Khatedar khatedar) {
        this.khatedars.remove(khatedar);
        khatedar.setPaymentAdvice(null);
        return this;
    }

    public LandCompensation getLandCompensation() {
        return this.landCompensation;
    }

    public void setLandCompensation(LandCompensation landCompensation) {
        this.landCompensation = landCompensation;
    }

    public PaymentAdvice landCompensation(LandCompensation landCompensation) {
        this.setLandCompensation(landCompensation);
        return this;
    }

    public ProjectLand getProjectLand() {
        return this.projectLand;
    }

    public void setProjectLand(ProjectLand projectLand) {
        this.projectLand = projectLand;
    }

    public PaymentAdvice projectLand(ProjectLand projectLand) {
        this.setProjectLand(projectLand);
        return this;
    }

    public Survey getSurvey() {
        return this.survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public PaymentAdvice survey(Survey survey) {
        this.setSurvey(survey);
        return this;
    }

    public Citizen getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public PaymentAdvice citizen(Citizen citizen) {
        this.setCitizen(citizen);
        return this;
    }

    public PaymentFileRecon getPaymentFileRecon() {
        return this.paymentFileRecon;
    }

    public void setPaymentFileRecon(PaymentFileRecon paymentFileRecon) {
        if (this.paymentFileRecon != null) {
            this.paymentFileRecon.setPaymentAdvice(null);
        }
        if (paymentFileRecon != null) {
            paymentFileRecon.setPaymentAdvice(this);
        }
        this.paymentFileRecon = paymentFileRecon;
    }

    public PaymentAdvice paymentFileRecon(PaymentFileRecon paymentFileRecon) {
        this.setPaymentFileRecon(paymentFileRecon);
        return this;
    }

    public PaymentFile getPaymentFile() {
        return this.paymentFile;
    }

    public void setPaymentFile(PaymentFile paymentFile) {
        if (this.paymentFile != null) {
            this.paymentFile.setPaymentAdvice(null);
        }
        if (paymentFile != null) {
            paymentFile.setPaymentAdvice(this);
        }
        this.paymentFile = paymentFile;
    }

    public PaymentAdvice paymentFile(PaymentFile paymentFile) {
        this.setPaymentFile(paymentFile);
        return this;
    }

    public Land getLand() {
        return this.land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public PaymentAdvice land(Land land) {
        this.setLand(land);
        return this;
    }

    public Set<PaymentAdviceDetails> getPaymentAdviceDetails() {
        return this.paymentAdviceDetails;
    }

    public void setPaymentAdviceDetails(Set<PaymentAdviceDetails> paymentAdviceDetails) {
        if (this.paymentAdviceDetails != null) {
            this.paymentAdviceDetails.forEach(i -> i.setPaymentAdvice(null));
        }
        if (paymentAdviceDetails != null) {
            paymentAdviceDetails.forEach(i -> i.setPaymentAdvice(this));
        }
        this.paymentAdviceDetails = paymentAdviceDetails;
    }

    public PaymentAdvice paymentAdviceDetails(Set<PaymentAdviceDetails> paymentAdviceDetails) {
        this.setPaymentAdviceDetails(paymentAdviceDetails);
        return this;
    }

    public PaymentAdvice addPaymentAdviceDetails(PaymentAdviceDetails paymentAdviceDetails) {
        this.paymentAdviceDetails.add(paymentAdviceDetails);
        paymentAdviceDetails.setPaymentAdvice(this);
        return this;
    }

    public PaymentAdvice removePaymentAdviceDetails(PaymentAdviceDetails paymentAdviceDetails) {
        this.paymentAdviceDetails.remove(paymentAdviceDetails);
        paymentAdviceDetails.setPaymentAdvice(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAdvice)) {
            return false;
        }
        return id != null && id.equals(((PaymentAdvice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAdvice{" +
            "id=" + getId() +
            ", accountHolderName='" + getAccountHolderName() + "'" +
            ", accountHolderBankName='" + getAccountHolderBankName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", bankName='" + getBankName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", micrCode='" + getMicrCode() + "'" +
            ", paymentAdviceType='" + getPaymentAdviceType() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            "}";
    }
}
