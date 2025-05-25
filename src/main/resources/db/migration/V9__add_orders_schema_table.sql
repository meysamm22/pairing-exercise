CREATE SCHEMA orders_schema;
CREATE TABLE IF NOT EXISTS orders_schema.orders
(
    id                 UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    total_amount        DECIMAL     NOT NULL,
    merchant_id         VARCHAR(40) NOT NULL,
    buyer_id            VARCHAR(40) NOT NULL
);
