package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.NoticeStatus;
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
 * Criteria class for the {@link com.melontech.landsys.domain.NoticeStatusInfo} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.NoticeStatusInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notice-status-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class NoticeStatusInfoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering NoticeStatus
     */
    public static class NoticeStatusFilter extends Filter<NoticeStatus> {

        public NoticeStatusFilter() {}

        public NoticeStatusFilter(NoticeStatusFilter filter) {
            super(filter);
        }

        @Override
        public NoticeStatusFilter copy() {
            return new NoticeStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private NoticeStatusFilter status;

    private LongFilter projectLandId;

    private Boolean distinct;

    public NoticeStatusInfoCriteria() {}

    public NoticeStatusInfoCriteria(NoticeStatusInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NoticeStatusInfoCriteria copy() {
        return new NoticeStatusInfoCriteria(this);
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

    public NoticeStatusFilter getStatus() {
        return status;
    }

    public NoticeStatusFilter status() {
        if (status == null) {
            status = new NoticeStatusFilter();
        }
        return status;
    }

    public void setStatus(NoticeStatusFilter status) {
        this.status = status;
    }

    public LongFilter getProjectLandId() {
        return projectLandId;
    }

    public LongFilter projectLandId() {
        if (projectLandId == null) {
            projectLandId = new LongFilter();
        }
        return projectLandId;
    }

    public void setProjectLandId(LongFilter projectLandId) {
        this.projectLandId = projectLandId;
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
        final NoticeStatusInfoCriteria that = (NoticeStatusInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, projectLandId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeStatusInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
