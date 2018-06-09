
-- cust_address as (
--   SELECT addr.customer_id, ARRAY_AGG(STRUCT(addr.address_line_1, addr.address_line_2, addr.city, addr.state, addr.postal_code, addr.country)) as customer_addresses
--   FROM `retail_demo_warehouse.customer_address` addr
--   GROUP BY 1
-- ),

-- cust_loyalty as (
--     SELECT l.customer_id, ARRAY_AGG(STRUCT(l.loyalty_number, l.loyalty_join_date, l.loyalty_lifetime_points, l.loyalty_available_points, 
--     l.loyalty_level_description, l.loyalty_level_expiration)) as customer_loyalty
--     FROM `retail_demo_warehouse.customer_loyalty` l 
--     GROUP BY 1
-- ),

CREATE TEMP FUNCTION ltv(cust_transactions ARRAY<STRUCT<order_id INT64, channel_id INT64, total_amount FLOAT64, store_id INT64, transaction_date STRING>>, contribution_margin FLOAT64, retention_rate FLOAT64,discount_rate FLOAT64,retention_costs FLOAT64,time_horizon_years FLOAT64)
RETURNS FLOAT64
LANGUAGE js AS """
  var gross_contribution = 0.0;
  for(var i = 0; i < cust_transactions.length; i++) {
    gross_contribution = gross_contribution + contribution_margin*cust_transactions[i].total_amount;
  }
  
  var horizon_factor_contribution = 0.0;
  var horizon_factor_retention = 0.0
  for(var j = 1; j <= time_horizon_years; j++) {
    horizon_factor_contribution = horizon_factor_contribution + Math.pow(retention_rate, j)/Math.pow(1 + discount_rate, j);
    horizon_factor_retention = horizon_factor_retention + Math.pow(retention_rate, j - 1)/Math.pow(1 + discount_rate, j - 0.5);
  }
  
  return (gross_contribution * horizon_factor_contribution) - (retention_costs * horizon_factor_retention)
""";
with cust_segments as (
  SELECT segment.customer_id, ARRAY_AGG(STRUCT(segment.customer_segment_name as segment_name, segment.customer_segment_value as segment_value)) as customer_segments
  FROM `retail_demo_warehouse.customer_segment` segment
  GROUP BY 1
),
cust_prefs as (
  SELECT prefs.customer_id, ARRAY_AGG(STRUCT(prefs.customer_preference_type as preference_type, prefs.customer_preference_value)) as customer_preferences
  FROM `retail_demo_warehouse.customer_preferences`  prefs
  GROUP BY 1
),
cust_faves as (
    SELECT fave.customer_id, ARRAY_AGG(STRUCT(vendor.vendor_name, fave.category_id, fave.item_id, fave.source)) as customer_favorites
    FROM `retail_demo_warehouse.customer_favorites` fave
    LEFT JOIN `retail_demo_warehouse.vendor` vendor
    ON fave.vendor_id = vendor.vendor_id
    GROUP BY 1
),
cust_rels as (
  SELECT rel.customer_id, ARRAY_AGG(STRUCT(rel.customer_preference_value as  relationship_type, rel.customer_preference_type as related_customer_id)) as relationships
  FROM `retail_demo_warehouse.customer_relationship` rel
  GROUP BY 1
),
cust_sales as (
  SELECT sales.customer_id, 
  MAX(sales.transaction_date) as last_purchase_date,
  MAX(CASE WHEN sales.channel_id = 3 THEN sales.transaction_date ELSE NULL END) as last_purchase_date_store,
  MAX(CASE WHEN sales.channel_id = 1 THEN sales.transaction_date ELSE NULL END) as last_purchase_date_online,
  AVG(sales.total_amount) as customer_aov,
  ARRAY_AGG(STRUCT(order_id, channel_id, total_amount, store_id, transaction_date)) as transactions
  FROM `retail_demo_warehouse.sales_header` sales
  GROUP BY 1
)
SELECT customer.customer_id,
       customer.customer_number,
       customer.household_id,
       customer.last_name,
       customer.first_name,
       customer.alternate_lastName,
       customer.salutation,
       customer.birth_date,
       CASE WHEN (customer.birth_date IS NULL OR customer.birth_date = '') THEN NULL 
       ELSE 
         DATE_DIFF(CURRENT_DATE(), PARSE_DATE("%Y-%m-%d", customer.birth_date), YEAR) - CASE WHEN (EXTRACT(MONTH FROM PARSE_DATE("%Y-%m-%d", customer.birth_date)) > EXTRACT(MONTH FROM CURRENT_DATE())) 
         OR (EXTRACT(MONTH FROM PARSE_DATE("%Y-%m-%d", customer.birth_date)) = EXTRACT(MONTH FROM CURRENT_DATE())) 
         AND (EXTRACT(DAY FROM PARSE_DATE("%Y-%m-%d", customer.birth_date)) > EXTRACT(DAY FROM CURRENT_DATE())) THEN 1 ELSE 0 END
       END as age,
       customer.gender,
       customer.marital_status,
       customer.anniversary,
       customer.primary_email,
       customer.secondary_email,
       customer.primary_phone,
       customer.primary_phone_type,
       customer.secondary_phone,
       customer.secondary_phone_type,
       customer.employee_flag,
       sales.last_purchase_date,
       sales.last_purchase_date_store,
       sales.last_purchase_date_online,
       sales.transactions as transactions,
       NULL as last_online_visit_date,
       NULL as last_email_open,
       NULL as last_email_click,
       NULL as rfm_score_model_1,
       NULL as rfm_score_model_2,
       CASE WHEN sales.transactions IS NOT NULL THEN ltv(sales.transactions, 0.265, .45, 0.075, 6.50, 5) ELSE 0.0 END as ltv_model_1,
       NULL as ltv_model_2,
       sales.customer_aov,
       NULL as category_propensities,
       cust_segments.customer_segments,
       prefs.customer_preferences,
       faves.customer_favorites,
       rels.relationships 
       --addr.customer_addresses, 
       --loyalty.customer_loyalty
FROM `retail_demo_warehouse.customer` customer 
LEFT JOIN cust_segments
ON customer.customer_id = cust_segments.customer_id
LEFT JOIN cust_sales sales
ON customer.customer_id = sales.customer_id
LEFT JOIN cust_prefs prefs
ON customer.customer_id = prefs.customer_id
LEFT JOIN cust_faves faves
ON customer.customer_id = faves.customer_id
LEFT JOIN cust_rels rels
ON customer.customer_id = rels.customer_id