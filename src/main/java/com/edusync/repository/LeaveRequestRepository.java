package com.edusync.repository;

import com.edusync.model.LeaveRequest;
import com.edusync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.id = :id")
    Optional<LeaveRequest> findWithStudentById(@Param("id") Long id);

    // Student's own requests
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.student = :student ORDER BY l.createdAt DESC")
    List<LeaveRequest> findByStudentOrderByCreatedAtDesc(@Param("student") User student);

    // Faculty: Pending requests
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.student.department = :dept AND l.facultyStatus = 'PENDING' ORDER BY l.createdAt DESC")
    List<LeaveRequest> findPendingForFaculty(@Param("dept") String department);

    // Faculty: Processed requests
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.student.department = :dept AND l.facultyStatus <> 'PENDING' ORDER BY l.createdAt DESC")
    List<LeaveRequest> findProcessedForFaculty(@Param("dept") String department);

    // Requests by department (for HOD)
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.student.department = :dept ORDER BY l.createdAt DESC")
    List<LeaveRequest> findByDepartment(@Param("dept") String department);

    // Pending requests for HOD approval
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.student.department = :dept AND l.hodStatus = 'PENDING' AND l.facultyStatus = 'APPROVED' ORDER BY l.createdAt DESC")
    List<LeaveRequest> findPendingForHod(@Param("dept") String department);

    // Pending for principal
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student WHERE l.principalStatus = 'PENDING' AND l.hodStatus = 'APPROVED'")
    List<LeaveRequest> findPendingForPrincipal();

    // All requests (admin)
    @Query("SELECT l FROM LeaveRequest l JOIN FETCH l.student ORDER BY l.createdAt DESC")
    List<LeaveRequest> findAllByOrderByCreatedAtDesc();

    // Count by status
    long countByOverallStatus(LeaveRequest.Status status);

    // Count by department
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.student.department = :dept AND l.overallStatus = :status")
    long countByDeptAndStatus(@Param("dept") String dept, @Param("status") LeaveRequest.Status status);
}
