package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.security.JwtUtil;
import com.edusync.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@CrossOrigin
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<AppDto.AssignmentResponse>> createAssignment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AppDto.AssignmentDto dto
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        AppDto.AssignmentResponse assignment = assignmentService.createAssignment(userId, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Assignment created", assignment));
    }

    @GetMapping("/faculty")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<AppDto.AssignmentResponse>>> getFacultyAssignments(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(
                AppDto.ApiResponse.ok("Faculty assignments", assignmentService.getFacultyAssignments(userId))
        );
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<List<AppDto.AssignmentResponse>>> getStudentAssignments(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(
                AppDto.ApiResponse.ok("Student assignments", assignmentService.getStudentAssignments(userId))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<Void>> deleteAssignment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        assignmentService.deleteAssignment(userId, id);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Assignment removed", null));
    }
}
