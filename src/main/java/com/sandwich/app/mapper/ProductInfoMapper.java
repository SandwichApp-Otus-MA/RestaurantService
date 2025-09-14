package com.sandwich.app.mapper;

import com.sandwich.app.domain.dto.product.ProductInfoDto;
import com.sandwich.app.domain.entity.ProductInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    config = AuditMapperConfig.class
)
public interface ProductInfoMapper {

    ProductInfoDto convert(ProductInfoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductInfoEntity convert(@MappingTarget ProductInfoEntity entity, ProductInfoDto dto);

}
