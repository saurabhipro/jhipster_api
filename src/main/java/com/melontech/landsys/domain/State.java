package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A State.
 */
@Entity
@Table(name = "landsys_state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "state")
    @JsonIgnoreProperties(value = { "subDistricts", "state" }, allowSetters = true)
    private Set<District> districts = new HashSet<>();

    @JsonIgnoreProperties(value = { "state", "projectLand", "village", "landType", "unit" }, allowSetters = true)
    @OneToOne(mappedBy = "state")
    private Land land;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public State id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public State name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<District> getDistricts() {
        return this.districts;
    }

    public void setDistricts(Set<District> districts) {
        if (this.districts != null) {
            this.districts.forEach(i -> i.setState(null));
        }
        if (districts != null) {
            districts.forEach(i -> i.setState(this));
        }
        this.districts = districts;
    }

    public State districts(Set<District> districts) {
        this.setDistricts(districts);
        return this;
    }

    public State addDistrict(District district) {
        this.districts.add(district);
        district.setState(this);
        return this;
    }

    public State removeDistrict(District district) {
        this.districts.remove(district);
        district.setState(null);
        return this;
    }

    public Land getLand() {
        return this.land;
    }

    public void setLand(Land land) {
        if (this.land != null) {
            this.land.setState(null);
        }
        if (land != null) {
            land.setState(this);
        }
        this.land = land;
    }

    public State land(Land land) {
        this.setLand(land);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        return id != null && id.equals(((State) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
