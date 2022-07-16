package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.SequenceType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.SequenceGenerator} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.SequenceGeneratorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-generators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SequenceGeneratorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SequenceType
     */
    public static class SequenceTypeFilter extends Filter<SequenceType> {

        public SequenceTypeFilter() {}

        public SequenceTypeFilter(SequenceTypeFilter filter) {
            super(filter);
        }

        @Override
        public SequenceTypeFilter copy() {
            return new SequenceTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceTypeFilter seqType;

    private IntegerFilter latestSequence;

    private StringFilter sequenceSuffix;

    private Boolean distinct;

    public SequenceGeneratorCriteria() {}

    public SequenceGeneratorCriteria(SequenceGeneratorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.seqType = other.seqType == null ? null : other.seqType.copy();
        this.latestSequence = other.latestSequence == null ? null : other.latestSequence.copy();
        this.sequenceSuffix = other.sequenceSuffix == null ? null : other.sequenceSuffix.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SequenceGeneratorCriteria copy() {
        return new SequenceGeneratorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SequenceTypeFilter getSeqType() {
        return seqType;
    }

    public SequenceTypeFilter seqType() {
        if (seqType == null) {
            seqType = new SequenceTypeFilter();
        }
        return seqType;
    }

    public void setSeqType(SequenceTypeFilter seqType) {
        this.seqType = seqType;
    }

    public IntegerFilter getLatestSequence() {
        return latestSequence;
    }

    public IntegerFilter latestSequence() {
        if (latestSequence == null) {
            latestSequence = new IntegerFilter();
        }
        return latestSequence;
    }

    public void setLatestSequence(IntegerFilter latestSequence) {
        this.latestSequence = latestSequence;
    }

    public StringFilter getSequenceSuffix() {
        return sequenceSuffix;
    }

    public StringFilter sequenceSuffix() {
        if (sequenceSuffix == null) {
            sequenceSuffix = new StringFilter();
        }
        return sequenceSuffix;
    }

    public void setSequenceSuffix(StringFilter sequenceSuffix) {
        this.sequenceSuffix = sequenceSuffix;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceGeneratorCriteria that = (SequenceGeneratorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(seqType, that.seqType) &&
            Objects.equals(latestSequence, that.latestSequence) &&
            Objects.equals(sequenceSuffix, that.sequenceSuffix) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seqType, latestSequence, sequenceSuffix, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceGeneratorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (seqType != null ? "seqType=" + seqType + ", " : "") +
            (latestSequence != null ? "latestSequence=" + latestSequence + ", " : "") +
            (sequenceSuffix != null ? "sequenceSuffix=" + sequenceSuffix + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
