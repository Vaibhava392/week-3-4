import java.util.Arrays;

class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class TradeAnalysisSystem {

    public static void main(String[] args) {
        Trade[] morningTrades = {
            new Trade("T3", 500),
            new Trade("T1", 100),
            new Trade("T2", 300)
        };

        // 1. Merge Sort (Ascending & Stable)
        mergeSort(morningTrades, 0, morningTrades.length - 1);
        System.out.println("MergeSort (Asc): " + Arrays.toString(morningTrades));

        // 2. Quick Sort (Descending & In-place)
        quickSort(morningTrades, 0, morningTrades.length - 1);
        System.out.println("QuickSort (Desc): " + Arrays.toString(morningTrades));

        // 3. Simple Volume Calculation
        long totalVolume = 0;
        for (Trade t : morningTrades) totalVolume += t.volume;
        System.out.println("Total Volume: " + totalVolume);
    }

    // --- MERGE SORT LOGIC ---
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Trade[] arr, int left, int mid, int right) {
        Trade[] temp = new Trade[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            // Stable: keeps original order for equal volumes
            if (arr[i].volume <= arr[j].volume) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // --- QUICK SORT LOGIC (DESCENDING) ---
    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(Trade[] arr, int low, int high) {
        // Using last element as pivot (Lomuto Partition)
        int pivot = arr[high].volume;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Change to > for Descending order
            if (arr[j].volume > pivot) {
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
