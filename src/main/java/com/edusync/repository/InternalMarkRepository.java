package com.edusync.repository;

import com.edusync.model.InternalMark;
import com.edusync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalMarkRepository extends JpaRepository<InternalMark, Long> {

    List<InternalMark> findByStudentOrderByCreatedAtDesc(User student);

    List<InternalMark> findByStudentAndSemesterOrderByCreatedAtDesc(User student, String semester);

    List<InternalMark> findByDepartmentAndSeriesNameOrderByCreatedAtDesc(String department, String seriesName);

    Optional<InternalMark> findByStudentIdAndSubjectNameAndSeriesName(Long studentId, String subjectName, String seriesName);
}
