package com.sandwich.app.domain.dto.pagination;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class PaginationRequest<T> {

    private T filter;

    private Pagination pagination;

    private List<Sorting> sorting;

    public Pagination getPagination() {
        return Optional.ofNullable(pagination).orElseGet(Pagination::byDefault);
    }

    public List<Sorting> getSorting() {
        return Optional.ofNullable(sorting).orElseGet(ArrayList::new);
    }
}
