package com.sandwich.app.domain.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandwich.app.domain.dto.enums.PositionFeature;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionDto {
    private UUID id;

    private String name;

    private UUID imageId;

    private List<Configuration> configurations= Collections.emptyList();

    private List<PositionFeature> features = Collections.emptyList();

    private List<Component> components = Collections.emptyList();

    private String description;

    private UUID menuId;
}
