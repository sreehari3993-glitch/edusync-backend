package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.model.PlacementApplication;
import com.edusync.model.PlacementDrive;
import com.edusync.security.JwtUtil;
import com.edusync.service.PlacementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Placement controller.
 *
 * GET  /api/placement/drives/public          — all drives (no auth)
 * GET  /api/placement/drives/upcoming        — upcoming drives
 * POST /api/placement/manage/drives          — placement officer creates drive
 * POST /api/placement/apply                  — student applies
 * GET  /api/placement/my-applications        — student's applications
 * GET  /api/placement/manage/drives/{id}/applicants — officer views applicants
 * PUT  /api/placement/manage/applications/{id}/status — update app status
 */
@RestController
@RequestMapping("/api/placement")
@RequiredArgsConstructor
@CrossOrigin
public class PlacementController {

    private final PlacementService placementService;
    private final JwtUtil jwtUtil;

    @GetMapping("/drives/public")
    public ResponseEntity<AppDto.ApiResponse<List<PlacementDrive>>> getPublicDrives() {
        return ResponseEntity.ok(
            AppDto.ApiResponse.ok("All placement drives", placementService.getAllDrives())
        );
    }

    @GetMapping("/drives/upcoming")
    public ResponseEntity<AppDto.ApiResponse<List<PlacementDrive>>> getUpcoming() {
        return ResponseEntity.ok(
            AppDto.ApiResponse.ok("Upcoming drives", placementService.getUpcomingDrives())
        );
    }

    @PostMapping("/manage/drives")
    @PreAuthorize("hasAnyRole('PLACEMENT_OFFICER','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<PlacementDrive>> createDrive(
            @RequestBody AppDto.PlacementDriveDto dto
    ) {
        PlacementDrive drive = placementService.createDrive(dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Drive created", drive));
    }

    @PutMapping("/manage/drives/{id}/status")
    @PreAuthorize("hasAnyRole('PLACEMENT_OFFICER','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<PlacementDrive>> updateDriveStatus(
            @PathVariable Long id,
            @RequestParam PlacementDrive.DriveStatus status
    ) {
        return ResponseEntity.ok(
            AppDto.ApiResponse.ok("Status updated", placementService.updateDriveStatus(id, status))
        );
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<PlacementApplication>> apply(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AppDto.PlacementApplicationDto dto
    ) {
        Long studentId = jwtUtil.extractUserId(authHeader.substring(7));
        PlacementApplication app = placementService.applyToDrive(studentId, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Application submitted!", app));
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<List<PlacementApplication>>> myApplications(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long studentId = jwtUtil.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(
            AppDto.ApiResponse.ok("Your applications", placementService.getStudentApplications(studentId))
        );
    }

    @GetMapping("/manage/drives/{driveId}/applicants")
    @PreAuthorize("hasAnyRole('PLACEMENT_OFFICER','ADMIN','HOD','PRINCIPAL')")
    public ResponseEntity<AppDto.ApiResponse<List<PlacementApplication>>> getApplicants(
            @PathVariable Long driveId
    ) {
        return ResponseEntity.ok(
            AppDto.ApiResponse.ok("Applicants", placementService.getDriveApplicants(driveId))
        );
    }

    @PutMapping("/manage/applications/{id}/status")
    @PreAuthorize("hasAnyRole('PLACEMENT_OFFICER','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<PlacementApplication>> updateAppStatus(
            @PathVariable Long id,
            @RequestParam PlacementApplication.ApplicationStatus status
    ) {
        return ResponseEntity.ok(
            AppDto.ApiResponse.ok("Application status updated", placementService.updateStatus(id, status))
        );
    }
}
