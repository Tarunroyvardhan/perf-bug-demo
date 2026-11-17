"""Daily sales reporting for the finance team."""

import json

import pandas as pd


def revenue_by_customer(orders: pd.DataFrame) -> dict:
    """Total order amount per customer, skipping cancelled orders."""
    totals = {}
    for _, row in orders.iterrows():
        if row["status"] == "CANCELLED":
            continue
        totals[row["customer_id"]] = totals.get(row["customer_id"], 0.0) + row["amount"]
    return totals


def combine_daily_files(paths: list) -> pd.DataFrame:
    """Loads one CSV per day and stacks them into a single table."""
    combined = pd.DataFrame()
    for path in paths:
        day = pd.read_csv(path)
        day["source_file"] = path
        combined = pd.concat([combined, day], ignore_index=True)
    return combined


def load_discount_codes(order_ids: list, codes_file: str) -> dict:
    """Looks up the discount code used by each order."""
    result = {}
    for order_id in order_ids:
        with open(codes_file) as f:
            codes = json.load(f)
        result[order_id] = codes.get(order_id)
    return result
