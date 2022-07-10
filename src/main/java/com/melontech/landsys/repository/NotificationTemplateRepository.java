package com.melontech.landsys.repository;

import com.melontech.landsys.domain.NotificationTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NotificationTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {}
