CREATE TABLE IF NOT EXISTS orders_schema.orders_shipments
(
    id                 UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    order_id            VARCHAR(40) NOT NULL,
    amount             DECIMAL     NOT NULL
);
