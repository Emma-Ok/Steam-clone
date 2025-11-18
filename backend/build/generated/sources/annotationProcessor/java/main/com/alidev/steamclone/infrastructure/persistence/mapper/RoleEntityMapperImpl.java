package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.infrastructure.persistence.entity.RoleEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-16T21:30:19-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class RoleEntityMapperImpl implements RoleEntityMapper {

    @Override
    public Role toDomain(RoleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String name = null;

        id = entity.getId();
        name = entity.getName();

        Role role = new Role( id, name );

        return role;
    }

    @Override
    public RoleEntity toEntity(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleEntity.RoleEntityBuilder roleEntity = RoleEntity.builder();

        roleEntity.id( role.getId() );
        roleEntity.name( role.getName() );

        return roleEntity.build();
    }
}
