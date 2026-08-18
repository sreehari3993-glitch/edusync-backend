package com.edusync.repository;

import com.edusync.model.Grievance;
import com.edusync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance, Long> {

    List<Grievance> findByStudentOrderByCreatedAtDesc(User student);

    List<Grievance> findByTargetRoleAndDepartmentOrderByCreatedAtDesc(Grievance.TargetRole targetRole, String department);

    List<Grievance> findByTargetRoleOrderByCreatedAtDesc(Grievance.TargetRole targetRole);
}
