package com.sandwich.app.controller;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.restaurant.RestaurantDto;
import com.sandwich.app.domain.dto.restaurant.RestaurantSearchRequest;
import com.sandwich.app.service.RestaurantService;
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
@RequestMapping("/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(restaurantService.get(id));
    }

    @GetMapping("/search")
    public ResponseEntity<PageData<RestaurantDto>> getAll(@Valid @RequestBody RestaurantSearchRequest request) {
        return ResponseEntity.ok(restaurantService.getAll(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<UUID> create(@Valid @RequestBody RestaurantDto user) {
        return ResponseEntity.ok(restaurantService.create(user));
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@Valid @RequestBody RestaurantDto user) {
        restaurantService.edit(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId) {
        restaurantService.delete(userId);
        return ResponseEntity.ok().build();
    }
}
