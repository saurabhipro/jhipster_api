package com.melontech.landsys.repository;

import com.melontech.landsys.domain.NoticeStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NoticeStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticeStatusRepository extends JpaRepository<NoticeStatus, Long> {}
