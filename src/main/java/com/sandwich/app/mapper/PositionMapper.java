package com.sandwich.app.mapper;

import com.sandwich.app.models.model.restaurant.menu.PositionDto;
import com.sandwich.app.domain.entity.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    config = AuditMapperConfig.class
)
public interface PositionMapper {

    @Mapping(target = "menuId", source = "menu.id")
    PositionDto convert(PositionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menu", ignore = true)
    PositionEntity convert(@MappingTarget PositionEntity entity, PositionDto dto);

}
