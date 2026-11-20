# Answer key – planted performance bugs

Each bug below was put into the code on purpose. The table lists where it is, what is wrong, and the
fix a correct tool should suggest. Use it to score the Performance Bug Detection System's output.

| # | File | Function | Commit | Bug (pattern) | Expected fix |
|---|---|---|---|---|---|
| 1 | `report/OrderReportService.java` | `exportOrdersCsv` | Add CSV export and daily summary for orders | `report += order.toString()` builds a String in a loop – every iteration copies the whole report (O(n²)) | Use a `StringBuilder` (or `StringJoiner`) and call `toString()` once |
| 2 | `report/OrderReportService.java` | `buildSummaryLines` | same | `String.format(...)` and `summary += line` inside the loop | Append to a `StringBuilder`; format with `sb.append(...)` or reuse a formatter |
| 3 | `customer/LoyaltyService.java` | `ordersFromLoyaltyMembers` | Add loyalty programme order matching and invite list | `loyaltyMemberIds.contains(...)` on a `List` inside the loop – linear search per order (O(n·m)) | Copy the IDs into a `HashSet` once and look up in the set. **Fixed in a later commit** ("Fix slow loyalty order matching by using a HashSet") – the fixed version should be scored clean |
| 4 | `customer/LoyaltyService.java` | `customersToInvite` | same | `loyaltyMemberIds.contains(...)` and `invite.indexOf(...)` on lists inside the loop | Use a `HashSet` for members and a `LinkedHashSet` for the invite list |
| 5 | `audit/AuditLogger.java` | `logTransactions` | Write an audit entry for every transaction | `new FileWriter(auditFile, true)` opened and closed for every transaction (I/O in a loop) | Open one `BufferedWriter` before the loop (try-with-resources) and write all lines |
| 6 | `audit/AuditLogger.java` | `countAlreadyLogged` | same | Re-reads the whole audit file for every transaction (I/O in a loop, O(n·m)) | Read the file once into a `HashSet` of IDs, then check each transaction against the set |
| 7 | `inventory/InventorySync.java` | `waitForStock` | Poll warehouse for stock and apply inventory updates | `Thread.sleep(500)` polling loop blocks the thread | Use a callback/event, a `ScheduledExecutorService`, or a `CompletableFuture` instead of sleeping |
| 8 | `inventory/InventorySync.java` | `applyUpdates` | same | `synchronized (stockLock)` taken on every iteration | Take the lock once around the whole batch (or use a `ConcurrentHashMap` / `putAll`) |
| 9 | `customer/CustomerImporter.java` | `validEmails` | Validate e-mails and parse rows in customer import | `Pattern.compile(...)` inside the loop recompiles the regex for every e-mail | `private static final Pattern EMAIL = Pattern.compile(...)` and reuse it |
| 10 | `customer/CustomerImporter.java` | `parseRows` | same | `replaceAll(...)` (compiles a regex) inside a nested loop; `new ArrayList` per line | Precompile the whitespace `Pattern` once; pre-size or reuse the lists |
| 11 | `analytics/sales_report.py` | `revenue_by_customer` | Add daily sales report script | `DataFrame.iterrows()` – row-by-row Python loop | Vectorise: `orders[orders.status != "CANCELLED"].groupby("customer_id")["amount"].sum().to_dict()` |
| 12 | `analytics/sales_report.py` | `combine_daily_files` | same | `pd.concat` inside the loop re-copies the growing table (O(n²)) | Collect the daily frames in a list and call `pd.concat` once after the loop |
| 13 | `analytics/sales_report.py` | `load_discount_codes` | same | Opens and parses the same JSON file for every order (I/O in a loop) | Load the JSON once before the loop |

## Clean code (should not need changes)

| File | Functions | Why it is clean |
|---|---|---|
| `util/OrderStats.java` | `totalAmount`, `ordersPerCustomer`, `joinIds` | Single pass, `HashMap.merge`, pre-sized `StringBuilder` |
| `analytics/clean_metrics.py` | `average_order_value`, `revenue_per_day`, `top_customers` | Vectorised pandas operations |
| `customer/LoyaltyService.java` (latest version) | `ordersFromLoyaltyMembers` | Uses a `HashSet` after the fix commit |
| `model/Order.java`, `model/Transaction.java` | constructors, getters | Plain data classes |

A good result: every function in the first table is flagged with a matching explanation and fix,
and the functions in the second table are either not flagged or marked *No evidence / no change needed*.
