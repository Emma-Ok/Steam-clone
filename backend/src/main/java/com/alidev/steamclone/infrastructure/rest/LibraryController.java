package com.alidev.steamclone.infrastructure.rest;

import com.alidev.steamclone.application.dto.library.LibraryEntryRequest;
import com.alidev.steamclone.application.dto.library.LibraryEntryResponse;
import com.alidev.steamclone.application.services.LibraryService;
import com.alidev.steamclone.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/add")
    public ResponseEntity<LibraryEntryResponse> add(@Valid @RequestBody LibraryEntryRequest request) {
        return ResponseEntity.ok(libraryService.addOrUpdate(SecurityUtils.currentUserId(), request));
    }

    @DeleteMapping("/remove/{gameId}")
    public ResponseEntity<Void> remove(@PathVariable UUID gameId) {
        libraryService.remove(SecurityUtils.currentUserId(), gameId);
        return ResponseEntity.noContent().build();
    }
}
