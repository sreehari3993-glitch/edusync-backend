package com.edusync.repository;

import com.edusync.model.PlacementApplication;
import com.edusync.model.User;
import com.edusync.model.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacementApplicationRepository extends JpaRepository<PlacementApplication, Long> {

    List<PlacementApplication> findByStudent(User student);

    List<PlacementApplication> findByDrive(PlacementDrive drive);

    Optional<PlacementApplication> findByStudentAndDrive(User student, PlacementDrive drive);

    boolean existsByStudentAndDrive(User student, PlacementDrive drive);

    List<PlacementApplication> findByDriveAndStatus(PlacementDrive drive, PlacementApplication.ApplicationStatus status);
}
