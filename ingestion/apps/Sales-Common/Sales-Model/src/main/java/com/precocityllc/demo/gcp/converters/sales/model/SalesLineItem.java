package com.precocityllc.demo.gcp.converters.sales.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class SalesLineItem {
    private int lineItemId;
    private int orderId;
    private int itemId;
    private String transactionType;
    private int quantity;
    private BigDecimal itemPriceEach;
    private BigDecimal itemTaxEach;
    private BigDecimal markdownEach;
    private BigDecimal freightEach;
    private BigDecimal dutyEach;
    private int giftWrapFlag;
    private String itemStatus;
    private String itemStatusAsOfDate;
    private String shipDate;
    private String promotionCode;
    private String giftMessage;
    private String giftRegistryNumber;
    private String createdTimestamp;
    private String updatedTimestamp;

    public SalesLineItem() {
    }

    public SalesLineItem(int lineItemId, int orderId, int itemId, String transactionType, int quantity, BigDecimal itemPriceEach, BigDecimal itemTaxEach, BigDecimal markdownEach, BigDecimal freightEach, BigDecimal dutyEach, int giftWrapFlag, String itemStatus, String itemStatusAsOfDate, String shipDate, String promotionCode, String giftMessage, String giftRegistryNumber, String createdTimestamp, String updatedTimestamp) {
        this.lineItemId = lineItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.itemPriceEach = itemPriceEach;
        this.itemTaxEach = itemTaxEach;
        this.markdownEach = markdownEach;
        this.freightEach = freightEach;
        this.dutyEach = dutyEach;
        this.giftWrapFlag = giftWrapFlag;
        this.itemStatus = itemStatus;
        this.itemStatusAsOfDate = itemStatusAsOfDate;
        this.shipDate = shipDate;
        this.promotionCode = promotionCode;
        this.giftMessage = giftMessage;
        this.giftRegistryNumber = giftRegistryNumber;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }
    @JsonProperty("line_item_id")
    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }
    @JsonProperty("order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    @JsonProperty("item_id")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    @JsonProperty("transaction_type")
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @JsonProperty("item_price_each")
    public BigDecimal getItemPriceEach() {
        return itemPriceEach;
    }

    public void setItemPriceEach(BigDecimal itemPriceEach) {
        this.itemPriceEach = itemPriceEach;
    }
    @JsonProperty("item_tax_each")
    public BigDecimal getItemTaxEach() {
        return itemTaxEach;
    }

    public void setItemTaxEach(BigDecimal itemTaxEach) {
        this.itemTaxEach = itemTaxEach;
    }
    @JsonProperty("markdown_each")
    public BigDecimal getMarkdownEach() {
        return markdownEach;
    }

    public void setMarkdownEach(BigDecimal markdownEach) {
        this.markdownEach = markdownEach;
    }
    @JsonProperty("freight_each")
    public BigDecimal getFreightEach() {
        return freightEach;
    }

    public void setFreightEach(BigDecimal freightEach) {
        this.freightEach = freightEach;
    }
    @JsonProperty("duty_each")
    public BigDecimal getDutyEach() {
        return dutyEach;
    }

    public void setDutyEach(BigDecimal dutyEach) {
        this.dutyEach = dutyEach;
    }
    @JsonProperty("gift_wrap_flag")
    public int getGiftWrapFlag() {
        return giftWrapFlag;
    }

    public void setGiftWrapFlag(int giftWrapFlag) {
        this.giftWrapFlag = giftWrapFlag;
    }
    @JsonProperty("item_status")
    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
    @JsonProperty("item_status_as_of_date")
    public String getItemStatusAsOfDate() {
        return itemStatusAsOfDate;
    }

    public void setItemStatusAsOfDate(String itemStatusAsOfDate) {
        this.itemStatusAsOfDate = itemStatusAsOfDate;
    }
    @JsonProperty("ship_date")
    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }
    @JsonProperty("promotion_code")
    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }
    @JsonProperty("gift_message")
    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }
    @JsonProperty("gift_registry_number")
    public String getGiftRegistryNumber() {
        return giftRegistryNumber;
    }

    public void setGiftRegistryNumber(String giftRegistryNumber) {
        this.giftRegistryNumber = giftRegistryNumber;
    }
    @JsonProperty("created_timestamp")
    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    @JsonProperty("updated_timestamp")
    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
}
