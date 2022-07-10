package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.NotificationTemplate;
import com.melontech.landsys.service.dto.NotificationTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationTemplate} and its DTO {@link NotificationTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationTemplateMapper extends EntityMapper<NotificationTemplateDTO, NotificationTemplate> {}
