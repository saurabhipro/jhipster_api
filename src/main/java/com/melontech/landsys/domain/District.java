package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A District.
 */
@Entity
@Table(name = "landsys_district")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "district")
    @JsonIgnoreProperties(value = { "district", "villages" }, allowSetters = true)
    private Set<SubDistrict> subDistricts = new HashSet<>();

    @OneToMany(mappedBy = "district")
    @JsonIgnoreProperties(value = { "district", "land" }, allowSetters = true)
    private Set<State> states = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public District id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public District name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubDistrict> getSubDistricts() {
        return this.subDistricts;
    }

    public void setSubDistricts(Set<SubDistrict> subDistricts) {
        if (this.subDistricts != null) {
            this.subDistricts.forEach(i -> i.setDistrict(null));
        }
        if (subDistricts != null) {
            subDistricts.forEach(i -> i.setDistrict(this));
        }
        this.subDistricts = subDistricts;
    }

    public District subDistricts(Set<SubDistrict> subDistricts) {
        this.setSubDistricts(subDistricts);
        return this;
    }

    public District addSubDistrict(SubDistrict subDistrict) {
        this.subDistricts.add(subDistrict);
        subDistrict.setDistrict(this);
        return this;
    }

    public District removeSubDistrict(SubDistrict subDistrict) {
        this.subDistricts.remove(subDistrict);
        subDistrict.setDistrict(null);
        return this;
    }

    public Set<State> getStates() {
        return this.states;
    }

    public void setStates(Set<State> states) {
        if (this.states != null) {
            this.states.forEach(i -> i.setDistrict(null));
        }
        if (states != null) {
            states.forEach(i -> i.setDistrict(this));
        }
        this.states = states;
    }

    public District states(Set<State> states) {
        this.setStates(states);
        return this;
    }

    public District addState(State state) {
        this.states.add(state);
        state.setDistrict(this);
        return this;
    }

    public District removeState(State state) {
        this.states.remove(state);
        state.setDistrict(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
