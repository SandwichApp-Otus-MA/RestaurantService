package com.sandwich.app.domain.dto.product;

import com.sandwich.app.domain.dto.pagination.AdvancedFieldFilter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;


@Data
@Accessors(chain = true)
public class ProductInfoFilter {

    private AdvancedFieldFilter<UUID> id;
    private AdvancedFieldFilter<String> name;
}