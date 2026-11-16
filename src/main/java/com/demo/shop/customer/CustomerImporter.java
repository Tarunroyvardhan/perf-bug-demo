package com.demo.shop.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerImporter {

    /**
     * Keeps only the rows whose e-mail address is valid.
     */
    public List<String> validEmails(List<String> emails) {
        List<String> valid = new ArrayList<>();
        for (String email : emails) {
            Pattern pattern = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$");
            if (pattern.matcher(email.trim()).matches()) {
                valid.add(email.trim().toLowerCase());
            }
        }
        return valid;
    }

    /**
     * Splits each "name;email;phone" line into its fields.
     */
    public List<List<String>> parseRows(List<String> lines) {
        List<List<String>> rows = new ArrayList<>();
        for (String line : lines) {
            List<String> fields = new ArrayList<>();
            for (String part : line.split(";")) {
                fields.add(part.replaceAll("\\s+", " ").trim());
            }
            rows.add(fields);
        }
        return rows;
    }
}
