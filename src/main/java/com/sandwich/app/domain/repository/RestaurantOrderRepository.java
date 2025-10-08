package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.RestaurantOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrderEntity, UUID>,
    JpaSpecificationExecutor<RestaurantOrderEntity>,
    QuerydslPredicateExecutor<RestaurantOrderEntity> {

    Optional<RestaurantOrderEntity> findByOrderId(UUID id);
}
