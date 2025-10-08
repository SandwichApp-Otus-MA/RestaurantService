package com.sandwich.app.service;

import static com.sandwich.app.models.utils.PageUtil.createPage;
import static com.sandwich.app.models.utils.PageUtil.createPageable;
import static com.sandwich.app.models.utils.PageUtil.createSort;

import com.sandwich.app.domain.entity.ProductEntity;
import com.sandwich.app.domain.repository.ProductRepository;
import com.sandwich.app.mapper.ProductMapper;
import com.sandwich.app.models.model.restaurant.product.ProductDto;
import com.sandwich.app.models.model.restaurant.product.ProductFilter;
import com.sandwich.app.models.pagination.PageData;
import com.sandwich.app.models.pagination.PaginationRequest;
import com.sandwich.app.query.builder.ProductQueryBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductQueryBuilder queryBuilder;

    @Transactional(readOnly = true)
    public ProductDto get(UUID id) {
        return repository.findById(id)
            .map(mapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    public PageData<ProductDto> getAll(PaginationRequest<ProductFilter> request) {
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
    public UUID create(ProductDto product) {
        var newEntity = mapper.convert(new ProductEntity(), product);
        return repository.save(newEntity).getId();
    }

    @Transactional
    public void edit(ProductDto product) {
        var existEntity = Optional.ofNullable(product.getId())
            .flatMap(id -> repository.findByIdWithLock(product.getId()))
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        mapper.convert(existEntity, product);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
