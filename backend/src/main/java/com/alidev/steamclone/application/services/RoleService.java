package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.catalog.RoleResponse;
import com.alidev.steamclone.domain.repository.RoleRepository;
import com.alidev.steamclone.infrastructure.mapper.CatalogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final CatalogMapper catalogMapper;

    public RoleService(RoleRepository roleRepository, CatalogMapper catalogMapper) {
        this.roleRepository = roleRepository;
        this.catalogMapper = catalogMapper;
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> list() {
        return roleRepository.findAll().stream()
            .map(catalogMapper::toRoleResponse)
            .toList();
    }
}
