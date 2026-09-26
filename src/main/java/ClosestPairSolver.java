import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    private static final Comparator<Point> BY_X = Comparator
            .comparingDouble((Point p) -> p.x)
            .thenComparingDouble(p -> p.y)
            .thenComparingInt(p -> p.id);
    private static final Comparator<Point> BY_Y = Comparator
            .comparingDouble((Point p) -> p.y)
            .thenComparingDouble(p -> p.x)
            .thenComparingInt(p -> p.id);
    private static int maxRecursionDepth;
    private static long distanceChecks;

    public static double closest(Point[] points) {
        maxRecursionDepth = 0;
        distanceChecks = 0;
        if (points.length < 2) return Double.POSITIVE_INFINITY;

        Point[] px = points.clone();
        Arrays.sort(px, BY_X);
        Point[] py = points.clone();
        Arrays.sort(py, BY_Y);
        Point[] buffer = new Point[points.length];
        return solve(px, py, buffer, 0, points.length, 1);
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static long getDistanceChecks() {
        return distanceChecks;
    }

    private static double solve(Point[] px, Point[] py, Point[] buffer, int left, int right, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);
        int n = right - left;
        if (n <= 3) return bruteForce(px, left, right);

        int mid = left + n / 2;
        Point leftLast = px[mid - 1];
        double midX = px[mid].x;

        int li = left;
        int ri = mid;
        for (int i = left; i < right; i++) {
            Point p = py[i];
            if (BY_X.compare(p, leftLast) <= 0) buffer[li++] = p;
            else buffer[ri++] = p;
        }
        System.arraycopy(buffer, left, py, left, n);

        double leftDistance = solve(px, py, buffer, left, mid, depth + 1);
        double rightDistance = solve(px, py, buffer, mid, right, depth + 1);

        int leftIndex = left;
        int rightIndex = mid;
        int bufferIndex = left;
        while (leftIndex < mid && rightIndex < right) {
            if (BY_Y.compare(py[leftIndex], py[rightIndex]) <= 0) buffer[bufferIndex++] = py[leftIndex++];
            else buffer[bufferIndex++] = py[rightIndex++];
        }
        while (leftIndex < mid) buffer[bufferIndex++] = py[leftIndex++];
        while (rightIndex < right) buffer[bufferIndex++] = py[rightIndex++];
        System.arraycopy(buffer, left, py, left, right - left);

        double delta = Math.min(leftDistance, rightDistance);

        int stripSize = 0;
        for (int i = left; i < right; i++) {
            Point p = py[i];
            if (Math.abs(p.x - midX) < delta) buffer[left + stripSize++] = p;
        }

        double best = delta;
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && buffer[left + j].y - buffer[left + i].y < best; j++) {
                distanceChecks++;
                best = Math.min(best, distance(buffer[left + i], buffer[left + j]));
            }
        }
        return best;
    }

    private static double bruteForce(Point[] points, int left, int right) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = left; i < right; i++) {
            for (int j = i + 1; j < right; j++) {
                distanceChecks++;
                best = Math.min(best, distance(points[i], points[j]));
            }
        }
        return best;
    }

    private static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
