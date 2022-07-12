package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.NoticeStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melontech.landsys.domain.NoticeStatusInfo} entity.
 */
public class NoticeStatusInfoDTO implements Serializable {

    private Long id;

    private NoticeStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NoticeStatus getStatus() {
        return status;
    }

    public void setStatus(NoticeStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticeStatusInfoDTO)) {
            return false;
        }

        NoticeStatusInfoDTO noticeStatusInfoDTO = (NoticeStatusInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noticeStatusInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeStatusInfoDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
