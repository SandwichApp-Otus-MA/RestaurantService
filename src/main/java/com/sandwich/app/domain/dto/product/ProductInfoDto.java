package com.sandwich.app.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandwich.app.domain.dto.DomainObjectDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoDto extends DomainObjectDto {

    private Double alcohol;

    private Double calories;

    private Double protein;

    private Double carbohydrate;

    private Double fat;

    private Boolean allergic;
}
