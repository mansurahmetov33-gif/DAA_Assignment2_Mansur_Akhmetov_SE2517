# Assignment 2 — Data Structures Report

## 1. Introduction

This project implements and evaluates three fundamental data structures in Java:

- `DynamicArray`
- `MyLinkedList`
- `MinHeap`

The main goals of the assignment are to implement the data structures, verify their correctness using unit tests, analyze their algorithmic complexity, collect operation metrics, and compare their practical performance using benchmark workloads.

The project contains 37 JUnit tests, and all 37 tests pass successfully.

## 2. Implemented Data Structures

### 2.1 DynamicArray

`DynamicArray` is a resizable array implementation.

The elements are stored in contiguous memory, which allows direct access by index.

The main operations include:

- adding elements;
- inserting elements at a specific index;
- removing elements;
- accessing elements by index;
- searching for elements;
- automatic resizing when the capacity is reached.

Indexed access has constant-time complexity: `O(1)`.

Insertion or removal at an arbitrary position may require shifting elements, resulting in linear complexity.

### 2.2 MyLinkedList

`MyLinkedList` is implemented using linked nodes.

Each node stores an element and a reference to the next node.

The structure is efficient for operations at the head because no shifting of elements is required.

However, accessing an arbitrary index requires traversing the list from the beginning.

Therefore, indexed access has `O(n)` complexity.

### 2.3 MinHeap

`MinHeap` is implemented as a binary min-heap.

The smallest element is always stored at the root.

The main operations are:

- insertion;
- extracting the minimum element;
- `bubbleUp`;
- `bubbleDown`.

The height of a binary heap is logarithmic, therefore insertion and extraction have `O(log n)` worst-case complexity.

## 3. Complexity Analysis

| Operation | DynamicArray | MyLinkedList | MinHeap |
|---|---:|---:|---:|
| Indexed access | O(1) | O(n) | — |
| Search | O(n) | O(n) | — |
| Insert at head | O(n) | O(1) | — |
| Insert in middle | O(n) | O(n) | — |
| Remove at head | O(n) | O(1) | — |
| Remove in middle | O(n) | O(n) | — |
| Insert | — | — | O(log n) |
| Extract minimum | — | — | O(log n) |

`DynamicArray` provides constant-time random access because the address of an element can be calculated directly from its index.

`MyLinkedList` requires sequential traversal for indexed operations.

`MinHeap` requires at most the height of the heap for insertion and extraction, giving logarithmic complexity.

## 4. Loop Invariants

### 4.1 DynamicArray Insertion

When inserting an element at a specific index, existing elements from that index onward are shifted one position to the right.

The loop invariant is:

> Before each iteration of the shifting loop, all elements that have already been processed have been moved to their correct destination positions, while the relative order of the remaining elements is preserved.

When the loop terminates, the requested index becomes available for the new element.

### 4.2 MinHeap BubbleDown

During `bubbleDown`, the current element is compared with its children.

The loop invariant is:

> At the beginning of each iteration, the heap property is valid for all subtrees below the current node, except possibly at the current node.

The algorithm swaps the current element with the smaller child when necessary.

When the loop terminates, the min-heap property is restored throughout the structure.

## 5. Benchmark Methodology

Four benchmark workloads were implemented.

### W1 — Random Access

W1 performs 10,000 random `get(index)` operations.

The workload compares:

- `DynamicArray`
- `MyLinkedList`

The tested input sizes are:

- 100
- 1,000
- 10,000
- 100,000

### W2 — Search

W2 performs 1,000 `contains` operations:

- 500 values are present;
- 500 values are absent.

This workload compares the search performance of `DynamicArray` and `MyLinkedList`.

### W3 — Insert and Remove

W3 performs 1,000 insert/remove pairs.

Two positions are tested:

- `head`
- `middle`

This workload demonstrates the difference between array element shifting and linked-node manipulation.

### W4 — Priority Processing

W4 inserts `n` elements into `MinHeap` and then extracts all elements.

The extracted elements are checked to ensure that they are in nondecreasing order.

## 6. Benchmark Configuration

The following input sizes were used:

- 100
- 1,000
- 10,000
- 100,000

The random seed was fixed to `42`.

Each benchmark case was executed five times.

The first execution was used as a warm-up and discarded.

The reported result is the median of the remaining four executions.

Execution time is measured in milliseconds.

## 7. Benchmark Results

### 7.1 W1 — Random Access

| n | DynamicArray (ms) | MyLinkedList (ms) |
|---:|---:|---:|
| 100 | 0.2450 | 0.5794 |
| 1000 | 0.0918 | 5.7514 |
| 10000 | 0.2151 | 64.0845 |
| 100000 | 0.0041 | 815.9051 |

`DynamicArray` significantly outperforms `MyLinkedList` for random access.

This is expected because array indexing is `O(1)`, while linked-list indexing requires traversal and is `O(n)`.

