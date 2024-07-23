CREATE SCHEMA IF NOT EXISTS car;

CREATE TABLE IF NOT EXISTS car.car
(
    id        BIGSERIAL PRIMARY KEY,
    object_id VARCHAR(255),
    year      VARCHAR(4),
    model     VARCHAR(255)
);