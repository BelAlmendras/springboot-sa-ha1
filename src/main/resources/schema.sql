CREATE TABLE IF NOT EXISTS customers (
    id_customer SERIAL PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(12),
    password VARCHAR(16) NOT NULL
    );

CREATE TABLE IF NOT EXISTS addresses (
    id_address SERIAL PRIMARY KEY,
    address VARCHAR(50) NOT NULL,
    city VARCHAR(20) NOT NULL,
    region VARCHAR(20) NOT NULL,
    zip_code INTEGER,
    id_customer INTEGER NOT NULL,
    CONSTRAINT fk_address_customer
    FOREIGN KEY (id_customer)
    REFERENCES customers (id_customer)
    );

-- Un cliente → una dirección
CREATE UNIQUE INDEX IF NOT EXISTS ux_address_customer
    ON addresses (id_customer);

CREATE TABLE IF NOT EXISTS categories (
    id_category SERIAL PRIMARY KEY,
    category_name VARCHAR(30) NOT NULL,
    description VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS products (
    id_product SERIAL PRIMARY KEY,
    product_name VARCHAR(30) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    description VARCHAR(500),
    --image BYTEA,
    id_category INTEGER NOT NULL,
    CONSTRAINT fk_product_category
    FOREIGN KEY (id_category)
    REFERENCES categories (id_category)
    );

CREATE TABLE IF NOT EXISTS orders (
    id_order SERIAL PRIMARY KEY,
    quantity INTEGER NOT NULL,
    order_date DATE NOT NULL,
    total NUMERIC(10,2) NOT NULL,
    id_product INTEGER NOT NULL,
    id_customer INTEGER NOT NULL,
    CONSTRAINT fk_order_product
    FOREIGN KEY (id_product)
    REFERENCES products (id_product),
    CONSTRAINT fk_order_customer
    FOREIGN KEY (id_customer)
    REFERENCES customers (id_customer)
    );

CREATE TABLE IF NOT EXISTS collections (
    id_collection SERIAL PRIMARY KEY,
    collection_name VARCHAR(50) NOT NULL,
    description VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS product_collections (
    id_product INTEGER NOT NULL,
    id_collection INTEGER NOT NULL,
    CONSTRAINT pk_product_collection
    PRIMARY KEY (id_product, id_collection),
    CONSTRAINT fk_pc_product
    FOREIGN KEY (id_product)
    REFERENCES products (id_product),
    CONSTRAINT fk_pc_collection
    FOREIGN KEY (id_collection)
    REFERENCES collections (id_collection)
    );

DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
id SERIAL PRIMARY KEY,
name VARCHAR(200) NOT NULL,
email VARCHAR(255) NOT NULL,
phone VARCHAR(20),
message TEXT NOT NULL,
created_at TIMESTAMP DEFAULT NOW()
);