package com.edusync.repository;

import com.edusync.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByActiveTrueAndDepartmentOrderByCreatedAtDesc(String department);

    @Query("""
            SELECT a FROM Assignment a
            WHERE a.active = true
              AND a.department = :department
              AND (a.semester IS NULL OR a.semester = '' OR a.semester = :semester)
            ORDER BY a.dueDate ASC, a.createdAt DESC
            """)
    List<Assignment> findVisibleToStudent(
            @Param("department") String department,
            @Param("semester") String semester
    );
}
