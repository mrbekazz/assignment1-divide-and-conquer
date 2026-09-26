import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Sort array");
            System.out.println("2. Find k-th smallest element");
            System.out.println("3. Find closest pair of points");
            System.out.println("4. Run performance experiments");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();

            if (choice == 0) break;
            if (choice == 1) runSorting(scanner);
            else if (choice == 2) runSelect(scanner);
            else if (choice == 3) runClosest(scanner);
            else if (choice == 4) Experiment.run(scanner);
            else System.out.println("Invalid choice.");
        }
        scanner.close();
    }

    private static void runSorting(Scanner scanner) {
        System.out.print("Enter array size: ");
        int n = scanner.nextInt();
        int[] a = new int[n];
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) a[i] = scanner.nextInt();

        System.out.print("1. MergeSort or 2. QuickSort: ");
        int algorithm = scanner.nextInt();
        if (algorithm == 1) MergeSorter.sort(a);
        else QuickSorter.sort(a);

        System.out.print("Sorted array: ");
        for (int value : a) System.out.print(value + " ");
        System.out.println();
    }

    private static void runSelect(Scanner scanner) {
        System.out.print("Enter array size: ");
        int n = scanner.nextInt();
        int[] a = new int[n];
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) a[i] = scanner.nextInt();
        System.out.print("Enter k (0-based): ");
        int k = scanner.nextInt();
        System.out.println("k-th smallest element: " + DeterministicSelector.select(a, k));
    }

    private static void runClosest(Scanner scanner) {
        System.out.print("Enter number of points: ");
        int n = scanner.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter x y for point " + (i + 1) + ": ");
            points[i] = new Point(scanner.nextDouble(), scanner.nextDouble());
        }
        double result = ClosestPairSolver.closest(points);
        if (Double.isInfinite(result)) System.out.println("At least two points are required.");
        else System.out.println("Closest distance: " + result);
    }
}
