package com.melontech.landsys.service.impl;

import com.melontech.landsys.domain.ProjectLand;
import com.melontech.landsys.repository.ProjectLandRepository;
import com.melontech.landsys.service.ProjectLandService;
import com.melontech.landsys.service.dto.ProjectLandDTO;
import com.melontech.landsys.service.mapper.ProjectLandMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectLand}.
 */
@Service
@Transactional
public class ProjectLandServiceImpl implements ProjectLandService {

    private final Logger log = LoggerFactory.getLogger(ProjectLandServiceImpl.class);

    private final ProjectLandRepository projectLandRepository;

    private final ProjectLandMapper projectLandMapper;

    public ProjectLandServiceImpl(ProjectLandRepository projectLandRepository, ProjectLandMapper projectLandMapper) {
        this.projectLandRepository = projectLandRepository;
        this.projectLandMapper = projectLandMapper;
    }

    @Override
    public ProjectLandDTO save(ProjectLandDTO projectLandDTO) {
        log.debug("Request to save ProjectLand : {}", projectLandDTO);
        ProjectLand projectLand = projectLandMapper.toEntity(projectLandDTO);
        projectLand = projectLandRepository.save(projectLand);
        return projectLandMapper.toDto(projectLand);
    }

    @Override
    public ProjectLandDTO update(ProjectLandDTO projectLandDTO) {
        log.debug("Request to save ProjectLand : {}", projectLandDTO);
        ProjectLand projectLand = projectLandMapper.toEntity(projectLandDTO);
        projectLand = projectLandRepository.save(projectLand);
        return projectLandMapper.toDto(projectLand);
    }

    @Override
    public Optional<ProjectLandDTO> partialUpdate(ProjectLandDTO projectLandDTO) {
        log.debug("Request to partially update ProjectLand : {}", projectLandDTO);

        return projectLandRepository
            .findById(projectLandDTO.getId())
            .map(existingProjectLand -> {
                projectLandMapper.partialUpdate(existingProjectLand, projectLandDTO);

                return existingProjectLand;
            })
            .map(projectLandRepository::save)
            .map(projectLandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectLandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectLands");
        return projectLandRepository.findAll(pageable).map(projectLandMapper::toDto);
    }

    public Page<ProjectLandDTO> findAllWithEagerRelationships(Pageable pageable) {
        return projectLandRepository.findAllWithEagerRelationships(pageable).map(projectLandMapper::toDto);
    }

    /**
     *  Get all the projectLands where Survey is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectLandDTO> findAllWhereSurveyIsNull() {
        log.debug("Request to get all projectLands where Survey is null");
        return StreamSupport
            .stream(projectLandRepository.findAll().spliterator(), false)
            .filter(projectLand -> projectLand.getSurvey() == null)
            .map(projectLandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the projectLands where LandCompensation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectLandDTO> findAllWhereLandCompensationIsNull() {
        log.debug("Request to get all projectLands where LandCompensation is null");
        return StreamSupport
            .stream(projectLandRepository.findAll().spliterator(), false)
            .filter(projectLand -> projectLand.getLandCompensation() == null)
            .map(projectLandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectLandDTO> findOne(Long id) {
        log.debug("Request to get ProjectLand : {}", id);
        return projectLandRepository.findOneWithEagerRelationships(id).map(projectLandMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectLand : {}", id);
        projectLandRepository.deleteById(id);
    }
}
