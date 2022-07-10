package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.ProjectStatusHistory;
import com.melontech.landsys.repository.ProjectStatusHistoryRepository;
import com.melontech.landsys.service.ProjectStatusHistoryService;
import com.melontech.landsys.service.dto.ProjectStatusHistoryDTO;
import com.melontech.landsys.service.mapper.ProjectStatusHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectStatusHistory}.
 */
@Service
@Transactional
public class ProjectStatusHistoryServiceImpl implements ProjectStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusHistoryServiceImpl.class);

    private final ProjectStatusHistoryRepository projectStatusHistoryRepository;

    private final ProjectStatusHistoryMapper projectStatusHistoryMapper;

    public ProjectStatusHistoryServiceImpl(
        ProjectStatusHistoryRepository projectStatusHistoryRepository,
        ProjectStatusHistoryMapper projectStatusHistoryMapper
    ) {
        this.projectStatusHistoryRepository = projectStatusHistoryRepository;
        this.projectStatusHistoryMapper = projectStatusHistoryMapper;
    }

    @Override
    public ProjectStatusHistoryDTO save(ProjectStatusHistoryDTO projectStatusHistoryDTO) {
        log.debug("Request to save ProjectStatusHistory : {}", projectStatusHistoryDTO);
        ProjectStatusHistory projectStatusHistory = projectStatusHistoryMapper.toEntity(projectStatusHistoryDTO);
        projectStatusHistory = projectStatusHistoryRepository.save(projectStatusHistory);
        return projectStatusHistoryMapper.toDto(projectStatusHistory);
    }

    @Override
    public ProjectStatusHistoryDTO update(ProjectStatusHistoryDTO projectStatusHistoryDTO) {
        log.debug("Request to save ProjectStatusHistory : {}", projectStatusHistoryDTO);
        ProjectStatusHistory projectStatusHistory = projectStatusHistoryMapper.toEntity(projectStatusHistoryDTO);
        projectStatusHistory = projectStatusHistoryRepository.save(projectStatusHistory);
        return projectStatusHistoryMapper.toDto(projectStatusHistory);
    }

    @Override
    public Optional<ProjectStatusHistoryDTO> partialUpdate(ProjectStatusHistoryDTO projectStatusHistoryDTO) {
        log.debug("Request to partially update ProjectStatusHistory : {}", projectStatusHistoryDTO);

        return projectStatusHistoryRepository
            .findById(projectStatusHistoryDTO.getId())
            .map(existingProjectStatusHistory -> {
                projectStatusHistoryMapper.partialUpdate(existingProjectStatusHistory, projectStatusHistoryDTO);

                return existingProjectStatusHistory;
            })
            .map(projectStatusHistoryRepository::save)
            .map(projectStatusHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectStatusHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectStatusHistories");
        return projectStatusHistoryRepository.findAll(pageable).map(projectStatusHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectStatusHistoryDTO> findOne(Long id) {
        log.debug("Request to get ProjectStatusHistory : {}", id);
        return projectStatusHistoryRepository.findById(id).map(projectStatusHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectStatusHistory : {}", id);
        projectStatusHistoryRepository.deleteById(id);
    }
}
