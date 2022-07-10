package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melontech.landsys.domain.NoticeStatus} entity.
 */
public class NoticeStatusDTO implements Serializable {

    private Long id;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticeStatusDTO)) {
            return false;
        }

        NoticeStatusDTO noticeStatusDTO = (NoticeStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noticeStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeStatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
