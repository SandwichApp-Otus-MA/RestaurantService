package com.sandwich.app.domain.dto.restaurant;

import com.sandwich.app.domain.dto.pagination.AdvancedFieldFilter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class RestaurantFilter {

    private AdvancedFieldFilter<UUID> id;
    private AdvancedFieldFilter<String> name;
    private AdvancedFieldFilter<LocalTime> openingTime;
    private AdvancedFieldFilter<LocalTime> closingTime;

}
