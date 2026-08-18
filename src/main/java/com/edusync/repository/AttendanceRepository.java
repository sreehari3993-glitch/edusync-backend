package com.edusync.repository;

import com.edusync.model.Attendance;
import com.edusync.model.User;
import com.edusync.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentAndSubject(User student, Subject subject);

    List<Attendance> findByStudentAndDate(User student, LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.student = :student AND a.subject = :subject AND a.date = :date")
    Optional<Attendance> findByStudentSubjectDate(
        @Param("student") User student,
        @Param("subject") Subject subject,
        @Param("date") LocalDate date
    );

    // Attendance % for a student in a subject
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.subject = :subject AND a.status IN ('PRESENT','OD','MEDICAL_LEAVE')")
    long countPresent(@Param("student") User student, @Param("subject") Subject subject);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.subject = :subject AND a.status != 'HOLIDAY'")
    long countTotal(@Param("student") User student, @Param("subject") Subject subject);

    // Faculty view — all attendance for a subject on a date
    List<Attendance> findBySubjectAndDate(Subject subject, LocalDate date);

    List<Attendance> findByStudent(User student);
}
