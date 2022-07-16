package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.SequenceType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.SequenceGen} entity.
 */
public class SequenceGenDTO implements Serializable {

    private Long id;

    @NotNull
    private SequenceType seqType;

    @NotNull
    private Integer latestSequence;

    @NotNull
    private String sequenceSuffix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SequenceType getSeqType() {
        return seqType;
    }

    public void setSeqType(SequenceType seqType) {
        this.seqType = seqType;
    }

    public Integer getLatestSequence() {
        return latestSequence;
    }

    public void setLatestSequence(Integer latestSequence) {
        this.latestSequence = latestSequence;
    }

    public String getSequenceSuffix() {
        return sequenceSuffix;
    }

    public void setSequenceSuffix(String sequenceSuffix) {
        this.sequenceSuffix = sequenceSuffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceGenDTO)) {
            return false;
        }

        SequenceGenDTO sequenceGenDTO = (SequenceGenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sequenceGenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceGenDTO{" +
            "id=" + getId() +
            ", seqType='" + getSeqType() + "'" +
            ", latestSequence=" + getLatestSequence() +
            ", sequenceSuffix='" + getSequenceSuffix() + "'" +
            "}";
    }
}
