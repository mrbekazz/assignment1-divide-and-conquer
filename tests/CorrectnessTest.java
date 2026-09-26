import java.util.Arrays;
import java.util.Random;

public class CorrectnessTest {
    public static void main(String[] args) {
        testSorting();
        testSelect();
        testClosestPair();
        System.out.println("All correctness tests passed.");
    }

    private static void testSorting() {
        int[][] cases = {
                {},
                {7},
                {5, 2, 9, 1, 5, 6},
                {1, 2, 3, 4, 5},
                {5, 4, 3, 2, 1},
                {2, 2, 2, 1, 1}
        };
        for (int[] original : cases) {
            int[] expected = original.clone();
            Arrays.sort(expected);
            int[] merge = original.clone();
            int[] quick = original.clone();
            MergeSorter.sort(merge);
            QuickSorter.sort(quick);
            if (!Arrays.equals(merge, expected) || !Arrays.equals(quick, expected)) {
                throw new AssertionError("Sorting test failed");
            }
        }

        Random random = new Random(42);
        for (int test = 0; test < 20; test++) {
            int[] original = new int[1 + random.nextInt(100)];
            for (int i = 0; i < original.length; i++) original[i] = random.nextInt(1000);
            int[] expected = original.clone();
            Arrays.sort(expected);
            int[] merge = original.clone();
            int[] quick = original.clone();
            MergeSorter.sort(merge);
            QuickSorter.sort(quick);
            if (!Arrays.equals(merge, expected) || !Arrays.equals(quick, expected)) {
                throw new AssertionError("Random sorting test failed");
            }
        }
    }

    private static void testSelect() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int n = 1 + random.nextInt(100);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = random.nextInt(20) - 10;
            int k = random.nextInt(n);
            int[] expected = a.clone();
            Arrays.sort(expected);
            int actual = DeterministicSelector.select(a, k);
            if (actual != expected[k]) throw new AssertionError("Select test failed");
        }
    }

    private static void testClosestPair() {
        Point[] duplicate = {new Point(1, 1), new Point(1, 1)};
        if (ClosestPairSolver.closest(duplicate) != 0.0) throw new AssertionError("Duplicate point test failed");
        if (!Double.isInfinite(ClosestPairSolver.closest(new Point[]{new Point(0, 0)}))) {
            throw new AssertionError("Single-point test failed");
        }

        Random random = new Random(42);
        for (int test = 0; test < 20; test++) {
            int n = 2 + random.nextInt(999);
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point(random.nextInt(5000), random.nextInt(5000));
            }
            double expected = bruteForce(points);
            double actual = ClosestPairSolver.closest(points);
            if (Math.abs(actual - expected) > 1e-9) throw new AssertionError("Closest Pair test failed");
        }
    }

    private static double bruteForce(Point[] points) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dx = points[i].x - points[j].x;
                double dy = points[i].y - points[j].y;
                best = Math.min(best, Math.sqrt(dx * dx + dy * dy));
            }
        }
        return best;
    }
}
