package com.sandwich.app.mapper;

import com.sandwich.app.domain.entity.AuditDomainObject;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@MapperConfig
public interface AuditMapperConfig {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    AuditDomainObject convert(@MappingTarget AuditDomainObject entity, Object to);
}
