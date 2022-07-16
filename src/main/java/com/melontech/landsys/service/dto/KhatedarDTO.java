package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.KhatedarStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Khatedar} entity.
 */
public class KhatedarDTO implements Serializable {

    private Long id;

    @NotNull
    private String caseFileNo;

    @NotNull
    private String remarks;

    @NotNull
    private HissaType hissaType;

    @NotNull
    private KhatedarStatus khatedarStatus;

    private ProjectLandDTO projectLand;

    private CitizenDTO citizen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseFileNo() {
        return caseFileNo;
    }

    public void setCaseFileNo(String caseFileNo) {
        this.caseFileNo = caseFileNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public KhatedarStatus getKhatedarStatus() {
        return khatedarStatus;
    }

    public void setKhatedarStatus(KhatedarStatus khatedarStatus) {
        this.khatedarStatus = khatedarStatus;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
    }

    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KhatedarDTO)) {
            return false;
        }

        KhatedarDTO khatedarDTO = (KhatedarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, khatedarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhatedarDTO{" +
            "id=" + getId() +
            ", caseFileNo='" + getCaseFileNo() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            ", khatedarStatus='" + getKhatedarStatus() + "'" +
            ", projectLand=" + getProjectLand() +
            ", citizen=" + getCitizen() +
            "}";
    }
}
