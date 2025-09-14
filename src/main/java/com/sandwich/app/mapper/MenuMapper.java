package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.menu.MenuDto;
import com.sandwich.app.domain.entity.MenuEntity;
import com.sandwich.app.domain.entity.PositionEntity;
import lombok.Setter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Mapper(
    config = AuditMapperConfig.class,
    uses = PositionMapper.class
)
public abstract class MenuMapper {

    @Setter(onMethod_ = @Autowired)
    private PositionMapper positionMapper;

    @Mapping(target = "restaurantId", source = "restaurant.id")
    public abstract MenuDto convert(MenuEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "positions", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    public abstract MenuEntity convert(@MappingTarget MenuEntity entity, MenuDto dto);

    @AfterMapping
    protected void afterMapping(@MappingTarget MenuEntity entity, MenuDto dto) {
        MapperUtils.map(entity.getPositions(), dto.getPositions(),
            (to, from) -> Objects.equals(to.getId(), from.getId()),
            () -> new PositionEntity().setMenu(entity),
            (to, from) -> positionMapper.convert(to, from));
    }
}
