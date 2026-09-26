public class DeterministicSelector {
    private static long comparisons;
    private static long swaps;
    private static int maxRecursionDepth;

    public static int select(int[] a, int k) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k must be in [0, n-1]");
        comparisons = 0;
        swaps = 0;
        maxRecursionDepth = 0;
        return select(a, 0, a.length - 1, k, 1);
    }

    public static long getComparisons() {
        return comparisons;
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    private static int select(int[] a, int left, int right, int k, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);
        if (left == right) return a[left];

        int pivot = medianOfMedians(a, left, right, depth + 1);
        int[] equal = partitionThreeWay(a, left, right, pivot);
        if (k < equal[0]) return select(a, left, equal[0] - 1, k, depth + 1);
        if (k <= equal[1]) return pivot;
        return select(a, equal[1] + 1, right, k, depth + 1);
    }

    private static int medianOfMedians(int[] a, int left, int right, int depth) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(a, left, right);
            return a[left + n / 2];
        }

        int medians = 0;
        for (int start = left; start <= right; start += 5) {
            int end = Math.min(start + 4, right);
            insertionSort(a, start, end);
            int medianIndex = start + (end - start) / 2;
            swap(a, left + medians, medianIndex);
            medians++;
        }
        return select(a, left, left + medians - 1, left + medians / 2, depth);
    }

    private static int[] partitionThreeWay(int[] a, int left, int right, int pivot) {
        int lt = left;
        int i = left;
        int gt = right;
        while (i <= gt) {
            comparisons++;
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                comparisons++;
                swap(a, i, gt--);
            } else {
                comparisons++;
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void insertionSort(int[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int value = a[i];
            int j = i - 1;
            while (j >= left) {
                comparisons++;
                if (a[j] <= value) break;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = value;
        }
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
