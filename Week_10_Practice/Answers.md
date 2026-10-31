# Week 10 - Category A - Part B and Part C

## Part B - Quiz

| Q | Answer | Reason |
|---|---|---|
| 1 | A, B, D, E | C is false: an interface cannot hold per-object state (fields are implicitly `public static final`). |
| 2 | A | Family sharing identity and state is IS-A (inheritance); a capability shared by unrelated types is CAN-DO (interface). |
| 3 | A, C, E | Interfaces have no instance state (B) and no constructors (D). |
| 4 | C | `toString()` gives a meaningful self-description for logging and display. |
| 5 | C | Equal objects must produce equal hash codes. |
| 6 | C | A shallow copy shares the nested object, so both see the change. |
| 7 | C | Elements after the position must shift, which is O(n). |
| 8 | A | Index access is direct address arithmetic, O(1). |
| 9 | B | Binary search needs sorted data. |
| 10 | C | Linear is O(n), binary is O(log n). |

## Part C - Concept Questions

### Question 1: Abstract type vs interface

An **abstract class** models an IS-A family. It can have constructors, instance fields (state), and a mix of abstract and concrete methods, so subclasses share both data and behaviour. A class can extend only one abstract class.

An **interface** models a CAN-DO capability. It has no instance state and no constructors. Its methods are abstract unless marked `default`, `static` or `private`. A class can implement many interfaces, so unrelated classes can share a capability.

Use an abstract class when related types share common state and code (for example `Device` with an ID and on/off state). Use an interface when unrelated types need the same contract (for example `Schedulable` for both lights and fans). Both support abstraction by letting callers depend on the type, not on the concrete class.

### Question 2: The equality contract

If `equals()` is overridden, `hashCode()` must also be overridden so that equal objects return equal hash codes. `equals()` must also be reflexive, symmetric, transitive, consistent, and return false for `null`.

Hash-based collections such as `HashSet` and `HashMap` first use `hashCode()` to pick a bucket, and only then call `equals()` inside that bucket. If two equal objects have different hash codes, they land in different buckets and `equals()` is never compared. The result is duplicates in a `HashSet`, `contains()` returning false for an object that is "in" the set, and `HashMap` lookups failing. In the Customer Registry, without `hashCode()`, `(101, Asha)` from Branch B would be counted as a second customer.

### Question 3: Inner vs static nested classes

An **inner (non-static) class** is tied to an instance of the outer class. It can access the outer object's instance fields and methods directly, but it can only be created through an outer instance (`outer.new Inner()`), and it holds a hidden reference to that outer object.

A **static nested class** is independent of any outer instance. It can access only the outer class's static members (or instance members through an explicit reference), and it is created directly (`new Outer.Nested()`).

Prefer an inner class when the helper really needs the outer object's state, such as an iterator over a collection's internal array. Prefer a static nested class when it is just logically grouped with the outer class, such as a `Builder` or a `Node` in a linked list. It uses less memory and avoids accidentally keeping the outer object alive.

### Question 4: Insert and delete in the middle of a fixed-size array

Both are **O(n)**. Inserting at index i means shifting every element from i to the end one place right to make a gap. Deleting means shifting them one place left to close the gap. In the worst case (index 0) all n elements move. If the array is full, inserting also needs a new, larger array and a full copy, which is O(n) as well. Access by index stays O(1).

Ways to reduce the cost:
- Use a **linked list** when inserts and deletes in the middle are frequent (O(1) once you have the node, but O(n) to find it).
- **Batch** changes and rebuild the array once, instead of shifting for every change.
- If order does not matter, delete by **swapping with the last element** in O(1).
- Use **lazy deletion** (mark as deleted, compact later).
- Use a **dynamic array** with doubling (such as `ArrayList`), so appends at the end are amortised O(1).
- Use a **balanced tree** (such as `TreeMap`) when sorted order plus fast inserts are both needed (O(log n)).

### Question 5: Linear search vs binary search

**Linear search** is O(n). It works on any data, sorted or not, needs no preprocessing, and works on linked lists. For small collections or a single one-off search it is simple and often fast enough.

**Binary search** is O(log n), but the data must be sorted and support index access. Sorting costs O(n log n) once, and keeping data sorted makes inserts O(n).

Choose **linear search** when the data is unsorted, small, changes often, or is only searched once or twice. Choose **binary search** when the data is already sorted or is large and queried many times, so the one-time sort is paid back. For example, 10^6 elements need about 20 comparisons with binary search instead of up to 10^6 with linear search. For range queries on sorted data (Problem 4), binary search with lower and upper bounds is the right choice.
