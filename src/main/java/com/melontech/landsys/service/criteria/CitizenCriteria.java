package com.melontech.landsys.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.Citizen} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.CitizenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /citizens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CitizenCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private StringFilter mobileNumber;

    private LocalDateFilter dob;

    private StringFilter accountNumber;

    private StringFilter fatherName;

    private StringFilter spouseName;

    private StringFilter successorName;

    private StringFilter aadhar;

    private StringFilter pan;

    private StringFilter accountNo;

    private LongFilter landId;

    private LongFilter bankBranchId;

    private LongFilter paymentAdviceId;

    private LongFilter khatedarId;

    private Boolean distinct;

    public CitizenCriteria() {}

    public CitizenCriteria(CitizenCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.mobileNumber = other.mobileNumber == null ? null : other.mobileNumber.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.spouseName = other.spouseName == null ? null : other.spouseName.copy();
        this.successorName = other.successorName == null ? null : other.successorName.copy();
        this.aadhar = other.aadhar == null ? null : other.aadhar.copy();
        this.pan = other.pan == null ? null : other.pan.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.landId = other.landId == null ? null : other.landId.copy();
        this.bankBranchId = other.bankBranchId == null ? null : other.bankBranchId.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.khatedarId = other.khatedarId == null ? null : other.khatedarId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CitizenCriteria copy() {
        return new CitizenCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getMobileNumber() {
        return mobileNumber;
    }

    public StringFilter mobileNumber() {
        if (mobileNumber == null) {
            mobileNumber = new StringFilter();
        }
        return mobileNumber;
    }

    public void setMobileNumber(StringFilter mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public LocalDateFilter getDob() {
        return dob;
    }

    public LocalDateFilter dob() {
        if (dob == null) {
            dob = new LocalDateFilter();
        }
        return dob;
    }

    public void setDob(LocalDateFilter dob) {
        this.dob = dob;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public StringFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new StringFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public StringFilter fatherName() {
        if (fatherName == null) {
            fatherName = new StringFilter();
        }
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getSpouseName() {
        return spouseName;
    }

    public StringFilter spouseName() {
        if (spouseName == null) {
            spouseName = new StringFilter();
        }
        return spouseName;
    }

    public void setSpouseName(StringFilter spouseName) {
        this.spouseName = spouseName;
    }

    public StringFilter getSuccessorName() {
        return successorName;
    }

    public StringFilter successorName() {
        if (successorName == null) {
            successorName = new StringFilter();
        }
        return successorName;
    }

    public void setSuccessorName(StringFilter successorName) {
        this.successorName = successorName;
    }

    public StringFilter getAadhar() {
        return aadhar;
    }

    public StringFilter aadhar() {
        if (aadhar == null) {
            aadhar = new StringFilter();
        }
        return aadhar;
    }

    public void setAadhar(StringFilter aadhar) {
        this.aadhar = aadhar;
    }

    public StringFilter getPan() {
        return pan;
    }

    public StringFilter pan() {
        if (pan == null) {
            pan = new StringFilter();
        }
        return pan;
    }

    public void setPan(StringFilter pan) {
        this.pan = pan;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public StringFilter accountNo() {
        if (accountNo == null) {
            accountNo = new StringFilter();
        }
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public LongFilter getLandId() {
        return landId;
    }

    public LongFilter landId() {
        if (landId == null) {
            landId = new LongFilter();
        }
        return landId;
    }

    public void setLandId(LongFilter landId) {
        this.landId = landId;
    }

    public LongFilter getBankBranchId() {
        return bankBranchId;
    }

    public LongFilter bankBranchId() {
        if (bankBranchId == null) {
            bankBranchId = new LongFilter();
        }
        return bankBranchId;
    }

    public void setBankBranchId(LongFilter bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public LongFilter getPaymentAdviceId() {
        return paymentAdviceId;
    }

    public LongFilter paymentAdviceId() {
        if (paymentAdviceId == null) {
            paymentAdviceId = new LongFilter();
        }
        return paymentAdviceId;
    }

    public void setPaymentAdviceId(LongFilter paymentAdviceId) {
        this.paymentAdviceId = paymentAdviceId;
    }

    public LongFilter getKhatedarId() {
        return khatedarId;
    }

    public LongFilter khatedarId() {
        if (khatedarId == null) {
            khatedarId = new LongFilter();
        }
        return khatedarId;
    }

    public void setKhatedarId(LongFilter khatedarId) {
        this.khatedarId = khatedarId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CitizenCriteria that = (CitizenCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(mobileNumber, that.mobileNumber) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(spouseName, that.spouseName) &&
            Objects.equals(successorName, that.successorName) &&
            Objects.equals(aadhar, that.aadhar) &&
            Objects.equals(pan, that.pan) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(landId, that.landId) &&
            Objects.equals(bankBranchId, that.bankBranchId) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(khatedarId, that.khatedarId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            address,
            mobileNumber,
            dob,
            accountNumber,
            fatherName,
            spouseName,
            successorName,
            aadhar,
            pan,
            accountNo,
            landId,
            bankBranchId,
            paymentAdviceId,
            khatedarId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitizenCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (mobileNumber != null ? "mobileNumber=" + mobileNumber + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
            (spouseName != null ? "spouseName=" + spouseName + ", " : "") +
            (successorName != null ? "successorName=" + successorName + ", " : "") +
            (aadhar != null ? "aadhar=" + aadhar + ", " : "") +
            (pan != null ? "pan=" + pan + ", " : "") +
            (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
            (landId != null ? "landId=" + landId + ", " : "") +
            (bankBranchId != null ? "bankBranchId=" + bankBranchId + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (khatedarId != null ? "khatedarId=" + khatedarId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
