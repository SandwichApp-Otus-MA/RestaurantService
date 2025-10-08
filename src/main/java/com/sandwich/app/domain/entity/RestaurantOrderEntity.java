package com.sandwich.app.domain.entity;

import com.sandwich.app.models.model.enums.RestaurantOrderStatus;
import com.sandwich.app.models.model.order.OrderItem;
import com.sandwich.app.models.model.restaurant.menu.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@NoArgsConstructor
@Entity
@Table(name = "restaurant_orders")
public class RestaurantOrderEntity extends DomainObject {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RestaurantOrderStatus status = RestaurantOrderStatus.CREATE;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RestaurantEntity restaurant;

    @Column(name = "order_id")
    private UUID orderId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "order_items", columnDefinition = "jsonb")
    private List<OrderItem> items = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "comment")
    private String comment;
}
