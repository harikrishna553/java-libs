-- ============================================================
-- E-COMMERCE ANALYTICS QUERIES (READ-ONLY)
-- Designed for Apache Superset Dashboards
-- ============================================================

-- ------------------------------------------------------------
-- 1. DAILY REVENUE TREND
-- What: Total revenue per day using order final amount
-- Why: Helps track growth and seasonality
-- Charts: Time-series Line Chart / Area Chart
-- ------------------------------------------------------------
SELECT
    DATE(order_date) AS order_day,
    SUM(final_amount) AS total_revenue
FROM orders
WHERE order_status != 'CANCELLED'
GROUP BY 1
ORDER BY 1;


-- ------------------------------------------------------------
-- 2. MONTHLY REVENUE TREND
-- What: Revenue aggregated by month
-- Why: High-level business performance view
-- Charts: Bar Chart / Line Chart
-- ------------------------------------------------------------
SELECT
    DATE_TRUNC('month', order_date) AS month,
    SUM(final_amount) AS total_revenue
FROM orders
WHERE order_status != 'CANCELLED'
GROUP BY 1
ORDER BY 1;


-- ------------------------------------------------------------
-- 3. TOP 10 PRODUCTS BY REVENUE
-- What: Best-performing products by sales value
-- Why: Identify top revenue drivers
-- Charts: Horizontal Bar Chart
-- ------------------------------------------------------------
SELECT
    p.product_name,
    SUM(oi.total_price) AS revenue
FROM order_items oi
JOIN products p ON oi.product_id = p.product_id
JOIN orders o ON oi.order_id = o.order_id
WHERE o.order_status != 'CANCELLED'
GROUP BY 1
ORDER BY revenue DESC
LIMIT 10;


-- ------------------------------------------------------------
-- 4. CATEGORY PERFORMANCE
-- What: Revenue aggregated at category level
-- Why: Understand which categories drive business
-- Charts: Bar Chart / Treemap / Pie Chart
-- ------------------------------------------------------------
SELECT
    c.category_name,
    SUM(oi.total_price) AS revenue
FROM order_items oi
JOIN products p ON oi.product_id = p.product_id
JOIN categories c ON p.category_id = c.category_id
JOIN orders o ON oi.order_id = o.order_id
WHERE o.order_status != 'CANCELLED'
GROUP BY 1
ORDER BY revenue DESC;


-- ------------------------------------------------------------
-- 5. REVENUE BY CITY
-- What: Geographic distribution of revenue
-- Why: Identify strong markets
-- Charts: Bar Chart / Map Chart
-- ------------------------------------------------------------
SELECT
    u.city,
    SUM(o.final_amount) AS revenue
FROM orders o
JOIN users u ON o.user_id = u.user_id
WHERE o.order_status != 'CANCELLED'
GROUP BY 1
ORDER BY revenue DESC;


-- ------------------------------------------------------------
-- 6. CUSTOMER ORDER FREQUENCY
-- What: Number of orders per user
-- Why: Identify repeat vs one-time customers
-- Charts: Histogram / Bar Chart
-- ------------------------------------------------------------
SELECT
    u.user_id,
    COUNT(o.order_id) AS total_orders
FROM users u
LEFT JOIN orders o ON u.user_id = o.user_id
GROUP BY 1;


-- ------------------------------------------------------------
-- 7. PAYMENT SUCCESS RATE
-- What: Success vs failure count
-- Why: Measure payment reliability
-- Charts: Pie Chart / Donut Chart
-- ------------------------------------------------------------
SELECT
    payment_status,
    COUNT(*) AS total_transactions
FROM payments
GROUP BY 1;


-- ------------------------------------------------------------
-- 8. PAYMENT METHOD DISTRIBUTION
-- What: Usage of different payment methods
-- Why: Understand customer preferences
-- Charts: Pie Chart / Bar Chart
-- ------------------------------------------------------------
SELECT
    payment_method,
    COUNT(*) AS usage_count
FROM payments
WHERE payment_status = 'SUCCESS'
GROUP BY 1
ORDER BY usage_count DESC;


-- ------------------------------------------------------------
-- 9. ORDER STATUS DISTRIBUTION
-- What: Count of orders by status
-- Why: Operational health (delivered vs cancelled)
-- Charts: Pie Chart / Bar Chart
-- ------------------------------------------------------------
SELECT
    order_status,
    COUNT(*) AS total_orders
FROM orders
GROUP BY 1;


-- ------------------------------------------------------------
-- 10. AVERAGE DELIVERY TIME
-- What: Avg days between shipped and delivered
-- Why: Logistics performance tracking
-- Charts: KPI / Big Number / Bar Chart (by courier)
-- ------------------------------------------------------------
SELECT
    courier_name,
    AVG(delivered_date - shipped_date) AS avg_delivery_time
FROM shipments
WHERE delivered_date IS NOT NULL
GROUP BY 1;


-- ------------------------------------------------------------
-- 11. TOP CUSTOMERS BY SPENDING
-- What: Users who spent the most
-- Why: Identify high-value customers
-- Charts: Bar Chart
-- ------------------------------------------------------------
SELECT
    u.first_name || ' ' || u.last_name AS customer_name,
    SUM(o.final_amount) AS total_spent
FROM users u
JOIN orders o ON u.user_id = o.user_id
WHERE o.order_status != 'CANCELLED'
GROUP BY 1
ORDER BY total_spent DESC
LIMIT 10;


-- ------------------------------------------------------------
-- 12. AVERAGE ORDER VALUE (AOV)
-- What: Avg revenue per order
-- Why: Core business KPI
-- Charts: Big Number / KPI
-- ------------------------------------------------------------
SELECT
    AVG(final_amount) AS avg_order_value
FROM orders
WHERE order_status != 'CANCELLED';


-- ------------------------------------------------------------
-- 13. PRODUCT RATING ANALYSIS
-- What: Avg rating per product
-- Why: Product quality insights
-- Charts: Bar Chart
-- ------------------------------------------------------------
SELECT
    p.product_name,
    AVG(r.rating) AS avg_rating
FROM reviews r
JOIN products p ON r.product_id = p.product_id
GROUP BY 1
ORDER BY avg_rating DESC;


-- ------------------------------------------------------------
-- 14. PROFIT ANALYSIS BY PRODUCT
-- What: Revenue - Cost
-- Why: Identify profitable products
-- Charts: Bar Chart
-- ------------------------------------------------------------
SELECT
    p.product_name,
    SUM(oi.total_price) AS revenue,
    SUM(oi.quantity * p.cost_price) AS cost,
    SUM(oi.total_price - (oi.quantity * p.cost_price)) AS profit
FROM order_items oi
JOIN products p ON oi.product_id = p.product_id
JOIN orders o ON oi.order_id = o.order_id
WHERE o.order_status != 'CANCELLED'
GROUP BY 1
ORDER BY profit DESC;


-- ------------------------------------------------------------
-- 15. ORDER TO DELIVERY FUNNEL
-- What: Funnel stages count
-- Why: Conversion/drop-off analysis
-- Charts: Funnel Chart
-- ------------------------------------------------------------
SELECT 'Orders Created' AS stage, COUNT(*) FROM orders
UNION ALL
SELECT 'Payments Success', COUNT(*) FROM payments WHERE payment_status = 'SUCCESS'
UNION ALL
SELECT 'Shipped', COUNT(*) FROM shipments WHERE shipment_status IN ('SHIPPED','IN_TRANSIT','DELIVERED')
UNION ALL
SELECT 'Delivered', COUNT(*) FROM shipments WHERE shipment_status = 'DELIVERED';