The difference becomes especially large for `n = 100000`.

### 7.2 W2 — Search

| n | DynamicArray (ms) | MyLinkedList (ms) |
|---:|---:|---:|
| 100 | 0.4226 | 0.5574 |
| 1000 | 0.3162 | 1.1065 |
| 10000 | 2.2381 | 11.0656 |
| 100000 | 23.1193 | 166.3545 |

Both data structures have linear search complexity.

However, `MyLinkedList` is consistently slower because every search requires traversing linked nodes.

### 7.3 W3 — Insert and Remove at Head

| n | DynamicArray (ms) | MyLinkedList (ms) |
|---:|---:|---:|
| 100 | 1.7997 | 0.1669 |
| 1000 | 0.1135 | 0.0407 |
| 10000 | 0.5739 | 0.0074 |
| 100000 | 16.7714 | 0.0046 |

`MyLinkedList` performs very well for operations at the head.

Only a small number of references need to be changed.

`DynamicArray`, in contrast, may need to shift many existing elements.

### 7.4 W3 — Insert and Remove in the Middle

| n | DynamicArray (ms) | MyLinkedList (ms) |
|---:|---:|---:|
| 100 | 0.9259 | 0.7251 |
| 1000 | 0.0765 | 1.2303 |
| 10000 | 0.2986 | 13.9063 |
| 100000 | 8.4567 | 157.3685 |

For middle operations, both structures require linear work.

`DynamicArray` must shift elements, while `MyLinkedList` must traverse the list to reach the required position.

For large input sizes, the linked list becomes considerably slower.

### 7.5 W4 — Priority Processing

| n | MinHeap (ms) |
|---:|---:|
| 100 | 0.1443 |
| 1000 | 0.3468 |
| 10000 | 1.6326 |
| 100000 | 15.3289 |

The results show that `MinHeap` scales efficiently as the input size increases.

Insertion and minimum extraction have logarithmic complexity, making the structure suitable for priority processing.

## 8. Operation Metrics

The benchmark records three main metrics:

- steps;
- moves;
- comparisons.

These metrics provide additional information about the behavior of each algorithm.

### W1 — Random Access

For `DynamicArray`, indexed access requires a constant amount of work.

For `MyLinkedList`, the number of node traversals increases with the input size.

This difference is reflected in the much larger number of steps, moves, and comparisons for the linked list.

### W2 — Search

Search is linear for both structures.

The number of steps and comparisons grows approximately proportionally to the number of elements.

### W3 — Insert and Remove

For `DynamicArray`, insertion and removal can require many element moves.

For `MyLinkedList`, head operations require only a small number of reference changes, while middle operations require traversal.

### W4 — Priority Processing

For `MinHeap`, the number of comparisons and moves increases with the size of the heap.

However, each insertion or extraction is limited by the logarithmic height of the heap.

## 9. Cache Locality and Pointer Chasing

The benchmark results are affected not only by asymptotic complexity but also by memory layout.

`DynamicArray` stores elements in contiguous memory.

This provides good cache locality because neighboring elements are located close to each other.

`MyLinkedList` stores elements in separate nodes connected by references.

The nodes may be located in different areas of memory.

When the program traverses a linked list, it repeatedly follows pointers to find the next node.

This behavior is known as pointer chasing.

Pointer chasing can cause more cache misses and memory-access overhead compared with a contiguous array.

This helps explain why `MyLinkedList` is significantly slower for random access and large searches.

## 10. Testing

The project contains 37 JUnit tests.

All tests pass successfully:

Tests run: 37  
Failures: 0  
Errors: 0

The tests cover the implemented data structures and verify operations such as insertion, removal, access, searching, resizing, heap ordering, and edge cases.

## 11. Conclusions

The experimental results generally agree with the theoretical complexity analysis.

`DynamicArray` is the most suitable structure for frequent indexed access because indexed access has `O(1)` complexity and the elements are stored contiguously.

`MyLinkedList` is particularly effective for operations at the head because insertion and removal can be performed in `O(1)` time.

However, indexed access and middle operations are expensive because the list must be traversed.

`MinHeap` provides efficient priority processing because insertion and minimum extraction require `O(log n)` time.

The experiments also demonstrate that asymptotic complexity is not the only factor affecting practical performance. Cache locality, pointer chasing, memory layout, and the number of element movements can significantly influence execution time.

Overall, the benchmark results support the expected theoretical behavior of the implemented data structures.

## 12. Reproducibility

The benchmark results are stored in:

`results/results.csv`

The benchmark uses the fixed random seed `42`.

The test suite can be executed using:

`mvn test`

The generated benchmark results can be used to create performance plots stored in:

`results/plots/`

The project therefore contains the source code, unit tests, benchmark implementation, benchmark results, plots, `README.md`, and this `REPORT.md`.