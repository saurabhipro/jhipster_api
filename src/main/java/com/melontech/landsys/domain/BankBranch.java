package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BankBranch.
 */
@Entity
@Table(name = "landsys_bank_branch")
public class BankBranch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ifsc", nullable = false, unique = true)
    private String ifsc;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bankNames" }, allowSetters = true)
    private Bank bank;

    @OneToMany(mappedBy = "bankBranch")
    @JsonIgnoreProperties(value = { "bankBranch", "khatedars" }, allowSetters = true)
    private Set<Citizen> citizens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankBranch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BankBranch name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfsc() {
        return this.ifsc;
    }

    public BankBranch ifsc(String ifsc) {
        this.setIfsc(ifsc);
        return this;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAddress() {
        return this.address;
    }

    public BankBranch address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public BankBranch bank(Bank bank) {
        this.setBank(bank);
        return this;
    }

    public Set<Citizen> getCitizens() {
        return this.citizens;
    }

    public void setCitizens(Set<Citizen> citizens) {
        if (this.citizens != null) {
            this.citizens.forEach(i -> i.setBankBranch(null));
        }
        if (citizens != null) {
            citizens.forEach(i -> i.setBankBranch(this));
        }
        this.citizens = citizens;
    }

    public BankBranch citizens(Set<Citizen> citizens) {
        this.setCitizens(citizens);
        return this;
    }

    public BankBranch addCitizen(Citizen citizen) {
        this.citizens.add(citizen);
        citizen.setBankBranch(this);
        return this;
    }

    public BankBranch removeCitizen(Citizen citizen) {
        this.citizens.remove(citizen);
        citizen.setBankBranch(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankBranch)) {
            return false;
        }
        return id != null && id.equals(((BankBranch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankBranch{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ifsc='" + getIfsc() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
