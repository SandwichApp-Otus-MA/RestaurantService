package com.sandwich.app.controller;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.pagination.PaginationRequest;
import com.sandwich.app.domain.dto.product.ProductDto;
import com.sandwich.app.domain.dto.product.ProductFilter;
import com.sandwich.app.service.ProductService;
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
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PageData<ProductDto>> getAll(@Valid @RequestBody PaginationRequest<ProductFilter> request) {
        return ResponseEntity.ok(service.getAll(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<UUID> create(@Valid @RequestBody ProductDto product) {
        return ResponseEntity.ok(service.create(product));
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@Valid @RequestBody ProductDto product) {
        service.edit(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
