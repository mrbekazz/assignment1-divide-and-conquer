import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Experiment {
    private static final Random RANDOM = new Random(42);

    public static void run(Scanner scanner) throws IOException {
        System.out.print("Enter small input size: ");
        int small = scanner.nextInt();
        System.out.print("Enter medium input size: ");
        int medium = scanner.nextInt();
        System.out.print("Enter large input size: ");
        int large = scanner.nextInt();

        int[] sizes = {small, medium, large};
        File file = new File("results/results.csv");
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("algorithm,input_type,n,time_ns,max_recursion_depth,metric_name,metric_value\n");
            for (int n : sizes) {
                for (String type : new String[]{"random", "sorted", "reverse-sorted", "duplicate-heavy"}) {
                    runSorting(writer, n, type, true);
                    runSorting(writer, n, type, false);
                    runSelect(writer, n, type);
                    runClosest(writer, n, type);
                }
            }
        }
        System.out.println("Experimental results saved to results/results.csv");
    }

    private static void runSorting(FileWriter writer, int n, String type, boolean merge) throws IOException {
        int[] input = createArray(n, type);
        long start = System.nanoTime();
        if (merge) MergeSorter.sort(input); else QuickSorter.sort(input);
        long time = System.nanoTime() - start;
        if (merge) {
            writeRow(writer, "MergeSort", type, n, time, MergeSorter.getMaxRecursionDepth(), "comparisons", MergeSorter.getComparisons());
        } else {
            writeRow(writer, "QuickSort", type, n, time, QuickSorter.getMaxRecursionDepth(), "swaps", QuickSorter.getSwaps());
        }
    }

    private static void runSelect(FileWriter writer, int n, String type) throws IOException {
        int[] input = createArray(n, type);
        int k = n / 2;
        long start = System.nanoTime();
        DeterministicSelector.select(input, k);
        long time = System.nanoTime() - start;
        writeRow(writer, "DeterministicSelect", type, n, time,
                DeterministicSelector.getMaxRecursionDepth(), "comparisons", DeterministicSelector.getComparisons());
    }

    private static void runClosest(FileWriter writer, int n, String type) throws IOException {
        Point[] input = createPoints(n, type);
        long start = System.nanoTime();
        ClosestPairSolver.closest(input);
        long time = System.nanoTime() - start;
        writeRow(writer, "ClosestPair", type, n, time,
                ClosestPairSolver.getMaxRecursionDepth(), "distance_checks", ClosestPairSolver.getDistanceChecks());
    }

    private static int[] createArray(int n, String type) {
        int[] a = new int[n];
        if (type.equals("random")) {
            for (int i = 0; i < n; i++) a[i] = RANDOM.nextInt(Math.max(1, n * 10));
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) a[i] = i;
        } else if (type.equals("reverse-sorted")) {
            for (int i = 0; i < n; i++) a[i] = n - i;
        } else {
            for (int i = 0; i < n; i++) a[i] = RANDOM.nextInt(10);
        }
        return a;
    }

    private static Point[] createPoints(int n, String type) {
        Point[] points = new Point[n];
        if (type.equals("random")) {
            for (int i = 0; i < n; i++) points[i] = new Point(RANDOM.nextDouble() * n, RANDOM.nextDouble() * n);
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) points[i] = new Point(i, RANDOM.nextDouble() * n);
        } else if (type.equals("reverse-sorted")) {
            for (int i = 0; i < n; i++) points[i] = new Point(n - i, RANDOM.nextDouble() * n);
        } else {
            for (int i = 0; i < n; i++) points[i] = new Point(RANDOM.nextInt(20), RANDOM.nextInt(20));
        }
        return points;
    }

    private static void writeRow(FileWriter writer, String algorithm, String type, int n, long time,
                                 int depth, String metricName, long metricValue) throws IOException {
        writer.write(algorithm + "," + type + "," + n + "," + time + "," + depth + "," + metricName + "," + metricValue + "\n");
    }
}
