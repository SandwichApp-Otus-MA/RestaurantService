package com.sandwich.app.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@NoArgsConstructor
@Entity
@Table(name = "products_info")
public class ProductInfoEntity extends DomainObject {

    @OneToOne(mappedBy = "productInfo")
    private ProductEntity product;

    @Column(name = "alcohol")
    private Double alcohol;

    @Column(name = "calories")
    private Double calories;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "carbohydrate")
    private Double carbohydrate;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "allergic")
    private Boolean allergic;
}
