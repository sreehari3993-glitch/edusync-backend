package com.edusync.repository;

import com.edusync.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByActiveTrueOrderByCreatedAtDesc();

    @Query("SELECT n FROM Notice n WHERE n.active = true AND (n.expiresAt IS NULL OR n.expiresAt > :now) ORDER BY n.createdAt DESC")
    List<Notice> findActiveNotices(@org.springframework.data.repository.query.Param("now") LocalDateTime now);

    List<Notice> findByVisibilityIn(List<Notice.Visibility> visibilities);
}
