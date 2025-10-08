package com.sandwich.app.service;

import static com.sandwich.app.models.utils.PageUtil.createPage;
import static com.sandwich.app.models.utils.PageUtil.createPageable;
import static com.sandwich.app.models.utils.PageUtil.createSort;

import com.sandwich.app.domain.entity.MenuEntity;
import com.sandwich.app.domain.repository.MenuRepository;
import com.sandwich.app.domain.repository.RestaurantRepository;
import com.sandwich.app.mapper.MenuMapper;
import com.sandwich.app.models.model.restaurant.menu.MenuDto;
import com.sandwich.app.models.model.restaurant.menu.MenuFilter;
import com.sandwich.app.models.pagination.PageData;
import com.sandwich.app.models.pagination.PaginationRequest;
import com.sandwich.app.query.builder.MenuQueryBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    private final MenuMapper mapper;
    private final MenuQueryBuilder queryBuilder;

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public MenuDto get(UUID id) {
        return repository.findById(id)
            .map(mapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
    }

    @Transactional(readOnly = true)
    public PageData<MenuDto> getAll(PaginationRequest<MenuFilter> request) {
        var predicate = queryBuilder.createPredicate(request.getFilter());
        var sort = createSort(request.getSorting());
        var pageable = createPageable(request.getPagination(), sort);
        var all = repository.findAll(predicate, pageable);

        return createPage(
            all.getTotalPages(),
            all.getTotalElements(),
            all.getContent().stream()
                .map(mapper::convert)
                .collect(Collectors.toList()),
            request.getPagination());
    }

    @Transactional
    public UUID create(MenuDto menu) {
        var newEntity = mapper.convert(new MenuEntity(), menu);
        var restaurant = restaurantRepository.findById(menu.getRestaurantId())
            .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        newEntity.setRestaurant(restaurant);
        return repository.save(newEntity).getId();
    }

    @Transactional
    public void edit(MenuDto menu) {
        var existEntity = Optional.ofNullable(menu.getId())
            .flatMap(id -> repository.findByIdWithLock(menu.getId()))
            .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        mapper.convert(existEntity, menu);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
