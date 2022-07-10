package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.PublicNotification;
import com.melontech.landsys.service.dto.PublicNotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicNotification} and its DTO {@link PublicNotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicNotificationMapper extends EntityMapper<PublicNotificationDTO, PublicNotification> {}
