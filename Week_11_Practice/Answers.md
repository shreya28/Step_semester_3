# Week 11 - Linked List Implementation and Operations - Part B and Part C

## Part B - Quiz

| Q | Answer | Reason |
|---|---|---|
| 1 | B | Save the rest of the list in `newNode.next` before overwriting `node.next`. |
| 2 | C | Floyd's fast and slow pointers use O(1) space. |
| 3 | D | Slow covers half the distance fast does, so it ends near the middle. |
| 4 | C | Only `prev`, `current` and `next` are needed. |
| 5 | D | Existing nodes are relinked; only a dummy node and a tail pointer are used. |
| 6 | C | The dummy removes the special case for a changing head. |
| 7 | B | Insert and remove at the front of a singly linked list are O(1). |
| 8 | C | One pass over each list, and the set holds m nodes. |
| 9 | C | Once `current.next` is overwritten, the rest of the list cannot be reached. |
| 10 | C | Dereferencing `null` throws a `NullPointerException`. |

## Part C - Concept Questions

### Question 1: Singly vs doubly linked lists

A singly linked node stores data and one `next` reference. A doubly linked node also stores `prev`, so each node uses one extra reference (8 bytes on most 64-bit JVMs, or 4 with compressed references).

A singly linked list supports O(1) insert and delete at the head, and after a node you already have. It can only be walked forwards, and deleting a given node needs its predecessor, which may take an O(n) search. A doubly linked list can be walked both ways and can delete a node or insert before it in O(1) when you hold a reference to it.

The cost is more pointer updates per operation (four links instead of two on insert), so there are more chances to leave `prev` and `next` inconsistent.

A doubly linked list is worth it when you need backward traversal or O(1) removal of arbitrary nodes. Examples are an LRU cache (a hash map pointing into a doubly linked list), browser back and forward history, a text editor's undo and redo, and a deque that adds and removes at both ends.

### Question 2: Cycle detection, two pointers vs hash set

A **hash set** of visited nodes takes O(n) time and O(n) space: stop when a node is seen twice.

**Floyd's fast and slow pointers** also take O(n) time but only O(1) space.

Why they must meet: once both pointers are inside a cycle of length L, fast gains exactly one node on slow at every step (it moves 2, slow moves 1). The gap between them therefore shrinks by 1 each step, modulo L. A gap that goes down by one each step must reach 0 within at most L steps, so fast cannot "jump over" slow. If there is no cycle, fast simply reaches `null`.

### Question 3: Merging sorted linked lists in O(1) space

Create a dummy node and a `tail` pointer. While both lists have nodes, compare their heads, attach the smaller one to `tail.next`, advance that list, and move `tail` forward. When one list runs out, attach the remainder of the other in a single step. Return `dummy.next`.

No new nodes are allocated: only the `next` references of existing nodes change, so extra space is O(1). Merging two arrays is also O(m + n) time, but an array cannot be "relinked". The result needs a new array of size m + n, which is O(m + n) extra memory. For large data, avoiding that allocation (and the copying and garbage-collection work that comes with it) is a real memory saving, even though the Big-O time is the same.

### Question 4: Dummy (sentinel) node

A dummy node sits before the real head, so every real node, including the head, has a predecessor. Insert and delete can then always be written as "change `prev.next`", and the new head is simply `dummy.next` at the end.

Example: removing every node with value `x`. Without a dummy you first need a separate loop like `while (head != null && head.data == x) head = head.next;`, then a second loop for the rest of the list. With a dummy, one loop handles all cases:

```java
Node dummy = new Node(0);
dummy.next = head;
Node prev = dummy;
while (prev.next != null) {
    if (prev.next.data == x) prev.next = prev.next.next;
    else prev = prev.next;
}
return dummy.next;
```

The same idea makes `removeNthFromEnd` work when the head itself is removed.

### Question 5: Common null-reference errors

