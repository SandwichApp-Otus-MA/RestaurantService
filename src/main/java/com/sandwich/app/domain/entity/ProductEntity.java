package com.sandwich.app.domain.entity;

import com.sandwich.app.domain.dto.enums.MeasureUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "products")
public class ProductEntity extends DomainObject {
    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private Integer count;

    @Enumerated(EnumType.STRING)
    @Column(name = "measure_unit")
    private MeasureUnit measureUnit;

    @OneToOne
    @JoinColumn(name = "product_info_id")
    private ProductInfoEntity productInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
