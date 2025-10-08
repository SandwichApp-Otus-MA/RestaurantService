package com.sandwich.app.query.builder;

import com.querydsl.core.BooleanBuilder;
import com.sandwich.app.domain.entity.QMenuEntity;
import com.sandwich.app.models.model.restaurant.menu.MenuFilter;
import com.sandwich.app.models.utils.AdvancedFilterUtil;
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