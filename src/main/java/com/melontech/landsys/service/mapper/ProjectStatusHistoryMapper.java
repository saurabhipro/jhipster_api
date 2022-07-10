package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.ProjectStatusHistory;
import com.melontech.landsys.service.dto.ProjectStatusHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectStatusHistory} and its DTO {@link ProjectStatusHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectStatusHistoryMapper extends EntityMapper<ProjectStatusHistoryDTO, ProjectStatusHistory> {}
