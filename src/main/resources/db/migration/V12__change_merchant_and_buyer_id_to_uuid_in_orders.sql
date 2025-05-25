ALTER TABLE orders_schema.orders
ALTER COLUMN merchant_id TYPE UUID USING merchant_id::uuid,
ALTER COLUMN buyer_id TYPE UUID USING buyer_id::uuid;
