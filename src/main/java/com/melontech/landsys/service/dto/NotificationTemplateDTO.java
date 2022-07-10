package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.melontech.landsys.domain.NotificationTemplate} entity.
 */
public class NotificationTemplateDTO implements Serializable {

    private Long id;

    private String name;

    private String templateText;

    private Boolean defaultUse;

    @Lob
    private byte[] file;

    private String fileContentType;

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

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public Boolean getDefaultUse() {
        return defaultUse;
    }

    public void setDefaultUse(Boolean defaultUse) {
        this.defaultUse = defaultUse;
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
        if (!(o instanceof NotificationTemplateDTO)) {
            return false;
        }

        NotificationTemplateDTO notificationTemplateDTO = (NotificationTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", templateText='" + getTemplateText() + "'" +
            ", defaultUse='" + getDefaultUse() + "'" +
            ", file='" + getFile() + "'" +
            "}";
    }
}
