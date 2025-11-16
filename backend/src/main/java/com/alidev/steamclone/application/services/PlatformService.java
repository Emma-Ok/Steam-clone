package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.catalog.PlatformResponse;
import com.alidev.steamclone.domain.repository.PlatformRepository;
import com.alidev.steamclone.infrastructure.mapper.CatalogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final CatalogMapper catalogMapper;

    public PlatformService(PlatformRepository platformRepository, CatalogMapper catalogMapper) {
        this.platformRepository = platformRepository;
        this.catalogMapper = catalogMapper;
    }

    @Transactional(readOnly = true)
    public List<PlatformResponse> list() {
        return platformRepository.findAll().stream().map(catalogMapper::toPlatformResponse).toList();
    }
}
