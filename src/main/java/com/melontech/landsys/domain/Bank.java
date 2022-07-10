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
    @JsonIgnoreProperties(value = { "citizens", "bank" }, allowSetters = true)
    private Set<BankBranch> bankNames = new HashSet<>();

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

    public Set<BankBranch> getBankNames() {
        return this.bankNames;
    }

    public void setBankNames(Set<BankBranch> bankBranches) {
        if (this.bankNames != null) {
            this.bankNames.forEach(i -> i.setBank(null));
        }
        if (bankBranches != null) {
            bankBranches.forEach(i -> i.setBank(this));
        }
        this.bankNames = bankBranches;
    }

    public Bank bankNames(Set<BankBranch> bankBranches) {
        this.setBankNames(bankBranches);
        return this;
    }

    public Bank addBankName(BankBranch bankBranch) {
        this.bankNames.add(bankBranch);
        bankBranch.setBank(this);
        return this;
    }

    public Bank removeBankName(BankBranch bankBranch) {
        this.bankNames.remove(bankBranch);
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
