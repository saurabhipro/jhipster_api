package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.KhatedayStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Khatedar} entity.
 */
public class KhatedarDTO implements Serializable {

    private Long id;

    private String caseFileNo;

    private String remarks;

    @Lob
    private byte[] noticeFile;

    private String noticeFileContentType;
    private KhatedayStatus status;

    private CitizenDTO citizen;

    private ProjectLandDTO projectLand;

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

    public byte[] getNoticeFile() {
        return noticeFile;
    }

    public void setNoticeFile(byte[] noticeFile) {
        this.noticeFile = noticeFile;
    }

    public String getNoticeFileContentType() {
        return noticeFileContentType;
    }

    public void setNoticeFileContentType(String noticeFileContentType) {
        this.noticeFileContentType = noticeFileContentType;
    }

    public KhatedayStatus getStatus() {
        return status;
    }

    public void setStatus(KhatedayStatus status) {
        this.status = status;
    }

    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
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
            ", noticeFile='" + getNoticeFile() + "'" +
            ", status='" + getStatus() + "'" +
            ", citizen=" + getCitizen() +
            ", projectLand=" + getProjectLand() +
            "}";
    }
}
