CREATE TABLE countries
(
    id              SMALLINT PRIMARY KEY,

    country_code    VARCHAR(5) NOT NULL UNIQUE,

    country_name    VARCHAR(100) NOT NULL UNIQUE,

    iso_code        VARCHAR(3) NOT NULL UNIQUE,

    region          VARCHAR(50),

    is_active       BOOLEAN NOT NULL DEFAULT TRUE,

    created_at      TIMESTAMP NOT NULL,

    created_by      VARCHAR(100),

    updated_at      TIMESTAMP,

    updated_by      VARCHAR(100)
);

INSERT INTO countries
(id, country_code, country_name, iso_code, region, is_active, created_at)
VALUES (1, 'IN', 'India', 'IND', 'APAC', TRUE, CURRENT_TIMESTAMP),

       (2, 'US', 'United States', 'USA', 'North America', TRUE, CURRENT_TIMESTAMP),

       (3, 'GB', 'United Kingdom', 'GBR', 'Europe', TRUE, CURRENT_TIMESTAMP),

       (4, 'DE', 'Germany', 'DEU', 'Europe', TRUE, CURRENT_TIMESTAMP),

       (5, 'JP', 'Japan', 'JPN', 'APAC', TRUE, CURRENT_TIMESTAMP),

       (6, 'BR', 'Brazil', 'BRA', 'LATAM', TRUE, CURRENT_TIMESTAMP),

       (7, 'CA', 'Canada', 'CAN', 'North America', TRUE, CURRENT_TIMESTAMP),

       (8, 'AU', 'Australia', 'AUS', 'APAC', TRUE, CURRENT_TIMESTAMP);