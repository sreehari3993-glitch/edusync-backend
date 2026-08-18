package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.model.Notice;
import com.edusync.security.JwtUtil;
import com.edusync.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Notice board controller.
 *
 * GET  /api/notices/public          — public, no auth (for landing page)
 * GET  /api/notices/all             — all authenticated users
 * POST /api/notices/create          — admin/HOD/principal
 * DELETE /api/notices/{id}          — admin
 */
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;
    private final JwtUtil jwtUtil;

    @GetMapping("/public")
    public ResponseEntity<AppDto.ApiResponse<List<Notice>>> getPublicNotices() {
        List<Notice> notices = noticeService.getActiveNotices();
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Active notices", notices));
    }

    @GetMapping("/all")
    public ResponseEntity<AppDto.ApiResponse<List<Notice>>> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(AppDto.ApiResponse.ok("All notices", notices));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','HOD','PRINCIPAL','FACULTY')")
    public ResponseEntity<AppDto.ApiResponse<Notice>> createNotice(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AppDto.NoticeDto dto
    ) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        Notice notice = noticeService.createNotice(userId, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Notice published", notice));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HOD','PRINCIPAL')")
    public ResponseEntity<AppDto.ApiResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Notice removed", null));
    }
}
