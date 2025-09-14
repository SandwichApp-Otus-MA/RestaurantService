package com.sandwich.app.service;

import static com.sandwich.app.utils.PageUtil.createPage;
import static com.sandwich.app.utils.PageUtil.createPageable;
import static com.sandwich.app.utils.PageUtil.createSort;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.pagination.PaginationRequest;
import com.sandwich.app.domain.dto.product.ProductInfoDto;
import com.sandwich.app.domain.dto.product.ProductInfoFilter;
import com.sandwich.app.domain.entity.ProductInfoEntity;
import com.sandwich.app.domain.repository.ProductInfoRepository;
import com.sandwich.app.mapper.ProductInfoMapper;
import com.sandwich.app.query.builder.ProductInfoQueryBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository repository;
    private final ProductInfoMapper mapper;
    private final ProductInfoQueryBuilder queryBuilder;

    @Transactional(readOnly = true)
    public ProductInfoDto get(UUID id) {
        return repository.findById(id)
            .map(mapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("ProductInfo not found"));
    }

    @Transactional(readOnly = true)
    public PageData<ProductInfoDto> getAll(PaginationRequest<ProductInfoFilter> request) {
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
    public UUID create(ProductInfoDto product) {
        var newEntity = mapper.convert(new ProductInfoEntity(), product);
        return repository.save(newEntity).getId();
    }

    @Transactional
    public void edit(ProductInfoDto product) {
        var existEntity = Optional.ofNullable(product.getId())
            .flatMap(id -> repository.findByIdWithLock(product.getId()))
            .orElseThrow(() -> new EntityNotFoundException("ProductInfo not found"));
        mapper.convert(existEntity, product);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
