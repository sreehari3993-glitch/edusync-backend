package com.edusync.repository;

import com.edusync.model.Mark;
import com.edusync.model.User;
import com.edusync.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findByStudent(User student);

    List<Mark> findByStudentAndSemester(User student, String semester);

    List<Mark> findBySubjectAndExamType(Subject subject, Mark.ExamType examType);

    @Query("SELECT AVG(m.marksObtained) FROM Mark m WHERE m.subject = :subject AND m.examType = :type")
    Double avgMarksBySubjectAndType(@Param("subject") Subject subject, @Param("type") Mark.ExamType type);
}
