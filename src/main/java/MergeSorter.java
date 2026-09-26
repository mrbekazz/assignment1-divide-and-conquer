public class MergeSorter {
    private static final int CUTOFF = 16;
    private static long comparisons;
    private static int maxRecursionDepth;

    public static void sort(int[] a) {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (a.length < 2) return;
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, 1);
    }

    public static long getComparisons() {
        return comparisons;
    }

    public static int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    private static void sort(int[] a, int[] buffer, int left, int right, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);
        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right);
            return;
        }
        int mid = left + (right - left) / 2;
        sort(a, buffer, left, mid, depth + 1);
        sort(a, buffer, mid + 1, right, depth + 1);
        if (a[mid] <= a[mid + 1]) {
            comparisons++;
            return;
        }
        merge(a, buffer, left, mid, right);
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

    private static void merge(int[] a, int[] buffer, int left, int mid, int right) {
        for (int i = left; i <= right; i++) buffer[i] = a[i];
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            comparisons++;
            if (buffer[i] <= buffer[j]) a[k++] = buffer[i++];
            else a[k++] = buffer[j++];
        }
        while (i <= mid) a[k++] = buffer[i++];
        while (j <= right) a[k++] = buffer[j++];
    }
}
