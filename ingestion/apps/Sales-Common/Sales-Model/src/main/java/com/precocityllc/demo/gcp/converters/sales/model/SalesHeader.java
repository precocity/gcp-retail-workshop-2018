package com.precocityllc.demo.gcp.converters.sales.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesHeader {
    //order_id,channel_id,customer_id,primary_associate_id,secondary_associate_id,total_amount,store_id,pos_terminal_id,transaction_date,transaction_number,merchandise_amount,tax_amount,payment_type,created_timestamp,updated_timestamp

    private int orderId;
    private int channelId;
    private Integer customerId;
    private Integer primaryAssociateId;
    private Integer secondaryAssociateId;
    private BigDecimal totalAmount;
    private Integer storeId;
    private Integer posTerminalId;
    private String transactionDate;
    private String transactionNumber;
    private BigDecimal merchandiseAmount;
    private BigDecimal taxAmount;
    private String paymentType;
    private String createdTimestamp;
    private String updatedTimestamp;

    public SalesHeader() {
    }

    public SalesHeader(int orderId,
                       int channelId,
                       Integer customerId,
                       Integer primaryAssociateId,
                       Integer secondaryAssociateId,
                       BigDecimal totalAmount,
                       Integer storeId,
                       Integer posTerminalId,
                       String transactionDate,
                       String transactionNumber,
                       BigDecimal merchandiseAmount,
                       BigDecimal taxAmount,
                       String paymentType,
                       String createdTimestamp,
                       String updatedTimestamp) {
        this.orderId = orderId;
        this.channelId = channelId;
        this.customerId = customerId;
        this.primaryAssociateId = primaryAssociateId;
        this.secondaryAssociateId = secondaryAssociateId;
        this.totalAmount = totalAmount;
        this.storeId = storeId;
        this.posTerminalId = posTerminalId;
        this.transactionDate = transactionDate;
        this.transactionNumber = transactionNumber;
        this.merchandiseAmount = merchandiseAmount;
        this.taxAmount = taxAmount;
        this.paymentType = paymentType;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    @JsonProperty("order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("channel_id")
    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @JsonProperty("customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("primary_associate_id")
    public Integer getPrimaryAssociateId() {
        return primaryAssociateId;
    }

    public void setPrimaryAssociateId(Integer primaryAssociateId) {
        this.primaryAssociateId = primaryAssociateId;
    }

    @JsonProperty("secondary_associate_id")
    public Integer getSecondaryAssociateId() {
        return secondaryAssociateId;
    }

    public void setSecondaryAssociateId(Integer secondaryAssociateId) {
        this.secondaryAssociateId = secondaryAssociateId;
    }

    @JsonProperty("total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("store_id")
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @JsonProperty("pos_terminal_id")
    public Integer getPosTerminalId() {
        return posTerminalId;
    }

    public void setPosTerminalId(Integer posTerminalId) {
        this.posTerminalId = posTerminalId;
    }

    @JsonProperty("transaction_date")
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @JsonProperty("transaction_number")
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @JsonProperty("merchandise_amount")
    public BigDecimal getMerchandiseAmount() {
        return merchandiseAmount;
    }

    public void setMerchandiseAmount(BigDecimal merchandiseAmount) {
        this.merchandiseAmount = merchandiseAmount;
    }

    @JsonProperty("tax_amount")
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    @JsonProperty("peyment_type")
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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
