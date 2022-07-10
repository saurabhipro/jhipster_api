package com.melontech.landsys.repository;

import com.melontech.landsys.domain.PublicNotification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PublicNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicNotificationRepository extends JpaRepository<PublicNotification, Long> {}
