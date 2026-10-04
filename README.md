# DAA Assignment 2 — Data Structures

## Overview

This project implements three data structures from scratch in Java:

- DynamicArray
- MyLinkedList
- MinHeap

The project also includes:

- JUnit 5 tests
- operation metrics
- benchmark workloads
- CSV results
- performance plots
- complexity analysis
- loop invariant proofs

The benchmark is designed to compare the practical and theoretical performance of the implemented data structures.

---

## Requirements

- Java 25
- Maven
- IntelliJ IDEA or another Java IDE

The project does not use Java collection classes such as:

- `ArrayList`
- `LinkedList`
- `PriorityQueue`

The implemented structures use primitive `int` values.

---

## Project Structure

```
src/
├── main/
│   └── java/
│       ├── structures/
│       │   ├── DynamicArray.java
│       │   ├── MyLinkedList.java
│       │   └── MinHeap.java
│       │
│       ├── metrics/
│       │   └── Metrics.java
│       │
│       └── benchmark/
│           └── Benchmark.java
│
└── test/
    └── java/
        ├── DynamicArrayTest.java
        ├── MyLinkedListTest.java
        └── MinHeapTest.java

results/
├── results.csv
└── plots/

README.md
REPORT.md
pom.xml

```

## Implemented Data Structures
# DynamicArray

A dynamic array based on a primitive int[].

Supported operations:
```
add(x)
add(index, x)
remove(index)
get(index)
contains(x)
```

The array uses a 2x capacity growth strategy when it becomes full.

# MyLinkedList
A singly linked list where each node contains:
```
int data
Node next
```

Supported operations:
```
add(x)
add(index, x)
remove(index)
get(index)
contains(x)
```

# MinHeap

An array-based binary min-heap.

Supported operations:

```
insert(x)
peekMin()
extractMin()
```

The heap maintains the min-heap property after insertions and extractions.

# Metrics

The project records three operation metrics:

- steps — array-cell reads or moves to the next linked-list node
- moves — element shifts in arrays or link updates in lists
- comparisons — comparisons between elements

Metrics are counted inside the data-structure operations.

# Testing

JUnit 5 is used for testing the data structures.

Tests cover:

- normal operations
- empty structures
- one-element structures
- duplicate values
- insertion at the beginning
- insertion at the end
- removal at the beginning
- removal at the end
- removal from the middle
- invalid indices
- search operations
- heap ordering

Run all tests with:
```
mvn test
```

# Benchmark

The benchmark contains four workloads.

## W1 — Random Access
- Fill the structure with n values.
- Perform 10,000 random get(index) operations.
- Compare DynamicArray and MyLinkedList.
## W2 — Search
- Fill the structure with n values.
- Perform 1,000 contains(x) queries.
- Half of the queries use present values.
- Half use absent values.
## W3 — Insert & Remove

Two variants are tested:

```
head
middle
```

For each variant:

- 1,000 insertions
- 1,000 removals

The head variant uses index 0.

The middle variant uses index n/2.

Both DynamicArray and MyLinkedList are tested.

## W4 — Priority Processing
- Insert n values into MinHeap.
- Extract n values.
- Verify that the extracted values are in non-decreasing order.


# Benchmark Sizes

The benchmark uses:
```
n = 100
n = 1,000
n = 10,000
n = 100,000
```
The random seed is:
```
42
```
This makes the generated benchmark data reproducible.

Each benchmark case is executed five times.

The first run is used as a warm-up and discarded.

The median of the remaining four runs is written to the CSV file.

# Running the Benchmark

Open:
```
src/main/java/benchmark/Benchmark.java
```
Run the main() method from IntelliJ IDEA.

After the benchmark finishes, the results are saved to:
```
results/results.csv
```
The CSV format is:
```
workload,variant,structure,n,time_ms,steps,moves,comparisons
```
# Results

The benchmark results are used to generate performance plots stored in:
```
results/plots/
```
The full analysis, complexity table, loop invariants, benchmark discussion, and conclusions are available in:
```
REPORT.md
```
# Reproducibility

The benchmark uses:
```
new Random(42)
```
for reproducible input generation.

Running the benchmark again regenerates the CSV results using the same workload sizes and random seed.

# Conclusion

The project demonstrates the practical differences between contiguous arrays, linked lists, and binary heaps.

The benchmark is used to compare:

- indexed access
- searching
- insertion and removal
- priority processing
- operation counts
- cache locality
- pointer chasing