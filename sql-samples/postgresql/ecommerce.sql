-- ============================================================
-- E-COMMERCE ANALYTICS SCHEMA (POSTGRESQL)
-- Designed for dashboarding and analytics (Apache Superset ready)
-- ============================================================

-- OVERVIEW
-- This schema models a simplified e-commerce platform with a focus on
-- analytics and reporting. It captures user behavior, transactions,
-- product hierarchy, payments, logistics, and feedback.

-- The design enables:
-- - Revenue and sales trend analysis
-- - Customer segmentation and cohort analysis
-- - Product and category performance insights
-- - Funnel analysis (order → payment → delivery)
-- - Operational metrics (shipping, payment success rates)

-- ------------------------------------------------------------
-- TABLE DESCRIPTIONS
-- ------------------------------------------------------------

-- USERS
-- Stores customer profile and demographic information.
-- Used for user segmentation, geography-based insights, and cohort analysis.

-- CATEGORIES
-- Represents hierarchical product categories.
-- Supports parent-child relationships for drill-down analytics.

-- PRODUCTS
-- Contains product catalog information.
-- Linked to categories; used for product performance and inventory insights.

-- ORDERS
-- Represents customer orders at a transaction level.
-- Captures order lifecycle, status, and monetary details.

-- ORDER_ITEMS
-- Line-level details of each order (one row per product in an order).
-- Core fact table for revenue, quantity, and product-level analysis.

-- PAYMENTS
-- Stores payment transactions associated with orders.
-- Used to analyze payment methods, success/failure rates, and revenue validation.

-- SHIPMENTS
-- Tracks shipping and delivery lifecycle of orders.
-- Enables logistics and delivery performance analysis.

-- REVIEWS
-- Captures user feedback and ratings for products.
-- Used for product quality insights and sentiment analysis.

-- ------------------------------------------------------------
-- DATA MODEL NOTES
-- ------------------------------------------------------------

-- 1. orders → order_items is a one-to-many relationship
-- 2. products → categories is a many-to-one relationship
-- 3. categories supports hierarchical structure via self-reference
-- 4. users → orders enables customer-level analytics
-- 5. payments and shipments are linked to orders (operational extensions)
-- 6. reviews link users and products (feedback loop)

-- This schema is optimized for analytical queries, not OLTP workloads.
-- ============================================================

DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS shipments;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id         BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(100),
    last_name       VARCHAR(100),
    email           VARCHAR(255) UNIQUE NOT NULL,
    phone           VARCHAR(20),
    gender          VARCHAR(10),
    date_of_birth   DATE,
    city            VARCHAR(100),
    state           VARCHAR(100),
    country         VARCHAR(100),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    category_id     BIGSERIAL PRIMARY KEY,
    category_name   VARCHAR(150) NOT NULL,
    parent_category_id BIGINT REFERENCES categories(category_id),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    product_id      BIGSERIAL PRIMARY KEY,
    product_name    VARCHAR(255) NOT NULL,
    category_id     BIGINT REFERENCES categories(category_id),
    price           NUMERIC(10,2) NOT NULL,
    cost_price      NUMERIC(10,2),
    brand           VARCHAR(100),
    stock_quantity  INT DEFAULT 0,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    order_id        BIGSERIAL PRIMARY KEY,
    user_id         BIGINT REFERENCES users(user_id),
    order_status    VARCHAR(50), -- CREATED, SHIPPED, DELIVERED, CANCELLED
    order_date      TIMESTAMP NOT NULL,
    total_amount    NUMERIC(12,2),
    discount_amount NUMERIC(10,2) DEFAULT 0,
    final_amount    NUMERIC(12,2),
    payment_status  VARCHAR(50), -- PENDING, PAID, FAILED
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    order_item_id   BIGSERIAL PRIMARY KEY,
    order_id        BIGINT REFERENCES orders(order_id),
    product_id      BIGINT REFERENCES products(product_id),
    quantity        INT NOT NULL,
    unit_price      NUMERIC(10,2) NOT NULL,
    total_price     NUMERIC(12,2)
);

CREATE TABLE payments (
    payment_id      BIGSERIAL PRIMARY KEY,
    order_id        BIGINT REFERENCES orders(order_id),
    payment_method  VARCHAR(50), -- CARD, UPI, NETBANKING, WALLET
    payment_status  VARCHAR(50), -- SUCCESS, FAILED, REFUNDED
    transaction_id  VARCHAR(255),
    payment_date    TIMESTAMP,
    amount          NUMERIC(12,2)
);

