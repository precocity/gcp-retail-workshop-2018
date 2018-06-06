SELECT
    fullVisitorId AS clientId,
    hits.product.productSku AS contentId,
    --SUM(IF(hits.eCommerceAction.action_type=='1' AND hits.product.isImpression IS NULL, 1, 0)) AS PLPViews,
    --SUM(IF(hits.eCommerceAction.action_type=='2' AND hits.product.isImpression IS NULL, 1, 0)) AS PDPViews,
    --SUM(IF(hits.eCommerceAction.action_type=='3' AND hits.product.isImpression IS NULL, 1, 0)) AS Carts,
    SUM(IF(hits.eCommerceAction.action_type=='6' AND hits.product.isImpression IS NULL, 1, 0)) AS timeOnPage
FROM
    TABLE_DATE_RANGE(
        [bigquery-public-data.google_analytics_sample.ga_sessions_],
        TIMESTAMP('2017-06-01'),
        TIMESTAMP('2017-08-01'))
GROUP BY clientId, contentId
HAVING 
  contentId IS NOT NULL and timeOnPage > 0