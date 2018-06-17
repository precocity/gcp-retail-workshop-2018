INSERT `retail_demo_warehouse.store_sales` (store_id, store_name, lat_long, transaction_date, total_sales, updated_timestamp)
SELECT store.store_id,
       store.store_name,
       CONCAT(CAST(store.latitude AS STRING),
       CONCAT(',', CAST(store.longitude AS STRING))) AS lat_long,
       sales.transaction_date, sales.store_sales AS total_sales,
       CURRENT_TIMESTAMP() as updated_timestamp
FROM (
       SELECT transaction_date,
       CAST(store_id AS INT64) AS store_id,
       ROUND(SUM((SELECT SUM(item_price_each * quantity) FROM UNNEST(lineitems))),2) AS store_sales
       FROM `retail_demo_warehouse.sales_events`
       GROUP BY transaction_date, store_id
) sales
JOIN
`retail_demo_warehouse.store` store
ON store.store_id = sales.store_id
;
