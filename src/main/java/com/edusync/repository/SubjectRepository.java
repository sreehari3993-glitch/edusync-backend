package com.edusync.repository;

import com.edusync.model.Subject;
import com.edusync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByDepartmentAndSemester(String department, String semester);

    List<Subject> findByFaculty(User faculty);

    List<Subject> findByDepartment(String department);
}
