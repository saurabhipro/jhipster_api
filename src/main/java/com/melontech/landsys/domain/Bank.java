package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Bank.
 */
@Entity
@Table(name = "landsys_bank")
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "bank")
    @JsonIgnoreProperties(value = { "bank", "citizens" }, allowSetters = true)
    private Set<BankBranch> bankBranches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bank id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Bank name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Bank code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<BankBranch> getBankBranches() {
        return this.bankBranches;
    }

    public void setBankBranches(Set<BankBranch> bankBranches) {
        if (this.bankBranches != null) {
            this.bankBranches.forEach(i -> i.setBank(null));
        }
        if (bankBranches != null) {
            bankBranches.forEach(i -> i.setBank(this));
        }
        this.bankBranches = bankBranches;
    }

    public Bank bankBranches(Set<BankBranch> bankBranches) {
        this.setBankBranches(bankBranches);
        return this;
    }

    public Bank addBankBranch(BankBranch bankBranch) {
        this.bankBranches.add(bankBranch);
        bankBranch.setBank(this);
        return this;
    }

    public Bank removeBankBranch(BankBranch bankBranch) {
        this.bankBranches.remove(bankBranch);
        bankBranch.setBank(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bank)) {
            return false;
        }
        return id != null && id.equals(((Bank) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bank{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
