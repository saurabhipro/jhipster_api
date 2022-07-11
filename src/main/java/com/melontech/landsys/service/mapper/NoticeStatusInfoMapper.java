package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.NoticeStatusInfo;
import com.melontech.landsys.service.dto.NoticeStatusInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NoticeStatusInfo} and its DTO {@link NoticeStatusInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoticeStatusInfoMapper extends EntityMapper<NoticeStatusInfoDTO, NoticeStatusInfo> {}
