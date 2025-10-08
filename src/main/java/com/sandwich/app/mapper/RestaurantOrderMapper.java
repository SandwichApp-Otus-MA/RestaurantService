package com.sandwich.app.mapper;

import com.sandwich.app.domain.entity.RestaurantOrderEntity;
import com.sandwich.app.models.model.restaurant.restaurant.RestaurantOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface RestaurantOrderMapper {

    @Mapping(target = "restaurant", ignore = true)
    RestaurantOrderEntity convert(@MappingTarget RestaurantOrderEntity restaurantOrderEntity, RestaurantOrderRequest request);
}
