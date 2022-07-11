package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Village.
 */
@Entity
@Table(name = "landsys_village")
public class Village implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "district", "villages" }, allowSetters = true)
    private SubDistrict subDistrict;

    @OneToMany(mappedBy = "village")
    @JsonIgnoreProperties(value = { "state", "village", "unit", "landType", "projectLands" }, allowSetters = true)
    private Set<Land> lands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Village id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Village name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubDistrict getSubDistrict() {
        return this.subDistrict;
    }

    public void setSubDistrict(SubDistrict subDistrict) {
        this.subDistrict = subDistrict;
    }

    public Village subDistrict(SubDistrict subDistrict) {
        this.setSubDistrict(subDistrict);
        return this;
    }

    public Set<Land> getLands() {
        return this.lands;
    }

    public void setLands(Set<Land> lands) {
        if (this.lands != null) {
            this.lands.forEach(i -> i.setVillage(null));
        }
        if (lands != null) {
            lands.forEach(i -> i.setVillage(this));
        }
        this.lands = lands;
    }

    public Village lands(Set<Land> lands) {
        this.setLands(lands);
        return this;
    }

    public Village addLand(Land land) {
        this.lands.add(land);
        land.setVillage(this);
        return this;
    }

    public Village removeLand(Land land) {
        this.lands.remove(land);
        land.setVillage(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Village)) {
            return false;
        }
        return id != null && id.equals(((Village) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Village{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
