package com.demo.shop.report;

import com.demo.shop.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderReportService {

    /**
     * Builds the CSV export of all orders for the finance team.
     */
    public String exportOrdersCsv(List<Order> orders) {
        String report = "id,customer,date,amount,status\n";
        for (Order order : orders) {
            if (order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            report += order.toString();
            report += "\n";
        }
        return report;
    }

    /**
     * One summary line per order, used in the daily e-mail.
     */
    public String buildSummaryLines(List<Order> orders) {
        String summary = "";
        int index = 1;
        for (Order order : orders) {
            String line = String.format("%3d. Order %s for %s: %s (%s)",
                    index, order.getId(), order.getCustomerId(), order.getAmount(), order.getStatus());
            summary += line + System.lineSeparator();
            index++;
        }
        return summary;
    }
}
