package com.demo.shop.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventorySync {

    private final Object stockLock = new Object();
    private final Map<String, Integer> stock = new HashMap<>();
    private final InventoryClient client;

    public InventorySync(InventoryClient client) {
        this.client = client;
    }

    /**
     * Blocks until the warehouse reports the product in stock, or gives up after maxAttempts.
     */
    public boolean waitForStock(String productId, int maxAttempts) throws InterruptedException {
        int attempts = 0;
        while (attempts < maxAttempts) {
            if (client.availableQuantity(productId) > 0) {
                return true;
            }
            Thread.sleep(500);
            attempts++;
        }
        return false;
    }

    /**
     * Applies a batch of stock updates received from the warehouse.
     */
    public void applyUpdates(List<String> productIds, List<Integer> quantities) {
        for (int i = 0; i < productIds.size(); i++) {
            synchronized (stockLock) {
                stock.put(productIds.get(i), quantities.get(i));
            }
        }
    }

    public interface InventoryClient {
        int availableQuantity(String productId);
    }
}
