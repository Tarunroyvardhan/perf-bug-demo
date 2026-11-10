package com.demo.shop.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction {

    private final String id;
    private final String orderId;
    private final Instant timestamp;
    private final BigDecimal amount;

    public Transaction(String id, String orderId, Instant timestamp, BigDecimal amount) {
        this.id = id;
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
