package com.sandwich.app.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandwich.app.domain.dto.DomainObjectDto;
import com.sandwich.app.domain.dto.enums.MeasureUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto extends DomainObjectDto {

    private String name;

    private Integer count;

    private MeasureUnit measureUnit;

    private ProductInfoDto productInfo;

    private UUID restaurantId;
}
