-- Create customers table
CREATE TABLE customers
(
    customer_id BIGINT NOT NULL,
    name        VARCHAR(255),
    email       VARCHAR(255),
    CONSTRAINT pk_customers PRIMARY KEY (customer_id)
);

-- Create orders table
CREATE TABLE orders
(
    order_id    BIGINT NOT NULL,
    amount      DOUBLE PRECISION,
    order_date  VARCHAR(255),
    customer_id BIGINT,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (customer_id);
