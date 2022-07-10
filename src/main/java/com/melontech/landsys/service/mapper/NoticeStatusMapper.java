package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.NoticeStatus;
import com.melontech.landsys.service.dto.NoticeStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NoticeStatus} and its DTO {@link NoticeStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoticeStatusMapper extends EntityMapper<NoticeStatusDTO, NoticeStatus> {}
