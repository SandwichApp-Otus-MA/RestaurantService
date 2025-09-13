package com.sandwich.app.service;

import static com.sandwich.app.utils.PageUtil.createPage;
import static com.sandwich.app.utils.PageUtil.createPageable;
import static com.sandwich.app.utils.PageUtil.createSort;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.restaurant.RestaurantDto;
import com.sandwich.app.domain.dto.restaurant.RestaurantSearchRequest;
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

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantQueryBuilder queryBuilder;

    @Transactional(readOnly = true)
    public RestaurantDto get(UUID id) {
        return restaurantRepository.findById(id)
            .map(restaurantMapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public PageData<RestaurantDto> getAll(RestaurantSearchRequest request) {
        var predicate = queryBuilder.createPredicate(request.getFilter());
        var sort = createSort(request.getSorting());
        var pageable = createPageable(request.getPagination(), sort);
        var all = restaurantRepository.findAll(predicate, pageable);

        return createPage(
            all.getTotalPages(),
            all.getTotalElements(),
            all.getContent().stream()
                .map(restaurantMapper::convert)
                .collect(Collectors.toList()),
            request.getPagination());
    }

    @Transactional
    public UUID create(RestaurantDto restaurant) {
        checkRestaurantName(restaurant);
        var newEntity = restaurantMapper.convert(new RestaurantEntity(), restaurant);
        return restaurantRepository.save(newEntity).getId();
    }

    @Transactional
    public void edit(RestaurantDto restaurant) {
        var existEntity = Optional.ofNullable(restaurant.getId())
            .flatMap(id -> restaurantRepository.findByIdWithLock(restaurant.getId()))
            .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        checkRestaurantName(restaurant);
        restaurantMapper.convert(existEntity, restaurant);
    }

    @Transactional
    public void delete(UUID id) {
        restaurantRepository.deleteById(id);
    }

    private void checkRestaurantName(RestaurantDto restaurant) {
        Optional.ofNullable(restaurant.getName())
            .flatMap(id -> restaurantRepository.findByName(restaurant.getName()))
            .ifPresent(o -> {
                throw new IllegalStateException("Ресторан c наименованием: %s уже существует!".formatted(restaurant.getName()));
            });
    }


}
