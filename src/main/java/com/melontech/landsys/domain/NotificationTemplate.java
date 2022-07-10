package com.melontech.landsys.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A NotificationTemplate.
 */
@Entity
@Table(name = "landsys_notification_template")
public class NotificationTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "template_text")
    private String templateText;

    @Column(name = "default_use")
    private Boolean defaultUse;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotificationTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public NotificationTemplate name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateText() {
        return this.templateText;
    }

    public NotificationTemplate templateText(String templateText) {
        this.setTemplateText(templateText);
        return this;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public Boolean getDefaultUse() {
        return this.defaultUse;
    }

    public NotificationTemplate defaultUse(Boolean defaultUse) {
        this.setDefaultUse(defaultUse);
        return this;
    }

    public void setDefaultUse(Boolean defaultUse) {
        this.defaultUse = defaultUse;
    }

    public byte[] getFile() {
        return this.file;
    }

    public NotificationTemplate file(byte[] file) {
        this.setFile(file);
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public NotificationTemplate fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationTemplate)) {
            return false;
        }
        return id != null && id.equals(((NotificationTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", templateText='" + getTemplateText() + "'" +
            ", defaultUse='" + getDefaultUse() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
