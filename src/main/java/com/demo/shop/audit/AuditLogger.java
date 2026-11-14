package com.demo.shop.audit;

import com.demo.shop.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AuditLogger {

    private final String auditFile;

    public AuditLogger(String auditFile) {
        this.auditFile = auditFile;
    }

    /**
     * Appends one audit line per transaction.
     */
    public void logTransactions(List<Transaction> transactions) throws IOException {
        for (Transaction tx : transactions) {
            FileWriter writer = new FileWriter(auditFile, true);
            writer.write(tx.getTimestamp() + " " + tx.getId() + " " + tx.getOrderId() + " " + tx.getAmount());
            writer.write(System.lineSeparator());
            writer.close();
        }
    }

    /**
     * Checks which transactions are already in the audit file.
     */
    public int countAlreadyLogged(List<Transaction> transactions) throws IOException {
        int found = 0;
        for (Transaction tx : transactions) {
            try (BufferedReader reader = new BufferedReader(new FileReader(auditFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(tx.getId())) {
                        found++;
                        break;
                    }
                }
            }
        }
        return found;
    }
}
