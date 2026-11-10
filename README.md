# Shop Backend (performance-bug demo)

A small, made-up order-processing backend used to **test the Performance Bug Detection System**.

Some commits in this repository **deliberately introduce performance bugs** (I/O inside loops,
string building in loops, linear searches, blocking sleeps and more). Other commits add clean code,
and one commit fixes a bug. The full list of planted bugs and their correct fixes is in
[ANSWER_KEY.md](ANSWER_KEY.md), so the tool's findings and suggested fixes can be checked.

**Do not use this code for anything real.**

## Layout

| Path | Contents |
|---|---|
| `src/main/java/com/demo/shop/` | Java services (orders, customers, inventory, audit) |
| `analytics/` | Python reporting scripts (pandas) |
| `ANSWER_KEY.md` | Planted bugs, where they are, and the expected fix |

## How to test the tool with it

1. Start the Performance Bug Detection app.
2. Analyse `https://github.com/Tarunroyvardhan/perf-bug-demo` with **Maximum Commits = 12**.
3. Compare the flagged functions, explanations and fixes with `ANSWER_KEY.md`.
