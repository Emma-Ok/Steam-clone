package com.alidev.steamclone.infrastructure.rest;

import com.alidev.steamclone.application.dto.catalog.GenreResponse;
import com.alidev.steamclone.application.services.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> list() {
        return ResponseEntity.ok(genreService.list());
    }
}
