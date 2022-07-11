package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Citizen.
 */
@Entity
@Table(name = "landsys_citizen")
public class Citizen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "successor_name")
    private String successorName;

    @NotNull
    @Column(name = "aadhar", nullable = false, unique = true)
    private String aadhar;

    @Column(name = "pan", unique = true)
    private String pan;

    @Lob
    @Column(name = "aadhar_image")
    private byte[] aadharImage;

    @Column(name = "aadhar_image_content_type")
    private String aadharImageContentType;

    @Lob
    @Column(name = "pan_image")
    private byte[] panImage;

    @Column(name = "pan_image_content_type")
    private String panImageContentType;

    @Column(name = "account_no", unique = true)
    private String accountNo;

    @Lob
    @Column(name = "acc_no_image")
    private byte[] accNoImage;

    @Column(name = "acc_no_image_content_type")
    private String accNoImageContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bank", "citizens" }, allowSetters = true)
    private BankBranch bankBranch;

    @OneToMany(mappedBy = "citizen")
    @JsonIgnoreProperties(value = { "citizen", "projectLand", "noticeStatusInfo", "survey", "landCompensations" }, allowSetters = true)
    private Set<Khatedar> khatedars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Citizen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Citizen photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Citizen photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getName() {
        return this.name;
    }

    public Citizen name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Citizen address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Citizen accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public Citizen fatherName(String fatherName) {
        this.setFatherName(fatherName);
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getSpouseName() {
        return this.spouseName;
    }

    public Citizen spouseName(String spouseName) {
        this.setSpouseName(spouseName);
        return this;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSuccessorName() {
        return this.successorName;
    }

    public Citizen successorName(String successorName) {
        this.setSuccessorName(successorName);
        return this;
    }

    public void setSuccessorName(String successorName) {
        this.successorName = successorName;
    }

    public String getAadhar() {
        return this.aadhar;
    }

    public Citizen aadhar(String aadhar) {
        this.setAadhar(aadhar);
        return this;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return this.pan;
    }

    public Citizen pan(String pan) {
        this.setPan(pan);
        return this;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public byte[] getAadharImage() {
        return this.aadharImage;
    }

    public Citizen aadharImage(byte[] aadharImage) {
        this.setAadharImage(aadharImage);
        return this;
    }

    public void setAadharImage(byte[] aadharImage) {
        this.aadharImage = aadharImage;
    }

    public String getAadharImageContentType() {
        return this.aadharImageContentType;
    }

    public Citizen aadharImageContentType(String aadharImageContentType) {
        this.aadharImageContentType = aadharImageContentType;
        return this;
    }

    public void setAadharImageContentType(String aadharImageContentType) {
        this.aadharImageContentType = aadharImageContentType;
    }

    public byte[] getPanImage() {
        return this.panImage;
    }

    public Citizen panImage(byte[] panImage) {
        this.setPanImage(panImage);
        return this;
    }

    public void setPanImage(byte[] panImage) {
        this.panImage = panImage;
    }

    public String getPanImageContentType() {
        return this.panImageContentType;
    }

    public Citizen panImageContentType(String panImageContentType) {
        this.panImageContentType = panImageContentType;
        return this;
    }

    public void setPanImageContentType(String panImageContentType) {
        this.panImageContentType = panImageContentType;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public Citizen accountNo(String accountNo) {
        this.setAccountNo(accountNo);
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public byte[] getAccNoImage() {
        return this.accNoImage;
    }

    public Citizen accNoImage(byte[] accNoImage) {
        this.setAccNoImage(accNoImage);
        return this;
    }

    public void setAccNoImage(byte[] accNoImage) {
        this.accNoImage = accNoImage;
    }

    public String getAccNoImageContentType() {
        return this.accNoImageContentType;
    }

    public Citizen accNoImageContentType(String accNoImageContentType) {
        this.accNoImageContentType = accNoImageContentType;
        return this;
    }

    public void setAccNoImageContentType(String accNoImageContentType) {
        this.accNoImageContentType = accNoImageContentType;
    }

    public BankBranch getBankBranch() {
        return this.bankBranch;
    }

    public void setBankBranch(BankBranch bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Citizen bankBranch(BankBranch bankBranch) {
        this.setBankBranch(bankBranch);
        return this;
    }

    public Set<Khatedar> getKhatedars() {
        return this.khatedars;
    }

    public void setKhatedars(Set<Khatedar> khatedars) {
        if (this.khatedars != null) {
            this.khatedars.forEach(i -> i.setCitizen(null));
        }
        if (khatedars != null) {
            khatedars.forEach(i -> i.setCitizen(this));
        }
        this.khatedars = khatedars;
    }

    public Citizen khatedars(Set<Khatedar> khatedars) {
        this.setKhatedars(khatedars);
        return this;
    }

    public Citizen addKhatedar(Khatedar khatedar) {
        this.khatedars.add(khatedar);
        khatedar.setCitizen(this);
        return this;
    }

    public Citizen removeKhatedar(Khatedar khatedar) {
        this.khatedars.remove(khatedar);
        khatedar.setCitizen(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Citizen)) {
            return false;
        }
        return id != null && id.equals(((Citizen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Citizen{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", spouseName='" + getSpouseName() + "'" +
            ", successorName='" + getSuccessorName() + "'" +
            ", aadhar='" + getAadhar() + "'" +
            ", pan='" + getPan() + "'" +
            ", aadharImage='" + getAadharImage() + "'" +
            ", aadharImageContentType='" + getAadharImageContentType() + "'" +
            ", panImage='" + getPanImage() + "'" +
            ", panImageContentType='" + getPanImageContentType() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", accNoImage='" + getAccNoImage() + "'" +
            ", accNoImageContentType='" + getAccNoImageContentType() + "'" +
            "}";
    }
}
