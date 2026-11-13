package com.demo.shop.util;

import com.demo.shop.model.Order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Clean, efficient helpers (control code: nothing here should be flagged). */
public final class OrderStats {

    private OrderStats() {
    }

    public static BigDecimal totalAmount(List<Order> orders) {
        BigDecimal total = BigDecimal.ZERO;
        for (Order order : orders) {
            total = total.add(order.getAmount());
        }
        return total;
    }

    public static Map<String, Integer> ordersPerCustomer(List<Order> orders) {
        Map<String, Integer> counts = new HashMap<>(orders.size());
        for (Order order : orders) {
            counts.merge(order.getCustomerId(), 1, Integer::sum);
        }
        return counts;
    }

    public static String joinIds(List<Order> orders) {
        StringBuilder sb = new StringBuilder(orders.size() * 12);
        for (Order order : orders) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(order.getId());
        }
        return sb.toString();
    }
}
