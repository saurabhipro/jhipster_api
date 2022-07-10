package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.HissaType;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.ProjectLand} entity.
 */
public class ProjectLandDTO implements Serializable {

    private Long id;

    private String remarks;

    @Lob
    private byte[] documents;

    private String documentsContentType;
    private HissaType hissaType;

    private LandDTO land;

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getDocuments() {
        return documents;
    }

    public void setDocuments(byte[] documents) {
        this.documents = documents;
    }

    public String getDocumentsContentType() {
        return documentsContentType;
    }

    public void setDocumentsContentType(String documentsContentType) {
        this.documentsContentType = documentsContentType;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public LandDTO getLand() {
        return land;
    }

    public void setLand(LandDTO land) {
        this.land = land;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectLandDTO)) {
            return false;
        }

        ProjectLandDTO projectLandDTO = (ProjectLandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectLandDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectLandDTO{" +
            "id=" + getId() +
            ", remarks='" + getRemarks() + "'" +
            ", documents='" + getDocuments() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            ", land=" + getLand() +
            ", project=" + getProject() +
            "}";
    }
}
