package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Village} entity.
 */
public class VillageDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private SubDistrictDTO subDistrict;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubDistrictDTO getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(SubDistrictDTO subDistrict) {
        this.subDistrict = subDistrict;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VillageDTO)) {
            return false;
        }

        VillageDTO villageDTO = (VillageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, villageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VillageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subDistrict=" + getSubDistrict() +
            "}";
    }
}
