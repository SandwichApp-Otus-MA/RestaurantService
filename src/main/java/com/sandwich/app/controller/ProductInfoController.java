package com.sandwich.app.controller;

import com.sandwich.app.models.pagination.PageData;
import com.sandwich.app.models.pagination.PaginationRequest;
import com.sandwich.app.models.model.restaurant.product.ProductInfoDto;
import com.sandwich.app.models.model.restaurant.product.ProductInfoFilter;
import com.sandwich.app.service.ProductInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/v1/product-info")
@RequiredArgsConstructor
public class ProductInfoController {

    private final ProductInfoService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductInfoDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PageData<ProductInfoDto>> getAll(@Valid @RequestBody PaginationRequest<ProductInfoFilter> request) {
        return ResponseEntity.ok(service.getAll(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<UUID> create(@Valid @RequestBody ProductInfoDto productInfo) {
        return ResponseEntity.ok(service.create(productInfo));
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@Valid @RequestBody ProductInfoDto productInfo) {
        service.edit(productInfo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
