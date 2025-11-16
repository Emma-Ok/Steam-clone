package com.alidev.steamclone.infrastructure.rest;

import com.alidev.steamclone.application.dto.catalog.PlatformResponse;
import com.alidev.steamclone.application.services.PlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponse>> list() {
        return ResponseEntity.ok(platformService.list());
    }
}
