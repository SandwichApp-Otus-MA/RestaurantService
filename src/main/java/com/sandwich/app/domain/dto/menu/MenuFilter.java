package com.sandwich.app.domain.dto.menu;

import com.sandwich.app.domain.dto.pagination.AdvancedFieldFilter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class MenuFilter {

    private AdvancedFieldFilter<UUID> id;
    private AdvancedFieldFilter<String> name;

}
