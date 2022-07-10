package com.melontech.landsys.repository;

import com.melontech.landsys.domain.ProjectStatusHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectStatusHistoryRepository extends JpaRepository<ProjectStatusHistory, Long> {}
