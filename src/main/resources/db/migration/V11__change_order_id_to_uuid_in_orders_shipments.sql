ALTER TABLE orders_schema.orders_shipments
ALTER COLUMN order_id TYPE UUID USING order_id::uuid;
