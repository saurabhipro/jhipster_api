package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PaymentFile} entity.
 */
public class PaymentFileDTO implements Serializable {

    private Long id;

    @NotNull
    private Double paymentFileId;

    @NotNull
    private Double totalPaymentAmount;

    private LocalDate paymentFileDate;

    @NotNull
    private PaymentStatus paymentFileStatus;

    private String khatedarIfscCode;

    @NotNull
    private PaymentAdviceType paymentMode;

    private KhatedarDTO khatedar;

    private PaymentAdviceDTO paymentAdvice;

    private ProjectLandDTO projectLand;

    private SurveyDTO survey;

    private BankDTO bank;

    private BankBranchDTO bankBranch;

    private LandCompensationDTO landCompensation;

    private PaymentFileHeaderDTO paymentFileHeader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPaymentFileId() {
        return paymentFileId;
    }

    public void setPaymentFileId(Double paymentFileId) {
        this.paymentFileId = paymentFileId;
    }

    public Double getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public LocalDate getPaymentFileDate() {
        return paymentFileDate;
    }

    public void setPaymentFileDate(LocalDate paymentFileDate) {
        this.paymentFileDate = paymentFileDate;
    }

    public PaymentStatus getPaymentFileStatus() {
        return paymentFileStatus;
    }

    public void setPaymentFileStatus(PaymentStatus paymentFileStatus) {
        this.paymentFileStatus = paymentFileStatus;
    }

    public String getKhatedarIfscCode() {
        return khatedarIfscCode;
    }

    public void setKhatedarIfscCode(String khatedarIfscCode) {
        this.khatedarIfscCode = khatedarIfscCode;
    }

    public PaymentAdviceType getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentAdviceType paymentMode) {
        this.paymentMode = paymentMode;
    }

    public KhatedarDTO getKhatedar() {
        return khatedar;
    }

    public void setKhatedar(KhatedarDTO khatedar) {
        this.khatedar = khatedar;
    }

    public PaymentAdviceDTO getPaymentAdvice() {
        return paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdviceDTO paymentAdvice) {
        this.paymentAdvice = paymentAdvice;
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

    public BankDTO getBank() {
        return bank;
    }

    public void setBank(BankDTO bank) {
        this.bank = bank;
    }

    public BankBranchDTO getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(BankBranchDTO bankBranch) {
        this.bankBranch = bankBranch;
    }

    public LandCompensationDTO getLandCompensation() {
        return landCompensation;
    }

    public void setLandCompensation(LandCompensationDTO landCompensation) {
        this.landCompensation = landCompensation;
    }

    public PaymentFileHeaderDTO getPaymentFileHeader() {
        return paymentFileHeader;
    }

    public void setPaymentFileHeader(PaymentFileHeaderDTO paymentFileHeader) {
        this.paymentFileHeader = paymentFileHeader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFileDTO)) {
            return false;
        }

        PaymentFileDTO paymentFileDTO = (PaymentFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileDTO{" +
            "id=" + getId() +
            ", paymentFileId=" + getPaymentFileId() +
            ", totalPaymentAmount=" + getTotalPaymentAmount() +
            ", paymentFileDate='" + getPaymentFileDate() + "'" +
            ", paymentFileStatus='" + getPaymentFileStatus() + "'" +
            ", khatedarIfscCode='" + getKhatedarIfscCode() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", khatedar=" + getKhatedar() +
            ", paymentAdvice=" + getPaymentAdvice() +
            ", projectLand=" + getProjectLand() +
            ", survey=" + getSurvey() +
            ", bank=" + getBank() +
            ", bankBranch=" + getBankBranch() +
            ", landCompensation=" + getLandCompensation() +
            ", paymentFileHeader=" + getPaymentFileHeader() +
            "}";
    }
}
