package com.melontech.landsys.repository;

import com.melontech.landsys.domain.NoticeStatusInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NoticeStatusInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticeStatusInfoRepository extends JpaRepository<NoticeStatusInfo, Long>, JpaSpecificationExecutor<NoticeStatusInfo> {}
