package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.model.InternalMark;
import com.edusync.security.JwtUtil;
import com.edusync.service.InternalMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Internal marks controller.
 *
 * POST /api/marks/save             — faculty/HOD saves batch series marks to DB
 * GET  /api/marks/student          — student retrieves own marks
 * GET  /api/marks/by-dept-series   — faculty/HOD retrieves marks for a department & series
 */
@RestController
@RequestMapping("/api/marks")
@RequiredArgsConstructor
@CrossOrigin
public class InternalMarkController {

    private final InternalMarkService internalMarkService;
    private final JwtUtil jwtUtil;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<InternalMark>>> saveBatchMarks(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AppDto.BatchInternalMarkDto dto
    ) {
        Long facultyId = jwtUtil.extractUserId(authHeader.substring(7));
        List<InternalMark> saved = internalMarkService.saveBatchMarks(facultyId, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Marks saved successfully", saved));
    }

    @GetMapping("/student")
    @PreAuthorize("hasAnyRole('STUDENT','FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<InternalMark>>> getStudentMarks(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long studentId = jwtUtil.extractUserId(authHeader.substring(7));
        List<InternalMark> list = internalMarkService.getMarksForStudent(studentId);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Student marks fetched", list));
    }

    @GetMapping("/by-dept-series")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<InternalMark>>> getMarksByDeptSeries(
            @RequestParam String dept,
            @RequestParam String series
    ) {
        List<InternalMark> list = internalMarkService.getMarksByDepartmentAndSeries(dept, series);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Marks fetched for " + series, list));
    }
}
