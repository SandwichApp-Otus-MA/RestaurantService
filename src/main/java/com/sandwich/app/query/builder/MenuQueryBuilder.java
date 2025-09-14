package com.sandwich.app.query.builder;

import com.querydsl.core.BooleanBuilder;
import com.sandwich.app.domain.dto.menu.MenuFilter;
import com.sandwich.app.domain.dto.restaurant.RestaurantFilter;
import com.sandwich.app.domain.entity.QMenuEntity;
import com.sandwich.app.utils.AdvancedFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuQueryBuilder {

    private static final QMenuEntity MENU = QMenuEntity.menuEntity;

    public BooleanBuilder createPredicate(MenuFilter filter) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.getId() != null) {
            predicate.and(AdvancedFilterUtil.getExpressionByAdvancedFilter(MENU.id, filter.getId()));
        }

        return predicate;
    }
}