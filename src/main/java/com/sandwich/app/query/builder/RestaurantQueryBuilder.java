package com.sandwich.app.query.builder;

import com.querydsl.core.BooleanBuilder;
import com.sandwich.app.domain.dto.restaurant.RestaurantFilter;
import com.sandwich.app.domain.entity.QRestaurantEntity;
import com.sandwich.app.utils.AdvancedFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantQueryBuilder {

    private static final QRestaurantEntity RESTAURANT = QRestaurantEntity.restaurantEntity;

    public BooleanBuilder createPredicate(RestaurantFilter filter) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.getId() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(RESTAURANT.id, filter.getId()));
        }

        return predicate;
    }
}