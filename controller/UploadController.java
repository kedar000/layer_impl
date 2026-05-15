package com.example.questionvalidator.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UploadController {

    @GetMapping("/health")
    public String health() {
        return "Project Running";
    }
}