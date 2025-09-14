package com.sandwich.app.service;

import static com.sandwich.app.utils.PageUtil.createPage;
import static com.sandwich.app.utils.PageUtil.createPageable;
import static com.sandwich.app.utils.PageUtil.createSort;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.pagination.PaginationRequest;
import com.sandwich.app.domain.dto.restaurant.RestaurantDto;
import com.sandwich.app.domain.dto.restaurant.RestaurantFilter;
import com.sandwich.app.domain.entity.RestaurantEntity;
import com.sandwich.app.domain.repository.RestaurantRepository;
import com.sandwich.app.mapper.RestaurantMapper;
import com.sandwich.app.query.builder.RestaurantQueryBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;
    private final RestaurantQueryBuilder queryBuilder;

    @Transactional(readOnly = true)
    public RestaurantDto get(UUID id) {
        return repository.findById(id)
            .map(mapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
    }

    @Transactional(readOnly = true)
    public PageData<RestaurantDto> getAll(PaginationRequest<RestaurantFilter> request) {
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
    public UUID create(RestaurantDto restaurant) {
        checkRestaurantName(restaurant);
        var newEntity = mapper.convert(new RestaurantEntity(), restaurant);
        return repository.save(newEntity).getId();
    }

    @Transactional
    public void edit(RestaurantDto restaurant) {
        var existEntity = Optional.ofNullable(restaurant.getId())
            .flatMap(id -> repository.findByIdWithLock(restaurant.getId()))
            .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        checkRestaurantName(restaurant);
        mapper.convert(existEntity, restaurant);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private void checkRestaurantName(RestaurantDto restaurant) {
        Optional.ofNullable(restaurant.getName())
            .flatMap(id -> repository.findByName(restaurant.getName()))
            .ifPresent(o -> {
                throw new IllegalStateException("Ресторан c наименованием: %s уже существует!".formatted(restaurant.getName()));
            });
    }


}
