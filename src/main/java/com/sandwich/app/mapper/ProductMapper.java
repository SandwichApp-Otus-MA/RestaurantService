package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.product.ProductDto;
import com.sandwich.app.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    config = AuditMapperConfig.class,
    uses = ProductInfoMapper.class
)
public interface ProductMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    ProductDto convert(ProductEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    ProductEntity convert(@MappingTarget ProductEntity entity, ProductDto dto);

}
