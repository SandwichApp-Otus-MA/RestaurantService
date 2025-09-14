package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.MenuEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<MenuEntity, UUID>,
    JpaSpecificationExecutor<MenuEntity>,
    QuerydslPredicateExecutor<MenuEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM RestaurantEntity a WHERE a.id = :id")
    Optional<MenuEntity> findByIdWithLock(UUID id);
}
