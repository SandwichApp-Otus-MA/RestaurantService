package com.sandwich.app.domain.dto.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@FieldNameConstants
@NoArgsConstructor
public class Configuration {
    private BigDecimal price;
    private String size;
}
