import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Transaction {
    String accountId;
    String txId;

    public Transaction(String accountId, String txId) {
        this.accountId = accountId;
        this.txId = txId;
    }
}

public class AccountLookupSystem {

    public static void main(String[] args) {
        // Sample sorted logs for Binary Search
        Transaction[] logs = {
            new Transaction("accA", "T1"),
            new Transaction("accB", "T2"),
            new Transaction("accB", "T3"),
            new Transaction("accC", "T4"),
            new Transaction("accD", "T5")
        };

        String target = "accB";

        System.out.println("--- Linear Search (First Occurrence) ---");
        linearSearch(logs, target);

        System.out.println("\n--- Binary Search (Exact Match + Count) ---");
        binarySearchWithCount(logs, target);
    }

    /**
     * Linear Search: O(n)
     * Checks every element sequentially.
     */
    public static void linearSearch(Transaction[] logs, String target) {
        int comparisons = 0;
        int foundIndex = -1;

        for (int i = 0; i < logs.length; i++) {
            comparisons++;
            if (logs[i].accountId.equals(target)) {
                foundIndex = i;
                break; // Stop at first occurrence
            }
        }

        System.out.println("Target " + target + " found at index: " + foundIndex);
        System.out.println("Total Comparisons: " + comparisons);
    }

    /**
     * Binary Search: O(log n)
     * Requires sorted input. Uses divide-and-conquer logic.
     */
    public static void binarySearchWithCount(Transaction[] logs, String target) {
        int low = 0;
        int high = logs.length - 1;
        int comparisons = 0;
        int index = -1;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int res = target.compareTo(logs[mid].accountId);

            if (res == 0) {
                index = mid;
                break; // Found one instance
            } else if (res > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (index != -1) {
            // Count occurrences by expanding left and right from found index
            int count = 1;
            int left = index - 1;
            while (left >= 0 && logs[left].accountId.equals(target)) {
                count++;
                left--;
            }
            int right = index + 1;
            while (right < logs.length && logs[right].accountId.equals(target)) {
                count++;
                right++;
            }
            System.out.println("Target " + target + " found at index: " + index);
            System.out.println("Occurrences: " + count);
        } else {
            System.out.println("Target not found.");
        }
        System.out.println("Binary Search Comparisons: " + comparisons);
    }
}
