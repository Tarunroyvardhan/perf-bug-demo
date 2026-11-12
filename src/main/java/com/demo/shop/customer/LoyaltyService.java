package com.demo.shop.customer;

import com.demo.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class LoyaltyService {

    private final List<String> loyaltyMemberIds;

    public LoyaltyService(List<String> loyaltyMemberIds) {
        this.loyaltyMemberIds = loyaltyMemberIds;
    }

    /**
     * Returns the orders placed by loyalty-programme members, so they can earn points.
     */
    public List<Order> ordersFromLoyaltyMembers(List<Order> orders) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (loyaltyMemberIds.contains(order.getCustomerId())) {
                result.add(order);
            }
        }
        return result;
    }

    /**
     * Customers who placed an order but are not yet members, for the sign-up campaign.
     */
    public List<String> customersToInvite(List<Order> orders) {
        List<String> invite = new ArrayList<>();
        for (Order order : orders) {
            String customer = order.getCustomerId();
            if (!loyaltyMemberIds.contains(customer) && invite.indexOf(customer) < 0) {
                invite.add(customer);
            }
        }
        return invite;
    }
}
