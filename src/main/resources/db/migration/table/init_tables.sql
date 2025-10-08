--liquibase formatted sql
--changeset AVoronov:v1.0.0/init_tables

create table if not exists restaurants
(
    id uuid not null primary key,
    name varchar(255) not null,
    opening_time time not null,
    closing_time time not null,
    rating int not null,
    address text not null,
    description text,
    version bigint not null,
    created_at timestamp not null default current_timestamp,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar
);

create table if not exists menus
(
    id uuid not null primary key,
    name varchar(255) not null,
    restaurant_id uuid not null,
    version bigint not null,
    created_at timestamp not null default current_timestamp,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    constraint menus_restaurant_fkey
        foreign key (restaurant_id)
            references restaurants (id)
            on delete cascade
);

create table if not exists menus
(
    id uuid not null primary key,
    name varchar(255) not null,
    restaurant_id uuid not null,
    version bigint not null,
    created_at timestamp not null default current_timestamp,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    constraint menus_restaurant_fkey
        foreign key (restaurant_id)
            references restaurants (id)
            on delete cascade
);

create table if not exists positions
(
    id uuid not null primary key,
    name varchar(255) not null,
    menu_id uuid not null,
    image_id uuid,
    configurations jsonb,
    components jsonb,
    features varchar[] not null,
    description text,
    version bigint not null,
    created_at timestamp not null default current_timestamp,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    constraint positions_menus_fkey
        foreign key (menu_id)
            references menus (id)
);

create table if not exists products_info
(
    id uuid not null primary key,
    alcohol float4 not null,
    calories float4 not null,
    protein float4 not null,
    carbohydrate float4 not null,
    fat float4 not null,
    allergic boolean not null
);

create table if not exists products
(
    id uuid not null primary key,
    name varchar(255) not null,
    count int not null,
    measure_unit varchar not null,
    restaurant_id uuid not null,
    product_info_id uuid not null,
    constraint products_products_info_fkey
        foreign key (product_info_id)
            references products_info (id),
    constraint products_restaurant_fkey
        foreign key (restaurant_id)
            references restaurants (id)
);

create table restaurant_orders
(
    id uuid not null primary key,
    status varchar not null,
    restaurant_id uuid,
    order_id uuid,
    order_items jsonb,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    comment VARCHAR(255),
    constraint restaurant_orders_restaurant_fkey
        foreign key (restaurant_id)
            references restaurants (id)
);
