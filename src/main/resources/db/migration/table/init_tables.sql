--liquibase formatted sql
--changeset AVoronov:v1.0.0/init_tables

CREATE TABLE IF NOT EXISTS restaurants
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    opening_time time NOT NULL,
    closing_time time NOT NULL,
    rating int NOT NULL,
    address text NOT NULL,
    description text,
    version bigint NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar
);

CREATE TABLE IF NOT EXISTS menus
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    restaurant_id uuid NOT NULL,
    version bigint NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    CONSTRAINT menus_restaurant_fkey
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurants (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS menus
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    restaurant_id uuid NOT NULL,
    version bigint NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    CONSTRAINT menus_restaurant_fkey
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurants (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS positions
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    menu_id uuid NOT NULL,
    image_id uuid,
    configurations jsonb,
    components jsonb,
    features varchar[] NOT NULL,
    description text,
    version bigint NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    CONSTRAINT positions_menus_fkey
        FOREIGN KEY (menu_id)
            REFERENCES menus (id)
);

CREATE TABLE IF NOT EXISTS products_info
(
    id uuid NOT NULL PRIMARY KEY,
    alcohol float4 NOT NULL,
    calories float4 NOT NULL,
    protein float4 NOT NULL,
    carbohydrate float4 NOT NULL,
    fat float4 NOT NULL,
    allergic boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    count int NOT NULL,
    measure_unit varchar NOT NULL,
    restaurant_id uuid NOT NULL,
    product_info_id uuid NOT NULL,
    CONSTRAINT products_products_info_fkey
        FOREIGN KEY (product_info_id)
            REFERENCES products_info (id),
    CONSTRAINT products_restaurant_fkey
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurants (id)
);
