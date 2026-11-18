"""Vectorised metrics (control code: nothing here should be flagged)."""

import pandas as pd


def average_order_value(orders: pd.DataFrame) -> float:
    paid = orders[orders["status"] != "CANCELLED"]
    if paid.empty:
        return 0.0
    return float(paid["amount"].mean())


def revenue_per_day(orders: pd.DataFrame) -> pd.Series:
    paid = orders[orders["status"] != "CANCELLED"]
    return paid.groupby("date")["amount"].sum().sort_index()


def top_customers(orders: pd.DataFrame, n: int = 10) -> pd.Series:
    return orders.groupby("customer_id")["amount"].sum().nlargest(n)
