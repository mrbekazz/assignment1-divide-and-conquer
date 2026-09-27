# Divide-and-Conquer Algorithms

## About the Project

This project is a Java implementation of four divide-and-conquer algorithms:

* MergeSort
* QuickSort
* Deterministic Select
* Closest Pair of Points

The program allows the user to work with arrays and find the required results. It also provides automated correctness tests and performance experiments.

## Features

The program can:

* Sort an array using MergeSort.
* Sort an array using QuickSort.
* Find the k-th smallest element using Deterministic Select.
* Find the closest pair of points.
* Run automated correctness tests.
* Measure algorithm execution time.
* Save experimental results to a CSV file.

## Algorithms

### MergeSort

MergeSort divides the array into smaller parts, sorts them, and combines the sorted parts.

The implementation uses:

* A reusable auxiliary array.
* Insertion Sort for small subarrays.
* Comparison counting.
* Recursion depth measurement.

### QuickSort

QuickSort selects a random pivot and partitions the array around it.

The implementation uses:

* Randomized pivot selection.
* In-place partitioning.
* Recursion on the smaller partition.
* Iteration over the larger partition.
* Comparison counting.
* Recursion depth measurement.

### Deterministic Select

Deterministic Select finds the k-th smallest element without sorting the entire array.

The implementation uses the Median-of-Medians method:

* Elements are divided into groups of five.
* A median is found for each group.
* The median of these medians is used as the pivot.
* Only the required part of the array is processed.

### Closest Pair of Points

Closest Pair finds the minimum distance between two points.

The implementation:

* Sorts points by x-coordinate.
* Divides the points into two parts.
* Solves both parts recursively.
* Creates a strip around the middle line.
* Checks the points in the strip by y-coordinate.

## Program Structure

```text
src/
└── daa/
    ├── Main.java
    ├── MergeSorter.java
    ├── QuickSorter.java
    ├── DeterministicSelector.java
    ├── ClosestPairSolver.java
    ├── Experiment.java
    └── Point.java
```

### Main.java

The main entry point of the program.

It:

* Reads user input.
* Runs the algorithms.
* Runs correctness tests.
* Starts performance experiments.

### MergeSorter.java

Contains the MergeSort implementation.

### QuickSorter.java

Contains the randomized QuickSort implementation.

### DeterministicSelector.java

Contains the Median-of-Medians selection algorithm.

### ClosestPairSolver.java

Contains the divide-and-conquer Closest Pair algorithm and the brute-force reference method.

### Experiment.java

Generates test data, measures execution time, and saves the results to a CSV file.

### Point.java

Represents a two-dimensional point with `x` and `y` coordinates.

## Input

The program uses `Scanner` for user input.

For array operations, the user enters:

1. Array size.
2. Array elements.
3. The value of `k` for Deterministic Select.

The value of `k` is zero-based, so:

```text
k = 0
```

means the smallest element, and:

```text
k = 1
```

means the second smallest element.

## Output

The program displays:

* Sorted array from MergeSort.
* Sorted array from QuickSort.
* The k-th smallest element.
* Closest pair distance.
* Correctness test results.
* Information about completed performance experiments.

## Testing

The program includes automated correctness tests.

### Sorting Tests

MergeSort and QuickSort are compared with Java's:

```java
Arrays.sort()
```

The tests use different types of input data and include edge cases such as empty and single-element arrays.

### Deterministic Select Tests

The algorithm is tested with 100 random test cases.

The result is compared with the corresponding element of a sorted array.

### Closest Pair Tests

The divide-and-conquer implementation is compared with a brute-force solution on smaller datasets.

## Performance Experiments

The `Experiment` class measures the execution time of the algorithms using:

```java
System.nanoTime()
```

The experiments use different input sizes and input types:

* Random
* Sorted
* Reverse-sorted
* Duplicate-heavy

The results are saved in:

```text
results/results.csv
```

## Results

The `results` folder contains the experimental data in CSV format.

The `docs` folder contains screenshots and plots generated from the experiment results.

## Technologies

* Java
* Maven
* Git
* GitHub

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure Java and Maven are configured.
3. Run `Main.java`.
4. Enter the required values in the console.
5. Check the generated results in:

```text
results/results.csv
```

## Project Goal

The goal of the project is to implement divide-and-conquer algorithms in Java, verify their correctness, and measure their practical performance on different types and sizes of input data.
