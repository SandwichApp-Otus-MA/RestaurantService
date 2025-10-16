package com.sandwich.app.domain.entity;

import com.sandwich.app.models.model.enums.MeasureUnit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "measure_unit")
    private MeasureUnit measureUnit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_info_id")
    private ProductInfoEntity productInfo;

    @Column(name = "description")
    private String description;
}
