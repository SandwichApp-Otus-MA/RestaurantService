package com.sandwich.app.domain.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * Базовая модель сущностей.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@FieldNameConstants
public abstract class DomainObjectDto implements Serializable {

    private UUID id;

}
