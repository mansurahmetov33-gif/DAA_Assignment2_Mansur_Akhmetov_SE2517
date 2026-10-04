package benchmark;

import metrics.Metrics;
import structures.DynamicArray;
import structures.MinHeap;
import structures.MyLinkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};

    private static final int RUNS = 5;
    private static final int RANDOM_ACCESS_OPERATIONS = 10_000;
    private static final int SEARCH_OPERATIONS = 1_000;
    private static final int INSERT_REMOVE_OPERATIONS = 1_000;

    private static final long SEED = 42L;

    public static void main(String[] args) throws IOException {

        File resultsDirectory = new File("results");

        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs();
        }

        File csvFile = new File(resultsDirectory, "results.csv");

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {

            writer.println(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            for (int n : SIZES) {

                System.out.println("Running benchmarks for n = " + n);

                int[] data = generateData(n);

                runRandomAccess(writer, data, n, "DynamicArray");
                runRandomAccess(writer, data, n, "MyLinkedList");

                runSearch(writer, data, n, "DynamicArray");
                runSearch(writer, data, n, "MyLinkedList");

                runInsertRemove(writer, data, n, "DynamicArray", "head");
                runInsertRemove(writer, data, n, "DynamicArray", "middle");

                runInsertRemove(writer, data, n, "MyLinkedList", "head");
                runInsertRemove(writer, data, n, "MyLinkedList", "middle");

                runPriorityProcessing(writer, data, n);
            }
        }

        System.out.println();
        System.out.println("Benchmark finished.");
        System.out.println("Results saved to: results/results.csv");
    }


    private static int[] generateData(int n) {

        int[] data = new int[n];

        Random random = new Random(SEED);

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static void runRandomAccess(
            PrintWriter writer,
            int[] data,
            int n,
            String structureName) {

        int[] indices = generateRandomIndices(n);

        long[] times = new long[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            Metrics metrics = new Metrics();

            if (structureName.equals("DynamicArray")) {

                DynamicArray array = new DynamicArray(metrics);

                for (int value : data) {
                    array.add(value);
                }

                metrics.reset();

                long start = System.nanoTime();

                for (int index : indices) {
                    array.get(index);
                }

                long end = System.nanoTime();

                times[run] = end - start;

            } else {

                MyLinkedList list = new MyLinkedList(metrics);

                for (int value : data) {
                    list.add(value);
                }

                metrics.reset();

                long start = System.nanoTime();

                for (int index : indices) {
                    list.get(index);
                }

                long end = System.nanoTime();

                times[run] = end - start;
            }

            if (run > 0) {
                steps += metrics.getSteps();
                moves += metrics.getMoves();
                comparisons += metrics.getComparisons();
            }
        }

        int measuredRuns = RUNS - 1;

        steps /= measuredRuns;
        moves /= measuredRuns;
        comparisons /= measuredRuns;

        double medianTimeMs = medianAfterWarmup(times) / 1_000_000.0;

        writeResult(
                writer,
                "W1",
                "-",
                structureName,
                n,
                medianTimeMs,
                steps,
                moves,
                comparisons
        );
    }

    private static int[] generateRandomIndices(int n) {

        int[] indices = new int[RANDOM_ACCESS_OPERATIONS];

        Random random = new Random(SEED);

        for (int i = 0; i < indices.length; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }



    private static void runSearch(
            PrintWriter writer,
            int[] data,
            int n,
            String structureName) {

        int[] queries = generateSearchQueries(data);

        long[] times = new long[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            Metrics metrics = new Metrics();

            if (structureName.equals("DynamicArray")) {

                DynamicArray array = new DynamicArray(metrics);

                for (int value : data) {
                    array.add(value);
                }

                metrics.reset();

                long start = System.nanoTime();

                for (int query : queries) {
                    array.contains(query);
                }

                long end = System.nanoTime();

                times[run] = end - start;

            } else {

                MyLinkedList list = new MyLinkedList(metrics);

                for (int value : data) {
                    list.add(value);
                }

                metrics.reset();

                long start = System.nanoTime();

                for (int query : queries) {
                    list.contains(query);
                }

                long end = System.nanoTime();

                times[run] = end - start;
            }

            if (run > 0) {
                steps += metrics.getSteps();
                moves += metrics.getMoves();
                comparisons += metrics.getComparisons();
            }
        }

        int measuredRuns = RUNS - 1;

        steps /= measuredRuns;
        moves /= measuredRuns;
        comparisons /= measuredRuns;

        double medianTimeMs = medianAfterWarmup(times) / 1_000_000.0;

        writeResult(
                writer,
                "W2",
                "-",
                structureName,
                n,
                medianTimeMs,
                steps,
                moves,
                comparisons
        );
    }

    private static int[] generateSearchQueries(int[] data) {

        int[] queries = new int[SEARCH_OPERATIONS];

        Random random = new Random(SEED);

        int half = SEARCH_OPERATIONS / 2;

        // Half of queries are present.
        for (int i = 0; i < half; i++) {
            int index = random.nextInt(data.length);
            queries[i] = data[index];
        }


        for (int i = half; i < SEARCH_OPERATIONS; i++) {
            queries[i] = -1 - i;
        }

        return queries;
    }

    private static void runInsertRemove(
            PrintWriter writer,
            int[] data,
            int n,
            String structureName,
            String variant) {

        long[] times = new long[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            Metrics metrics = new Metrics();

            if (structureName.equals("DynamicArray")) {

                DynamicArray array = new DynamicArray(metrics);

                for (int value : data) {
                    array.add(value);
                }

                metrics.reset();

                int index = getInsertRemoveIndex(n, variant);

                long start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    array.add(index, i);
                    array.remove(index);
                }

                long end = System.nanoTime();

                times[run] = end - start;

            } else {

                MyLinkedList list = new MyLinkedList(metrics);

                for (int value : data) {
                    list.add(value);
                }

                metrics.reset();

                int index = getInsertRemoveIndex(n, variant);

                long start = System.nanoTime();

                for (int i = 0; i < INSERT_REMOVE_OPERATIONS; i++) {
                    list.add(index, i);
                    list.remove(index);
                }

                long end = System.nanoTime();

                times[run] = end - start;
            }

            if (run > 0) {
                steps += metrics.getSteps();
                moves += metrics.getMoves();
                comparisons += metrics.getComparisons();
            }
        }

        int measuredRuns = RUNS - 1;

        steps /= measuredRuns;
        moves /= measuredRuns;
        comparisons /= measuredRuns;

        double medianTimeMs = medianAfterWarmup(times) / 1_000_000.0;

        writeResult(
                writer,
                "W3",
                variant,
                structureName,
                n,
                medianTimeMs,
                steps,
                moves,
                comparisons
        );
    }

    private static int getInsertRemoveIndex(int n, String variant) {

        if (variant.equals("head")) {
            return 0;
        }

        return n / 2;
    }



    private static void runPriorityProcessing(
            PrintWriter writer,
            int[] data,
            int n) {

        long[] times = new long[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            Metrics metrics = new Metrics();

            MinHeap heap = new MinHeap(metrics);

            long start = System.nanoTime();

            for (int value : data) {
                heap.insert(value);
            }

            int previous = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {

                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "MinHeap returned values in wrong order."
                    );
                }

                previous = current;
            }

            long end = System.nanoTime();

            times[run] = end - start;

            if (run > 0) {
                steps += metrics.getSteps();
                moves += metrics.getMoves();
                comparisons += metrics.getComparisons();
            }
        }

        int measuredRuns = RUNS - 1;

        steps /= measuredRuns;
        moves /= measuredRuns;
        comparisons /= measuredRuns;

        double medianTimeMs = medianAfterWarmup(times) / 1_000_000.0;

        writeResult(
                writer,
                "W4",
                "-",
                "MinHeap",
                n,
                medianTimeMs,
                steps,
                moves,
                comparisons
        );
    }

    private static long medianAfterWarmup(long[] times) {

        long[] measured = new long[RUNS - 1];

        for (int i = 1; i < RUNS; i++) {
            measured[i - 1] = times[i];
        }

        // Simple sorting without collections.
        for (int i = 0; i < measured.length - 1; i++) {
            for (int j = i + 1; j < measured.length; j++) {

                if (measured[j] < measured[i]) {
                    long temp = measured[i];
                    measured[i] = measured[j];
                    measured[j] = temp;
                }
            }
        }


        return (measured[1] + measured[2]) / 2;
    }



    private static void writeResult(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            double timeMs,
            long steps,
            long moves,
            long comparisons) {

        writer.printf(
                java.util.Locale.US,
                "%s,%s,%s,%d,%.4f,%d,%d,%d%n",
                workload,
                variant,
                structure,
                n,
                timeMs,
                steps,
                moves,
                comparisons
        );

        System.out.printf(
                "%s | %s | %s | n=%d | %.4f ms%n",
                workload,
                structure,
                variant,
                n,
                timeMs
        );
    }
}