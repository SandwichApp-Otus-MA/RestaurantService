package com.sandwich.app.domain.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandwich.app.domain.dto.DomainObjectDto;
import com.sandwich.app.domain.dto.menu.MenuDto;
import com.sandwich.app.domain.entity.RestaurantEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link RestaurantEntity}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDto extends DomainObjectDto {

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalTime openingTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalTime closingTime;

    private List<MenuDto> menus = Collections.emptyList();

    @NotNull
    private Integer rating;

    @NotNull
    private String address;

    private String description;

}