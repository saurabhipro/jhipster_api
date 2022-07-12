package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Citizen} entity.
 */
public class CitizenDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] photo;

    private String photoContentType;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private String mobileNo;

    private LocalDate dob;

    private String accountNumber;

    @NotNull
    private String fatherName;

    private String spouseName;

    private String successorName;

    @NotNull
    private String aadhar;

    private String pan;

    @Lob
    private byte[] aadharImage;

    private String aadharImageContentType;

    @Lob
    private byte[] panImage;

    private String panImageContentType;

    private String accountNo;

    @Lob
    private byte[] accNoImage;

    private String accNoImageContentType;
    private BankBranchDTO bankBranch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSuccessorName() {
        return successorName;
    }

    public void setSuccessorName(String successorName) {
        this.successorName = successorName;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public byte[] getAadharImage() {
        return aadharImage;
    }

    public void setAadharImage(byte[] aadharImage) {
        this.aadharImage = aadharImage;
    }

    public String getAadharImageContentType() {
        return aadharImageContentType;
    }

    public void setAadharImageContentType(String aadharImageContentType) {
        this.aadharImageContentType = aadharImageContentType;
    }

    public byte[] getPanImage() {
        return panImage;
    }

    public void setPanImage(byte[] panImage) {
        this.panImage = panImage;
    }

    public String getPanImageContentType() {
        return panImageContentType;
    }

    public void setPanImageContentType(String panImageContentType) {
        this.panImageContentType = panImageContentType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public byte[] getAccNoImage() {
        return accNoImage;
    }

    public void setAccNoImage(byte[] accNoImage) {
        this.accNoImage = accNoImage;
    }

    public String getAccNoImageContentType() {
        return accNoImageContentType;
    }

    public void setAccNoImageContentType(String accNoImageContentType) {
        this.accNoImageContentType = accNoImageContentType;
    }

    public BankBranchDTO getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(BankBranchDTO bankBranch) {
        this.bankBranch = bankBranch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitizenDTO)) {
            return false;
        }

        CitizenDTO citizenDTO = (CitizenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citizenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitizenDTO{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", dob='" + getDob() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", spouseName='" + getSpouseName() + "'" +
            ", successorName='" + getSuccessorName() + "'" +
            ", aadhar='" + getAadhar() + "'" +
            ", pan='" + getPan() + "'" +
            ", aadharImage='" + getAadharImage() + "'" +
            ", panImage='" + getPanImage() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", accNoImage='" + getAccNoImage() + "'" +
            ", bankBranch=" + getBankBranch() +
            "}";
    }
}
