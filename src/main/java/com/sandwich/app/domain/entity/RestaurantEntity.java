package com.sandwich.app.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class RestaurantEntity extends AuditDomainObject {

    @Column(name = "name")
    private String name;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuEntity> menus = new ArrayList<>();

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;
}
