package com.precocityllc.demo.gcp.converters.sales.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The sales record is the combination of sales header data and sales line item data. It may be viewed as complete order.
 */
public class SalesRecord extends SalesHeader {
    private List<SalesLineItem> lineItems = new ArrayList<>();

    public SalesRecord() {
    }

    public SalesRecord(SalesHeader header) {
        super(header.getOrderId()
                , header.getChannelId()
                , header.getCustomerId()
                , header.getPrimaryAssociateId()
                , header.getSecondaryAssociateId()
                , header.getTotalAmount()
                , header.getStoreId()
                , header.getPosTerminalId()
                , header.getTransactionDate()
                , header.getTransactionNumber()
                , header.getMerchandiseAmount()
                , header.getTaxAmount()
                , header.getPaymentType()
                , header.getCreatedTimestamp()
                , header.getUpdatedTimestamp());
    }

    public List<SalesLineItem> getLineItems() {
        return lineItems;
    }

}
