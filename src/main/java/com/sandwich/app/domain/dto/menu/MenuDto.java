package com.sandwich.app.domain.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDto {

    private UUID id;

    private String name;

    private List<PositionDto> positions = Collections.emptyList();

    private UUID restaurantId;
}
