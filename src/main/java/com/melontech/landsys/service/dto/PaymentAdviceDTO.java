package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PaymentAdvice} entity.
 */
public class PaymentAdviceDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountHolderName;

    @NotNull
    private String accountHolderBankName;

    @NotNull
    private Double paymentAmount;

    @NotNull
    private String bankName;

    @NotNull
    private String accountNumber;

    @NotNull
    private String ifscCode;

    private String checkNumber;

    private String micrCode;

    private PaymentAdviceType paymentAdviceType;

    private String referenceNumber;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private HissaType hissaType;

    private LandCompensationDTO landCompensation;

    private ProjectLandDTO projectLand;

    private SurveyDTO survey;

    private CitizenDTO citizen;

    private PaymentFileDTO paymentFile;

    private LandDTO land;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderBankName() {
        return accountHolderBankName;
    }

    public void setAccountHolderBankName(String accountHolderBankName) {
        this.accountHolderBankName = accountHolderBankName;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public PaymentAdviceType getPaymentAdviceType() {
        return paymentAdviceType;
    }

    public void setPaymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.paymentAdviceType = paymentAdviceType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public LandCompensationDTO getLandCompensation() {
        return landCompensation;
    }

    public void setLandCompensation(LandCompensationDTO landCompensation) {
        this.landCompensation = landCompensation;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
    }

    public SurveyDTO getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyDTO survey) {
        this.survey = survey;
    }

    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
    }

    public PaymentFileDTO getPaymentFile() {
        return paymentFile;
    }

    public void setPaymentFile(PaymentFileDTO paymentFile) {
        this.paymentFile = paymentFile;
    }

    public LandDTO getLand() {
        return land;
    }

    public void setLand(LandDTO land) {
        this.land = land;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAdviceDTO)) {
            return false;
        }

        PaymentAdviceDTO paymentAdviceDTO = (PaymentAdviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentAdviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAdviceDTO{" +
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
            ", landCompensation=" + getLandCompensation() +
            ", projectLand=" + getProjectLand() +
            ", survey=" + getSurvey() +
            ", citizen=" + getCitizen() +
            ", paymentFile=" + getPaymentFile() +
            ", land=" + getLand() +
            "}";
    }
}
