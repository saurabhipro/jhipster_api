package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SubDistrict.
 */
@Entity
@Table(name = "landsys_sub_district")
public class SubDistrict implements Serializable {

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
    @JsonIgnoreProperties(value = { "state", "subDistricts" }, allowSetters = true)
    private District district;

    @OneToMany(mappedBy = "subDistrict")
    @JsonIgnoreProperties(value = { "subDistrict", "lands", "surveys", "landCompensations", "projectLands" }, allowSetters = true)
    private Set<Village> villages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubDistrict id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SubDistrict name(String name) {
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

    public SubDistrict district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Set<Village> getVillages() {
        return this.villages;
    }

    public void setVillages(Set<Village> villages) {
        if (this.villages != null) {
            this.villages.forEach(i -> i.setSubDistrict(null));
        }
        if (villages != null) {
            villages.forEach(i -> i.setSubDistrict(this));
        }
        this.villages = villages;
    }

    public SubDistrict villages(Set<Village> villages) {
        this.setVillages(villages);
        return this;
    }

    public SubDistrict addVillage(Village village) {
        this.villages.add(village);
        village.setSubDistrict(this);
        return this;
    }

    public SubDistrict removeVillage(Village village) {
        this.villages.remove(village);
        village.setSubDistrict(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubDistrict)) {
            return false;
        }
        return id != null && id.equals(((SubDistrict) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubDistrict{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
