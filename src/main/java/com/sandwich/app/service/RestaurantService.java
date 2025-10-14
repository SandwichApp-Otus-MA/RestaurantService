package com.sandwich.app.service;

import static com.sandwich.app.models.utils.PageUtil.createPage;
import static com.sandwich.app.models.utils.PageUtil.createPageable;
import static com.sandwich.app.models.utils.PageUtil.createSort;

import com.sandwich.app.domain.entity.RestaurantEntity;
import com.sandwich.app.domain.entity.RestaurantOrderEntity;
import com.sandwich.app.domain.repository.RestaurantOrderRepository;
import com.sandwich.app.domain.repository.RestaurantRepository;
import com.sandwich.app.kafka.OrderEventProducer;
import com.sandwich.app.mapper.RestaurantMapper;
import com.sandwich.app.mapper.RestaurantOrderMapper;
import com.sandwich.app.models.model.enums.EventType;
import com.sandwich.app.models.model.enums.OrderStatus;
import com.sandwich.app.models.model.enums.RestaurantOrderStatus;
import com.sandwich.app.models.model.enums.RestaurantStatus;
import com.sandwich.app.models.model.event.NotificationEvent;
import com.sandwich.app.models.model.restaurant.restaurant.RestaurantDto;
import com.sandwich.app.models.model.restaurant.restaurant.RestaurantFilter;
import com.sandwich.app.models.model.restaurant.restaurant.RestaurantOrderRequest;
import com.sandwich.app.models.model.restaurant.restaurant.RestaurantOrderResponse;
import com.sandwich.app.models.pagination.PageData;
import com.sandwich.app.models.pagination.PaginationRequest;
import com.sandwich.app.query.builder.RestaurantQueryBuilder;
import com.sandwich.app.restservices.service.DeliveryRestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantOrderRepository orderRepository;
    private final RestaurantMapper mapper;
    private final RestaurantOrderMapper orderMapper;
    private final RestaurantQueryBuilder queryBuilder;
    private final TransactionTemplate transactionTemplate;
    private final OrderEventProducer orderEventProducer;
    private final DeliveryRestService deliveryRestService;

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

    @Transactional
    public RestaurantOrderResponse createOrder(RestaurantOrderRequest request) {
        var order = orderRepository.findByOrderId(request.getOrderId());

        if (order.isPresent()) {
            return new RestaurantOrderResponse()
                .setId(order.get().getId())
                .setStatus(RestaurantStatus.REJECTED)
                .setComment("Заказ уже существует!");
        }

        var newOrder = orderMapper.convert(new RestaurantOrderEntity(), request);
        newOrder.setStatus(RestaurantOrderStatus.PREPARING);
        newOrder.setRestaurant(repository.getReferenceById(request.getId()));

        // TODO: проверки на выполнимость заказа
        return new RestaurantOrderResponse()
            .setId(newOrder.getId())
            .setStatus(RestaurantStatus.ACCEPTED);
    }

    public void cancelOrder(UUID restaurantId, UUID orderId) {
        changeOrderStatus(restaurantId, orderId, RestaurantOrderStatus.CANCELED);
        orderEventProducer.send(orderId, OrderStatus.RESTAURANT_REJECTED);
    }

    public void changeOrderStatus(UUID restaurantId, UUID orderId, RestaurantOrderStatus status) {
        transactionTemplate.executeWithoutResult(st -> {
            var restaurantOrder = orderRepository.findByOrderId(orderId).orElseThrow((() ->
                new EntityNotFoundException("Не найден заказ с id: %s!".formatted(orderId))));

            if (Objects.equals(restaurantOrder.getRestaurant().getId(), restaurantId)) {
                throw new IllegalArgumentException("Отменяемый заказ не соответствует выбранному ресторану!");
            }

            restaurantOrder.setStatus(status);
        });

        if (status == RestaurantOrderStatus.COMPLETED) {
            // TODO: можно использовать rabbit
            deliveryRestService.notify(new NotificationEvent()
                .setEventType(EventType.SUCCESS)
                .setMessage("Заказ готов и ожидает курьера!")
                .setOperation("RESTAURANT_ORDER_COMPLETED")
                .setData(Map.of("orderId", orderId)));
        }
    }
}
