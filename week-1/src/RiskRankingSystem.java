import java.util.Arrays;
import java.util.Comparator;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return String.format("%s(Score:%d, Bal:%.2f)", name, riskScore, accountBalance);
    }
}

public class RiskRankingSystem {

    public static void main(String[] args) {
        Client[] clients = {
                new Client("ClientC", 80, 5000.0),
                new Client("ClientA", 20, 15000.0),
                new Client("ClientB", 50, 2000.0),
                new Client("ClientD", 80, 12000.0) // Same risk, higher balance
        };

        System.out.println("--- Phase 1: Bubble Sort (Ascending Risk) ---");
        bubbleSortAscending(clients.clone());

        System.out.println("\n--- Phase 2: Insertion Sort (Descending Risk + Balance) ---");
        insertionSortRiskDesc(clients);

        System.out.println("\n--- Phase 3: Top Risks ---");
        displayTopRisks(clients, 3);
    }

    /**
     * Bubble Sort: Optimized with early termination and swap visualization.
     */
    public static void bubbleSortAscending(Client[] arr) {
        int n = arr.length;
        int totalSwaps = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // Visualize swap
                    System.out.println("Swapping " + arr[j].name + " with " + arr[j+1].name);
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                    totalSwaps++;
                }
            }
            if (!swapped) break;
        }
        System.out.println("Result: " + Arrays.toString(arr));
        System.out.println("Total Swaps: " + totalSwaps);
    }

    /**
     * Insertion Sort: Stable, descending order.
     * Primary: Risk Score (High to Low).
     * Secondary: Account Balance (High to Low).
     */
    public static void insertionSortRiskDesc(Client[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;

            // Shift elements if they are smaller than the key (for descending)
            // Or if risk is equal, shift if balance is smaller
            while (j >= 0 && (arr[j].riskScore < key.riskScore ||
                    (arr[j].riskScore == key.riskScore && arr[j].accountBalance < key.accountBalance))) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        System.out.println("Result: " + Arrays.toString(arr));
    }

    public static void displayTopRisks(Client[] arr, int topN) {
        int count = Math.min(topN, arr.length);
        System.out.println("Top " + count + " Highest Risk Clients:");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + arr[i]);
        }
    }
}
