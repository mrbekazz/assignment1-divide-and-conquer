import java.util.Random;

public class QuickSorter {
    private static final Random RANDOM = new Random(42);
    private static long comparisons;
    private static long swaps;
    private static int maxRecursionDepth;

    public static void sort(int[] a) {
        comparisons = 0;
        swaps = 0;
        maxRecursionDepth = 0;
        quickSort(a, 0, a.length - 1, 1);
    }

    public static long getComparisons() {
        return comparisons;
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    private static void quickSort(int[] a, int low, int high, int depth) {
        while (low < high) {
            maxRecursionDepth = Math.max(maxRecursionDepth, depth);
            int pivotIndex = low + RANDOM.nextInt(high - low + 1);
            swap(a, pivotIndex, high);
            int p = partition(a, low, high);
            if (p - low < high - p) {
                quickSort(a, low, p - 1, depth + 1);
                low = p + 1;
            } else {
                quickSort(a, p + 1, high, depth + 1);
                high = p - 1;
            }
        }
        if (low == high) maxRecursionDepth = Math.max(maxRecursionDepth, depth);
    }

    private static int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, high);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        swaps++;
    }

    public static long getSwaps() {
        return swaps;
    }
}
