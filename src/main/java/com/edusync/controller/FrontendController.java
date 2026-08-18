package com.edusync.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Controller
public class FrontendController {

    @Value("${edusync.frontend.html-path:}")
    private String configuredHtmlPath;

    @GetMapping(value = {
            "/",
            "/index.html",
            "/frontend-connected.html",
            "/college-platform-connected.html",
            "/college-platform-review-no-login.html"
    }, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> serveFrontend() {
        Resource frontend = resolveFrontendResource();
        if (frontend == null || !frontend.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).cachePrivate().mustRevalidate())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"index.html\"")
                .contentType(MediaType.TEXT_HTML)
                .body(frontend);
    }

    private Resource resolveFrontendResource() {
        // 1. Try classpath static/index.html or static/frontend-connected.html
        Resource cpIndex = new ClassPathResource("static/index.html");
        if (cpIndex.exists()) {
            return cpIndex;
        }

        Resource cpFrontend = new ClassPathResource("static/frontend-connected.html");
        if (cpFrontend.exists()) {
            return cpFrontend;
        }

        // 2. Try configured path if provided
        String path = configuredHtmlPath == null ? "" : configuredHtmlPath.trim();
        if (!path.isEmpty()) {
            Path customPath = Paths.get(path).toAbsolutePath().normalize();
            if (Files.isRegularFile(customPath)) {
                return new FileSystemResource(customPath);
            }
        }

        // 3. Try local relative files in workspace
        String[] localCandidates = {
                "src/main/resources/static/index.html",
                "frontend-connected.html",
                "college-platform-connected.html",
                "edusync-backend/frontend-connected.html",
                "edusync-backend/src/main/resources/static/index.html"
        };
        for (String rel : localCandidates) {
            Path p = Paths.get(rel).toAbsolutePath().normalize();
            if (Files.isRegularFile(p)) {
                return new FileSystemResource(p);
            }
        }

        // 4. Try Downloads directory
        Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", "college-platform-connected.html");
        if (Files.isRegularFile(downloadsPath)) {
            return new FileSystemResource(downloadsPath);
        }

        Path downloadsAlt = Paths.get(System.getProperty("user.home"), "Downloads", "frontend-connected.html");
        if (Files.isRegularFile(downloadsAlt)) {
            return new FileSystemResource(downloadsAlt);
        }

        return null;
    }
}

