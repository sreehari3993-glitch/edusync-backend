package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.model.Grievance;
import com.edusync.model.User;
import com.edusync.security.JwtUtil;
import com.edusync.service.GrievanceService;
import com.edusync.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for student Grievances and HOD/Principal resolution.
 */
@RestController
@RequestMapping("/api/grievances")
@RequiredArgsConstructor
@CrossOrigin
public class GrievanceController {

    private final GrievanceService grievanceService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<AppDto.GrievanceResponse>> submitGrievance(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AppDto.GrievanceDto dto
    ) {
        Long studentId = jwtUtil.extractUserId(authHeader.substring(7));
        Grievance g = grievanceService.submitGrievance(studentId, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Grievance submitted", AppDto.GrievanceResponse.from(g)));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<List<AppDto.GrievanceResponse>>> getMyGrievances(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long studentId = jwtUtil.extractUserId(authHeader.substring(7));
        List<AppDto.GrievanceResponse> list = grievanceService.getMyGrievances(studentId).stream()
                .map(AppDto.GrievanceResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(AppDto.ApiResponse.ok("My grievances", list));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('HOD','PRINCIPAL','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<AppDto.GrievanceResponse>>> getPendingGrievances(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        User user = userService.getProfile(userId);
        List<AppDto.GrievanceResponse> list = grievanceService.getGrievancesForRole(user).stream()
                .map(AppDto.GrievanceResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Incoming grievances", list));
    }

    @PutMapping("/{id}/respond")
    @PreAuthorize("hasAnyRole('HOD','PRINCIPAL','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<AppDto.GrievanceResponse>> respondToGrievance(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody AppDto.GrievanceReplyDto dto
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        Grievance updated = grievanceService.respondToGrievance(userId, id, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Grievance response recorded", AppDto.GrievanceResponse.from(updated)));
    }
}
