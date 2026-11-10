package com.demo.shop.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

    private final String id;
    private final String customerId;
    private final LocalDate date;
    private final BigDecimal amount;
    private final String status;

    public Order(String id, String customerId, LocalDate date, BigDecimal amount, String status) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return id + "," + customerId + "," + date + "," + amount + "," + status;
    }
}
