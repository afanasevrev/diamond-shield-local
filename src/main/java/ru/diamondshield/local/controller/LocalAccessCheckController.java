package ru.diamondshield.local.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.diamondshield.local.dto.localcheck.LocalAccessCheckRequest;
import ru.diamondshield.local.dto.localcheck.LocalAccessCheckResponse;
import ru.diamondshield.local.service.LocalAccessCheckService;

@RestController
@RequestMapping("/api/local/access-check")
public class LocalAccessCheckController {

    private final LocalAccessCheckService accessCheckService;

    public LocalAccessCheckController(LocalAccessCheckService accessCheckService) {
        this.accessCheckService = accessCheckService;
    }

    @PostMapping
    public LocalAccessCheckResponse check(@Valid @RequestBody LocalAccessCheckRequest request) {
        return accessCheckService.check(request);
    }
}