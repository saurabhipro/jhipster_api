package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PublicNotification} entity.
 */
public class PublicNotificationDTO implements Serializable {

    private Long id;

    private LocalDate date;

    @Lob
    private byte[] file;

    private String fileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicNotificationDTO)) {
            return false;
        }

        PublicNotificationDTO publicNotificationDTO = (PublicNotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicNotificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicNotificationDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", file='" + getFile() + "'" +
            "}";
    }
}
