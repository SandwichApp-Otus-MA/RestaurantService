package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.restaurant.RestaurantDto;
import com.sandwich.app.domain.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    config = AuditMapperConfig.class,
    uses = MenuMapper.class
)
public interface RestaurantMapper {

    RestaurantDto convert(RestaurantEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menus", ignore = true)
    RestaurantEntity convert(@MappingTarget RestaurantEntity entity, RestaurantDto dto);
}
