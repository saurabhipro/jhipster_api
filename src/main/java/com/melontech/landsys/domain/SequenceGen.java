package com.melontech.landsys.domain;

import com.melontech.landsys.domain.enumeration.SequenceType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SequenceGen.
 */
@Entity
@Table(name = "sequence_gen")
public class SequenceGen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seq_type", nullable = false)
    private SequenceType seqType;

    @NotNull
    @Column(name = "latest_sequence", nullable = false)
    private Integer latestSequence;

    @NotNull
    @Column(name = "sequence_suffix", nullable = false)
    private String sequenceSuffix;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SequenceGen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SequenceType getSeqType() {
        return this.seqType;
    }

    public SequenceGen seqType(SequenceType seqType) {
        this.setSeqType(seqType);
        return this;
    }

    public void setSeqType(SequenceType seqType) {
        this.seqType = seqType;
    }

    public Integer getLatestSequence() {
        return this.latestSequence;
    }

    public SequenceGen latestSequence(Integer latestSequence) {
        this.setLatestSequence(latestSequence);
        return this;
    }

    public void setLatestSequence(Integer latestSequence) {
        this.latestSequence = latestSequence;
    }

    public String getSequenceSuffix() {
        return this.sequenceSuffix;
    }

    public SequenceGen sequenceSuffix(String sequenceSuffix) {
        this.setSequenceSuffix(sequenceSuffix);
        return this;
    }

    public void setSequenceSuffix(String sequenceSuffix) {
        this.sequenceSuffix = sequenceSuffix;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceGen)) {
            return false;
        }
        return id != null && id.equals(((SequenceGen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceGen{" +
            "id=" + getId() +
            ", seqType='" + getSeqType() + "'" +
            ", latestSequence=" + getLatestSequence() +
            ", sequenceSuffix='" + getSequenceSuffix() + "'" +
            "}";
    }
}
