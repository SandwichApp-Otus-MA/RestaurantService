package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID>,
    JpaSpecificationExecutor<ProductEntity>,
    QuerydslPredicateExecutor<ProductEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM ProductEntity a WHERE a.id = :id")
    Optional<ProductEntity> findByIdWithLock(UUID id);
}
