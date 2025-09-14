package com.sandwich.app.query.builder;

import com.querydsl.core.BooleanBuilder;
import com.sandwich.app.domain.dto.product.ProductInfoFilter;
import com.sandwich.app.domain.entity.QProductInfoEntity;
import com.sandwich.app.utils.AdvancedFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductInfoQueryBuilder {

    private static final QProductInfoEntity PRODUCT_INFO = QProductInfoEntity.productInfoEntity;

    public BooleanBuilder createPredicate(ProductInfoFilter filter) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.getId() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(PRODUCT_INFO.id, filter.getId()));
        }

        return predicate;
    }
}
