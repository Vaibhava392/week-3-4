import java.util.Arrays;

public class RiskThresholdSystem {

    public static void main(String[] args) {
        // Sorted risk thresholds (Compliance Bands)
        int[] riskBands = {10, 25, 50, 100};
        int newClientScore = 30;

        System.out.println("--- Linear Search (Threshold Match) ---");
        linearSearch(riskBands, newClientScore);

        System.out.println("\n--- Binary Search (Floor & Ceiling) ---");
        findFloorAndCeiling(riskBands, newClientScore);
    }

    /**
     * Linear Search: Simple check for exact match.
     * Efficiency: O(n)
     */
    public static void linearSearch(int[] bands, int target) {
        int comparisons = 0;
        boolean found = false;
        for (int band : bands) {
            comparisons++;
            if (band == target) {
                found = true;
                break;
            }
        }
        System.out.println("Threshold " + target + (found ? " found" : " not found"));
        System.out.println("Total Comparisons: " + comparisons);
    }

    /**
     * Binary Search Variation: Finding Floor and Ceiling.
     * Efficiency: O(log n)
     */
    public static void findFloorAndCeiling(int[] bands, int target) {
        int low = 0, high = bands.length - 1;
        int floor = -1;
        int ceiling = -1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;

            if (bands[mid] == target) {
                floor = ceiling = bands[mid];
                break;
            } else if (bands[mid] < target) {
                floor = bands[mid]; // Current mid is a candidate for floor
                low = mid + 1;
            } else {
                ceiling = bands[mid]; // Current mid is a candidate for ceiling
                high = mid - 1;
            }
        }

        System.out.println("Search for: " + target);
        System.out.println("Floor (largest <= " + target + "): " + (floor != -1 ? floor : "None"));
        System.out.println("Ceiling (smallest >= " + target + "): " + (ceiling != -1 ? ceiling : "None"));
        System.out.println("Binary Comparisons: " + comparisons);
    }
}
