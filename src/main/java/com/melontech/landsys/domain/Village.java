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
    @JsonIgnoreProperties(value = { "village", "unit", "landType", "state", "projectLands" }, allowSetters = true)
    private Set<Land> lands = new HashSet<>();

    @OneToMany(mappedBy = "village")
    @JsonIgnoreProperties(value = { "projectLand", "village", "landCompensation", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    private Set<Survey> surveys = new HashSet<>();

    @OneToMany(mappedBy = "village")
    @JsonIgnoreProperties(value = { "projectLand", "survey", "village", "paymentAdvices", "paymentFiles" }, allowSetters = true)
    private Set<LandCompensation> landCompensations = new HashSet<>();

    @OneToMany(mappedBy = "village")
    @JsonIgnoreProperties(
        value = {
            "land",
            "project",
            "village",
            "noticeStatusInfo",
            "survey",
            "landCompensation",
            "paymentAdvices",
            "paymentAdviceDetails",
            "paymentFiles",
            "khatedars",
        },
        allowSetters = true
    )
    private Set<ProjectLand> projectLands = new HashSet<>();

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

    public Set<Survey> getSurveys() {
        return this.surveys;
    }

    public void setSurveys(Set<Survey> surveys) {
        if (this.surveys != null) {
            this.surveys.forEach(i -> i.setVillage(null));
        }
        if (surveys != null) {
            surveys.forEach(i -> i.setVillage(this));
        }
        this.surveys = surveys;
    }

    public Village surveys(Set<Survey> surveys) {
        this.setSurveys(surveys);
        return this;
    }

    public Village addSurvey(Survey survey) {
        this.surveys.add(survey);
        survey.setVillage(this);
        return this;
    }

    public Village removeSurvey(Survey survey) {
        this.surveys.remove(survey);
        survey.setVillage(null);
        return this;
    }

    public Set<LandCompensation> getLandCompensations() {
        return this.landCompensations;
    }

    public void setLandCompensations(Set<LandCompensation> landCompensations) {
        if (this.landCompensations != null) {
            this.landCompensations.forEach(i -> i.setVillage(null));
        }
        if (landCompensations != null) {
            landCompensations.forEach(i -> i.setVillage(this));
        }
        this.landCompensations = landCompensations;
    }

    public Village landCompensations(Set<LandCompensation> landCompensations) {
        this.setLandCompensations(landCompensations);
        return this;
    }

    public Village addLandCompensation(LandCompensation landCompensation) {
        this.landCompensations.add(landCompensation);
        landCompensation.setVillage(this);
        return this;
    }

    public Village removeLandCompensation(LandCompensation landCompensation) {
        this.landCompensations.remove(landCompensation);
        landCompensation.setVillage(null);
        return this;
    }

    public Set<ProjectLand> getProjectLands() {
        return this.projectLands;
    }

    public void setProjectLands(Set<ProjectLand> projectLands) {
        if (this.projectLands != null) {
            this.projectLands.forEach(i -> i.setVillage(null));
        }
        if (projectLands != null) {
            projectLands.forEach(i -> i.setVillage(this));
        }
        this.projectLands = projectLands;
    }

    public Village projectLands(Set<ProjectLand> projectLands) {
        this.setProjectLands(projectLands);
        return this;
    }

    public Village addProjectLand(ProjectLand projectLand) {
        this.projectLands.add(projectLand);
        projectLand.setVillage(this);
        return this;
    }

    public Village removeProjectLand(ProjectLand projectLand) {
        this.projectLands.remove(projectLand);
        projectLand.setVillage(null);
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