1. **Looking one or two nodes ahead without checking.** For example, `fast.next.next` when `fast` or `fast.next` is `null`, or `cur.next.data` on the last node. Cause: the loop condition doesn't guarantee the reference being dereferenced exists. Fix: put every reference you dereference in the loop condition (`while (fast != null && fast.next != null)`), and check `node.next != null` before using `node.next.data`.

2. **Operating on an empty list or losing the head.** For example, calling `head.next` when `head == null`, or deleting the head and still using the old reference. Cause: the empty and single-node cases weren't handled, or the head changed. Fix: handle `head == null` at the start, use a dummy node so head changes go through `dummy.next`, and always return the new head from methods that modify the list.

In general: test the empty, one-node and two-node lists, and save `next` before changing links.

### Question 6: prev, current and next in iterative reversal

- `current` is the node being processed.
- `prev` is the head of the part already reversed (starts as `null`).
- `next` temporarily saves `current.next` before it is overwritten.

Each step is: `next = current.next; current.next = prev; prev = current; current = next;`. When `current` becomes `null`, `prev` is the new head.

If `next` is not saved first, then after `current.next = prev` the only reference to the rest of the list is gone. The loop can't move forward (or moves backwards into the reversed part), and every node after `current` becomes unreachable and is lost. In Java it would be garbage collected; in C it would be leaked.

### Question 7: "Inserting in the middle of a linked list is O(1)"

It is only true when you **already hold a reference** to the node you are inserting after (or before, in a doubly linked list). The insertion itself is two pointer changes with no shifting.

It is misleading because you usually have an index or a value, not a reference. Finding the position takes an O(n) walk, so "insert at index i" is O(n) overall, the same as an array. Arrays also have to shift elements (O(n)), but that shift is a fast, cache-friendly memory move, while walking a list jumps around memory. In practice an `ArrayList` insert is often faster than a `LinkedList` insert at the same index. The O(1) advantage only appears when you insert repeatedly at a position you already hold, such as while iterating with a list iterator.

### Question 8: Circular linked lists

In a circular linked list, the last node's `next` points back to the head instead of `null`, so there is no natural end.

A real-world use is round-robin scheduling: an operating system cycles through ready processes, giving each a time slice and then moving to `next`, wrapping around forever. Other examples are a multiplayer game's turn order and a music playlist on repeat.

The common mistake is using `while (current != null)` to traverse it. Since no node's `next` is ever `null`, the condition is always true and the loop never ends. Instead, stop when you come back to the start, for example with a `do { ... current = current.next; } while (current != head);` loop (after checking the list isn't empty).

### Question 9: Why linked lists are slower than arrays in practice

An array stores its elements in **one contiguous block** of memory. When the CPU loads one element, it brings a whole cache line (typically 64 bytes) holding the next several elements too, and the hardware prefetcher predicts the sequential access and loads ahead. Most accesses are served from the fast L1 or L2 cache.

Linked list nodes are **separate heap objects scattered in memory**, each reached through a pointer. Every `next` can land on a different cache line, so traversal often causes cache misses that wait for main memory (around 100 times slower than L1). The prefetcher can't predict the next address until the current node is loaded. Each node also carries object header and pointer overhead, so fewer useful values fit in the cache.

Both traversals are O(n), but the constant factor per step is much larger for linked lists, so arrays are often several times faster.

### Question 10: Unreachable nodes and memory management

In a **garbage-collected language** such as Java, a "deleted" node that nothing references any more is found by the garbage collector and its memory is reclaimed automatically. The programmer only needs to unlink it, and to avoid keeping stray references to it (for example in a cache or a static field), which would keep it alive.

In languages with **manual memory management** such as C and C++, unlinking a node does not free it. The program must call `free()` or `delete` on it, after saving `node->next` if needed. If it doesn't:
- **Memory leaks:** unreachable nodes pile up, so a long-running program (server, embedded device) steadily uses more memory until it slows down or crashes.
- **Use-after-free and dangling pointers:** if memory is freed while something still points to it, or is freed twice, the program can crash, corrupt data, or have security holes.

So in those languages, every removal should be paired with exactly one deallocation, at the right time.