CREATE TABLE shipments (
    shipment_id     BIGSERIAL PRIMARY KEY,
    order_id        BIGINT REFERENCES orders(order_id),
    shipment_status VARCHAR(50), -- PACKED, SHIPPED, IN_TRANSIT, DELIVERED
    courier_name    VARCHAR(100),
    tracking_number VARCHAR(255),
    shipped_date    TIMESTAMP,
    delivered_date  TIMESTAMP
);

CREATE TABLE reviews (
    review_id       BIGSERIAL PRIMARY KEY,
    user_id         BIGINT REFERENCES users(user_id),
    product_id      BIGINT REFERENCES products(product_id),
    rating          INT CHECK (rating BETWEEN 1 AND 5),
    review_text     TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (
    first_name,
    last_name,
    email,
    phone,
    gender,
    date_of_birth,
    city,
    state,
    country
) VALUES
('Aarav', 'Sharma', 'aarav.sharma@example.com', '9876543210', 'Male', '1992-05-14', 'Bengaluru', 'Karnataka', 'India'),
('Diya', 'Patel', 'diya.patel@example.com', '9123456780', 'Female', '1995-08-21', 'Ahmedabad', 'Gujarat', 'India'),
('Vivaan', 'Reddy', 'vivaan.reddy@example.com', '9988776655', 'Male', '1990-12-02', 'Hyderabad', 'Telangana', 'India'),
('Ananya', 'Iyer', 'ananya.iyer@example.com', '9012345678', 'Female', '1993-03-11', 'Chennai', 'Tamil Nadu', 'India'),
('Arjun', 'Singh', 'arjun.singh@example.com', '9898989898', 'Male', '1988-07-19', 'Delhi', 'Delhi', 'India'),
('Ishita', 'Gupta', 'ishita.gupta@example.com', '9765432109', 'Female', '1996-11-25', 'Mumbai', 'Maharashtra', 'India'),
('Karthik', 'Nair', 'karthik.nair@example.com', '9345678901', 'Male', '1991-01-30', 'Kochi', 'Kerala', 'India'),
('Sneha', 'Das', 'sneha.das@example.com', '9234567890', 'Female', '1994-06-17', 'Kolkata', 'West Bengal', 'India'),
('Rohit', 'Verma', 'rohit.verma@example.com', '9112233445', 'Male', '1989-09-09', 'Pune', 'Maharashtra', 'India'),
('Meera', 'Joshi', 'meera.joshi@example.com', '9001122334', 'Female', '1997-02-14', 'Jaipur', 'Rajasthan', 'India');


INSERT INTO categories (
    category_name,
    parent_category_id
) VALUES
-- Top-level categories
('Electronics', NULL),
('Fashion', NULL),
('Home & Kitchen', NULL),
('Beauty & Personal Care', NULL),
('Sports & Fitness', NULL),

-- Electronics sub-categories
('Mobiles', 1),
('Laptops', 1),
('Televisions', 1),
('Accessories', 1),

-- Fashion sub-categories
('Men Clothing', 2),
('Women Clothing', 2),
('Footwear', 2),

-- Home & Kitchen sub-categories
('Furniture', 3),
('Kitchen Appliances', 3),
('Home Decor', 3),

-- Beauty sub-categories
('Skincare', 4),
('Haircare', 4),
('Makeup', 4),

-- Sports sub-categories
('Gym Equipment', 5),
('Outdoor Sports', 5);

INSERT INTO products (
    product_name,
    category_id,
    price,
    cost_price,
    brand,
    stock_quantity
) VALUES
-- Mobiles (category_id = 6)
('iPhone 14', 6, 79999.00, 65000.00, 'Apple', 50),
('Galaxy S21 FE', 6, 49999.00, 40000.00, 'Samsung', 70),
('Redmi Note 12', 6, 17999.00, 14000.00, 'Xiaomi', 120),

-- Laptops (category_id = 7)
('MacBook Air M2', 7, 119999.00, 100000.00, 'Apple', 30),
('Dell Inspiron 15', 7, 64999.00, 52000.00, 'Dell', 45),
('HP Pavilion 14', 7, 57999.00, 46000.00, 'HP', 40),

-- Televisions (category_id = 8)
('Sony Bravia 55 inch', 8, 89999.00, 75000.00, 'Sony', 25),
('Samsung Crystal 4K', 8, 59999.00, 48000.00, 'Samsung', 35),

-- Accessories (category_id = 9)
('Boat Wireless Earbuds', 9, 2999.00, 1200.00, 'Boat', 200),
('Logitech Wireless Mouse', 9, 1499.00, 700.00, 'Logitech', 150),

-- Men Clothing (category_id = 10)
('Men Cotton T-Shirt', 10, 799.00, 300.00, 'Roadster', 300),
('Men Slim Fit Jeans', 10, 1999.00, 900.00, 'Levis', 180),

-- Women Clothing (category_id = 11)
('Women Kurti Set', 11, 1499.00, 700.00, 'Biba', 220),
('Women Summer Dress', 11, 1799.00, 800.00, 'Zara', 160),

-- Footwear (category_id = 12)
('Nike Running Shoes', 12, 4999.00, 2500.00, 'Nike', 140),
('Adidas Sneakers', 12, 5999.00, 3000.00, 'Adidas', 130),

-- Furniture (category_id = 13)
('Wooden Study Table', 13, 7999.00, 5000.00, 'Ikea', 60),
('Ergonomic Office Chair', 13, 12999.00, 8000.00, 'GreenSoul', 55),

-- Kitchen Appliances (category_id = 14)
('Prestige Pressure Cooker', 14, 2499.00, 1200.00, 'Prestige', 180),
('Philips Air Fryer', 14, 9999.00, 7000.00, 'Philips', 70),

-- Home Decor (category_id = 15)
('Wall Art Painting', 15, 1999.00, 800.00, 'Home Centre', 90),
('Decorative Table Lamp', 15, 2999.00, 1500.00, 'Urban Ladder', 75),

-- Skincare (category_id = 16)
('Face Moisturizer', 16, 499.00, 200.00, 'Nivea', 250),
('Sunscreen SPF 50', 16, 699.00, 300.00, 'Lakme', 220),

-- Haircare (category_id = 17)
('Shampoo Anti-Dandruff', 17, 349.00, 150.00, 'Head & Shoulders', 300),
('Hair Oil Coconut', 17, 199.00, 80.00, 'Parachute', 350),

-- Makeup (category_id = 18)
('Lakme Lipstick', 18, 599.00, 250.00, 'Lakme', 200),
('Maybelline Foundation', 18, 799.00, 350.00, 'Maybelline', 180),

-- Gym Equipment (category_id = 19)
('Dumbbell Set 10kg', 19, 1999.00, 1000.00, 'Kore', 100),
('Treadmill Basic', 19, 24999.00, 18000.00, 'PowerMax', 20),

-- Outdoor Sports (category_id = 20)
('Cricket Bat', 20, 2999.00, 1500.00, 'SG', 120),
('Football Size 5', 20, 999.00, 400.00, 'Nivia', 200);


INSERT INTO orders (
    user_id,
    order_status,
    order_date,
    total_amount,
    discount_amount,
    final_amount,
    payment_status
) VALUES
(1, 'DELIVERED', '2024-01-05 10:15:00', 79999.00, 5000.00, 74999.00, 'PAID'),
(2, 'DELIVERED', '2024-01-07 14:20:00', 17999.00, 1000.00, 16999.00, 'PAID'),
(3, 'SHIPPED',   '2024-01-10 09:45:00', 49999.00, 3000.00, 46999.00, 'PAID'),
(4, 'CANCELLED', '2024-01-12 18:10:00', 1499.00, 0.00, 1499.00, 'FAILED'),
(5, 'DELIVERED', '2024-01-15 11:30:00', 64999.00, 4000.00, 60999.00, 'PAID'),

(6, 'DELIVERED', '2024-02-02 16:05:00', 9999.00, 500.00, 9499.00, 'PAID'),
(7, 'DELIVERED', '2024-02-05 12:40:00', 2999.00, 200.00, 2799.00, 'PAID'),
(8, 'SHIPPED',   '2024-02-08 19:25:00', 1999.00, 100.00, 1899.00, 'PAID'),
(9, 'DELIVERED', '2024-02-11 08:55:00', 2499.00, 0.00, 2499.00, 'PAID'),
(10,'CANCELLED', '2024-02-14 21:10:00', 5999.00, 0.00, 5999.00, 'FAILED'),

(1, 'DELIVERED', '2024-03-01 10:00:00', 119999.00, 10000.00, 109999.00, 'PAID'),
(2, 'DELIVERED', '2024-03-03 15:45:00', 799.00, 50.00, 749.00, 'PAID'),
(3, 'SHIPPED',   '2024-03-06 17:30:00', 12999.00, 1000.00, 11999.00, 'PAID'),
(4, 'DELIVERED', '2024-03-09 13:20:00', 499.00, 0.00, 499.00, 'PAID'),
(5, 'DELIVERED', '2024-03-12 09:10:00', 89999.00, 5000.00, 84999.00, 'PAID'),

(6, 'DELIVERED', '2024-03-15 20:00:00', 2999.00, 200.00, 2799.00, 'PAID'),
(7, 'SHIPPED',   '2024-03-18 11:50:00', 1999.00, 100.00, 1899.00, 'PAID'),
(8, 'DELIVERED', '2024-03-21 16:40:00', 24999.00, 2000.00, 22999.00, 'PAID'),
(9, 'DELIVERED', '2024-03-25 18:15:00', 999.00, 50.00, 949.00, 'PAID'),
(10,'DELIVERED', '2024-03-28 22:05:00', 599.00, 0.00, 599.00, 'PAID');


INSERT INTO order_items (
    order_id,
    product_id,
    quantity,
    unit_price,
    total_price
) VALUES
-- Order 1
(1, 1, 1, 79999.00, 79999.00),

-- Order 2
(2, 3, 1, 17999.00, 17999.00),

-- Order 3
(3, 2, 1, 49999.00, 49999.00),

-- Order 4 (cancelled)
(4, 13, 1, 1499.00, 1499.00),

-- Order 5
(5, 5, 1, 64999.00, 64999.00),

-- Order 6
(6, 20, 1, 9999.00, 9999.00),

-- Order 7
(7, 9, 1, 2999.00, 2999.00),

-- Order 8 (multi-item)
(8, 21, 1, 1999.00, 1999.00),

-- Order 9
(9, 19, 1, 2499.00, 2499.00),

-- Order 10 (cancelled)
(10, 15, 1, 5999.00, 5999.00),

-- Order 11
(11, 4, 1, 119999.00, 119999.00),

-- Order 12
(12, 11, 1, 799.00, 799.00),

-- Order 13
(13, 18, 1, 12999.00, 12999.00),

-- Order 14
(14, 23, 1, 499.00, 499.00),

-- Order 15
(15, 7, 1, 89999.00, 89999.00),

-- Order 16 (multi-item)
(16, 9, 1, 2999.00, 2999.00),

-- Order 17
(17, 21, 1, 1999.00, 1999.00),

-- Order 18
(18, 29, 1, 24999.00, 24999.00),

-- Order 19
(19, 32, 1, 999.00, 999.00),

-- Order 20
(20, 27, 1, 599.00, 599.00);



INSERT INTO payments (
    order_id,
    payment_method,
    payment_status,
    transaction_id,
    payment_date,
    amount
) VALUES
(1, 'CARD',       'SUCCESS', 'TXN1001', '2024-01-05 10:16:00', 74999.00),
(2, 'UPI',        'SUCCESS', 'TXN1002', '2024-01-07 14:21:00', 16999.00),
(3, 'NETBANKING', 'SUCCESS', 'TXN1003', '2024-01-10 09:46:00', 46999.00),
(4, 'UPI',        'FAILED',  'TXN1004', '2024-01-12 18:11:00', 1499.00),
(5, 'CARD',       'SUCCESS', 'TXN1005', '2024-01-15 11:31:00', 60999.00),

(6, 'UPI',        'SUCCESS', 'TXN1006', '2024-02-02 16:06:00', 9499.00),
(7, 'WALLET',     'SUCCESS', 'TXN1007', '2024-02-05 12:41:00', 2799.00),
(8, 'UPI',        'SUCCESS', 'TXN1008', '2024-02-08 19:26:00', 1899.00),
(9, 'NETBANKING', 'SUCCESS', 'TXN1009', '2024-02-11 08:56:00', 2499.00),
(10,'CARD',       'FAILED',  'TXN1010', '2024-02-14 21:11:00', 5999.00),

(11,'CARD',       'SUCCESS', 'TXN1011', '2024-03-01 10:01:00', 109999.00),
(12,'UPI',        'SUCCESS', 'TXN1012', '2024-03-03 15:46:00', 749.00),
(13,'NETBANKING', 'SUCCESS', 'TXN1013', '2024-03-06 17:31:00', 11999.00),
(14,'WALLET',     'SUCCESS', 'TXN1014', '2024-03-09 13:21:00', 499.00),
(15,'CARD',       'SUCCESS', 'TXN1015', '2024-03-12 09:11:00', 84999.00),

(16,'UPI',        'SUCCESS', 'TXN1016', '2024-03-15 20:01:00', 2799.00),
(17,'WALLET',     'SUCCESS', 'TXN1017', '2024-03-18 11:51:00', 1899.00),
(18,'CARD',       'SUCCESS', 'TXN1018', '2024-03-21 16:41:00', 22999.00),
(19,'UPI',        'SUCCESS', 'TXN1019', '2024-03-25 18:16:00', 949.00),
(20,'NETBANKING', 'SUCCESS', 'TXN1020', '2024-03-28 22:06:00', 599.00);


INSERT INTO shipments (
    order_id,
    shipment_status,
    courier_name,
    tracking_number,
    shipped_date,
    delivered_date
) VALUES
(1,  'DELIVERED',  'BlueDart',     'TRK1001', '2024-01-06 09:00:00', '2024-01-08 14:00:00'),
(2,  'DELIVERED',  'Delhivery',    'TRK1002', '2024-01-08 10:00:00', '2024-01-10 16:30:00'),
(3,  'IN_TRANSIT', 'Ekart',        'TRK1003', '2024-01-11 11:00:00', NULL),
(5,  'DELIVERED',  'BlueDart',     'TRK1005', '2024-01-16 08:30:00', '2024-01-18 12:45:00'),

(6,  'DELIVERED',  'Delhivery',    'TRK1006', '2024-02-03 09:15:00', '2024-02-05 13:20:00'),
(7,  'DELIVERED',  'Ekart',        'TRK1007', '2024-02-06 14:00:00', '2024-02-08 18:00:00'),
(8,  'SHIPPED',    'BlueDart',     'TRK1008', '2024-02-09 10:30:00', NULL),
(9,  'DELIVERED',  'India Post',   'TRK1009', '2024-02-12 07:45:00', '2024-02-15 11:00:00'),

(11, 'DELIVERED',  'BlueDart',     'TRK1011', '2024-03-02 09:00:00', '2024-03-04 15:00:00'),
(12, 'DELIVERED',  'Delhivery',    'TRK1012', '2024-03-04 10:15:00', '2024-03-06 13:30:00'),
(13, 'IN_TRANSIT', 'Ekart',        'TRK1013', '2024-03-07 12:00:00', NULL),
(14, 'DELIVERED',  'India Post',   'TRK1014', '2024-03-10 08:20:00', '2024-03-12 10:40:00'),
(15, 'DELIVERED',  'BlueDart',     'TRK1015', '2024-03-13 09:30:00', '2024-03-15 14:10:00'),

(16, 'DELIVERED',  'Delhivery',    'TRK1016', '2024-03-16 11:00:00', '2024-03-18 17:20:00'),
(17, 'SHIPPED',    'Ekart',        'TRK1017', '2024-03-19 13:00:00', NULL),
(18, 'DELIVERED',  'BlueDart',     'TRK1018', '2024-03-22 10:00:00', '2024-03-24 16:45:00'),
(19, 'DELIVERED',  'India Post',   'TRK1019', '2024-03-26 08:00:00', '2024-03-28 12:00:00'),
(20, 'DELIVERED',  'Delhivery',    'TRK1020', '2024-03-29 09:10:00', '2024-03-31 14:30:00');


INSERT INTO reviews (
    user_id,
    product_id,
    rating,
    review_text,
    created_at
) VALUES
(1, 1, 5, 'Excellent phone, very smooth performance.', '2024-01-10 12:00:00'),
(2, 3, 4, 'Good value for money.', '2024-01-12 15:30:00'),
(3, 2, 4, 'Great display but battery could be better.', '2024-01-15 18:45:00'),
(4, 13, 2, 'Fabric quality not as expected.', '2024-01-18 11:20:00'),
(5, 5, 5, 'Laptop performance is amazing.', '2024-01-20 09:10:00'),

(6, 20, 4, 'Very useful kitchen appliance.', '2024-02-06 14:25:00'),
(7, 9, 5, 'Sound quality is excellent for the price.', '2024-02-08 17:10:00'),
(8, 21, 3, 'Looks good but average quality.', '2024-02-10 19:00:00'),
(9, 19, 4, 'Sturdy and well built.', '2024-02-13 08:45:00'),
(10,15, 2, 'Not comfortable to wear.', '2024-02-16 21:30:00'),

(1, 4, 5, 'Super fast and lightweight.', '2024-03-03 10:20:00'),
(2,11, 4, 'Nice fit and comfortable.', '2024-03-05 13:40:00'),
(3,18, 5, 'Chair is very ergonomic.', '2024-03-08 16:10:00'),
(4,23, 4, 'Moisturizer works really well.', '2024-03-11 09:00:00'),
(5, 7, 5, 'Picture quality is outstanding.', '2024-03-14 18:25:00'),

(6, 9, 4, 'Good product overall.', '2024-03-17 20:15:00'),
(7,21, 3, 'Decent but expected more.', '2024-03-20 11:50:00'),
(8,29, 5, 'Treadmill is perfect for home workouts.', '2024-03-23 15:30:00'),
(9,32, 4, 'Ball quality is good.', '2024-03-27 17:05:00'),
(10,27,3, 'Average product, okay for the price.', '2024-03-30 22:00:00');
