package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.BankBranch} entity.
 */
public class BankBranchDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String ifsc;

    @NotNull
    private String address;

    private BankDTO bank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BankDTO getBank() {
        return bank;
    }

    public void setBank(BankDTO bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankBranchDTO)) {
            return false;
        }

        BankBranchDTO bankBranchDTO = (BankBranchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankBranchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankBranchDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ifsc='" + getIfsc() + "'" +
            ", address='" + getAddress() + "'" +
            ", bank=" + getBank() +
            "}";
    }
}
