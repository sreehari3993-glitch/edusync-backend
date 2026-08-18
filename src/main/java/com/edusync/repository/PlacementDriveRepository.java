package com.edusync.repository;

import com.edusync.model.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementDriveRepository extends JpaRepository<PlacementDrive, Long> {

    List<PlacementDrive> findByStatusOrderByDriveDateDesc(PlacementDrive.DriveStatus status);

    List<PlacementDrive> findAllByOrderByDriveDateDesc();

    @Query("SELECT p FROM PlacementDrive p WHERE p.eligibleBranches LIKE %:branch% ORDER BY p.driveDate DESC")
    List<PlacementDrive> findByEligibleBranch(@Param("branch") String branch);
}
