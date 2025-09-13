package com.sandwich.app.domain.entity;

import com.sandwich.app.domain.dto.enums.PositionFeature;
import com.sandwich.app.domain.dto.menu.Component;
import com.sandwich.app.domain.dto.menu.Configuration;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@NoArgsConstructor
@Entity
@Table(name = "positions")
public class PositionEntity extends AuditDomainObject {

    @Column(name = "name")
    private String name;

    @Column(name = "image_id")
    private UUID imageId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configurations", columnDefinition = "jsonb")
    private List<Configuration> configurations = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "components", columnDefinition = "jsonb")
    private List<Component> components = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "features")
    private List<PositionFeature> features = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "menu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MenuEntity menu;

}
