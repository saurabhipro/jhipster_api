package com.melontech.landsys.service.mapper;

import com.melontech.landsys.domain.SequenceGen;
import com.melontech.landsys.service.dto.SequenceGenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceGen} and its DTO {@link SequenceGenDTO}.
 */
@Mapper(componentModel = "spring")
public interface SequenceGenMapper extends EntityMapper<SequenceGenDTO, SequenceGen> {}
