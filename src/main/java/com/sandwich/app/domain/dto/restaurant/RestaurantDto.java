package com.sandwich.app.domain.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandwich.app.domain.dto.menu.MenuDto;
import com.sandwich.app.domain.entity.RestaurantEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link RestaurantEntity}
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDto {

    private UUID id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalTime openingTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalTime closingTime;

    private List<MenuDto> menus = Collections.emptyList();

    private Integer rating;

    private String address;

    private String description;

}