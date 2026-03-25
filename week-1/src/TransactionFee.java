import java.util.ArrayList;
import java.util.List;

class Transaction {
    String id;
    double fee;
    long timestamp; // Represented as epoch or simple integer for comparison

    public Transaction(String id, double fee, long timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s: fee=%.2f, ts=%d]", id, fee, timestamp);
    }
}

public class TransactionFee {

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, 1000));
        transactions.add(new Transaction("id2", 25.0, 930));
        transactions.add(new Transaction("id3", 5.0, 1015));

        processTransactions(transactions);
    }

    public static void processTransactions(List<Transaction> list) {
        int size = list.size();

        if (size <= 100) {
            System.out.println("Processing small batch via Bubble Sort...");
            bubbleSort(list);
        } else if (size <= 1000) {
            System.out.println("Processing medium batch via Insertion Sort...");
            insertionSort(list);
        }

        flagOutliers(list);
    }

    /**
     * Bubble Sort: Stable, O(n^2), with early termination.
     * Sorts by Fee Ascending.
     */
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int swaps = 0;
        int passes = 0;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    // Swap
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break; // Early termination if already sorted
        }
        System.out.println("Sorted: " + list);
        System.out.println("Stats: " + passes + " passes, " + swaps + " swaps.");
    }

    /**
     * Insertion Sort: Stable, O(n^2).
     * Sorts by Fee + Timestamp (Fee primary, Timestamp secondary).
     */
    public static void insertionSort(List<Transaction> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            // Shift elements that are greater than key
            // Logic: Compare Fee first; if equal, compare Timestamp
            while (j >= 0 && (list.get(j).fee > key.fee ||
                    (list.get(j).fee == key.fee && list.get(j).timestamp > key.timestamp))) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
        System.out.println("Sorted: " + list);
    }

    public static void flagOutliers(List<Transaction> list) {
        System.out.print("High-fee outliers (> $50): ");
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > 50.0) {
                System.out.print(t.id + " ");
                found = true;
            }
        }
        if (!found) System.out.print("none");
        System.out.println("\n");
    }
}
