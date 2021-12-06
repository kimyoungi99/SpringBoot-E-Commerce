CREATE DATABASE IF NOT EXISTS ecommerce;
USE ecommerce;

CREATE TABLE IF NOT EXISTS user(
    _id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(10) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL,
    address VARCHAR(50) NOT NULL,
    point BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    _id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price LONG NOT NULL,

    FOREIGN KEY (seller_id) REFERENCES user (_id)
);

CREATE TABLE IF NOT EXISTS product_order (
    _id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    order_time DATETIME NOT NULL,
    address VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    order_status TINYINT NOT NULL,

    FOREIGN KEY (product_id) REFERENCES product (_id),
    FOREIGN KEY (buyer_id) REFERENCES user (_id)
);