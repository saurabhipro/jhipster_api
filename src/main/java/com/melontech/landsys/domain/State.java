package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "subDistricts", "states" }, allowSetters = true)
    private District district;

    @JsonIgnoreProperties(value = { "state", "village", "unit", "landType", "projectLands" }, allowSetters = true)
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

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public State district(District district) {
        this.setDistrict(district);
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
