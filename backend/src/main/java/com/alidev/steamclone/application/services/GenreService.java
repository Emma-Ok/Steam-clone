package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.catalog.GenreResponse;
import com.alidev.steamclone.domain.repository.GenreRepository;
import com.alidev.steamclone.infrastructure.mapper.CatalogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final CatalogMapper catalogMapper;

    public GenreService(GenreRepository genreRepository, CatalogMapper catalogMapper) {
        this.genreRepository = genreRepository;
        this.catalogMapper = catalogMapper;
    }

    @Transactional(readOnly = true)
    public List<GenreResponse> list() {
        return genreRepository.findAll().stream().map(catalogMapper::toGenreResponse).toList();
    }
}
