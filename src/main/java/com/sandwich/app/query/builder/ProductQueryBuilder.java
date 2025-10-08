package com.sandwich.app.query.builder;

import com.querydsl.core.BooleanBuilder;
import com.sandwich.app.models.model.restaurant.product.ProductFilter;
import com.sandwich.app.domain.entity.QProductEntity;
import com.sandwich.app.models.utils.AdvancedFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductQueryBuilder {

    private static final QProductEntity PRODUCT = QProductEntity.productEntity;

    public BooleanBuilder createPredicate(ProductFilter filter) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.getId() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(PRODUCT.id, filter.getId()));
        }

        return predicate;
    }
}