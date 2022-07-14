package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Unit.
 */
@Entity
@Table(name = "landsys_unit")
public class Unit implements Serializable {

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
    @Column(name = "conversion_factor", nullable = false)
    private Double conversionFactor;

    @OneToMany(mappedBy = "unit")
    @JsonIgnoreProperties(value = { "village", "unit", "landType", "state", "projectLands" }, allowSetters = true)
    private Set<Land> lands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Unit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Unit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConversionFactor() {
        return this.conversionFactor;
    }

    public Unit conversionFactor(Double conversionFactor) {
        this.setConversionFactor(conversionFactor);
        return this;
    }

    public void setConversionFactor(Double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Set<Land> getLands() {
        return this.lands;
    }

    public void setLands(Set<Land> lands) {
        if (this.lands != null) {
            this.lands.forEach(i -> i.setUnit(null));
        }
        if (lands != null) {
            lands.forEach(i -> i.setUnit(this));
        }
        this.lands = lands;
    }

    public Unit lands(Set<Land> lands) {
        this.setLands(lands);
        return this;
    }

    public Unit addLand(Land land) {
        this.lands.add(land);
        land.setUnit(this);
        return this;
    }

    public Unit removeLand(Land land) {
        this.lands.remove(land);
        land.setUnit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", conversionFactor=" + getConversionFactor() +
            "}";
    }
}
