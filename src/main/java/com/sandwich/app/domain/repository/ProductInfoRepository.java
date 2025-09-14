package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.ProductInfoEntity;
import jakarta.persistence.LockModeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, UUID>,
    JpaSpecificationExecutor<ProductInfoEntity>,
    QuerydslPredicateExecutor<ProductInfoEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM ProductInfoEntity a WHERE a.id = :id")
    Optional<ProductInfoEntity> findByIdWithLock(UUID id);
}
