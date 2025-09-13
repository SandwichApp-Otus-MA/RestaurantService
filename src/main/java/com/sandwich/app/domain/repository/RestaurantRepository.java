package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.RestaurantEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, UUID>,
    JpaSpecificationExecutor<RestaurantEntity>,
    QuerydslPredicateExecutor<RestaurantEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM RestaurantEntity a WHERE a.id = :id")
    Optional<RestaurantEntity> findByIdWithLock(@Param("id") UUID id);

    Optional<RestaurantEntity> findByName(@Param("name") String name);

}