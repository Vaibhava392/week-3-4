import java.util.Arrays;

class Asset {
    String ticker;
    double returnRate;
    double volatility;

    public Asset(String ticker, double returnRate, double volatility) {
        this.ticker = ticker;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return String.format("[%s: %.1f%%, Vol: %.1f]", ticker, returnRate, volatility);
    }
}

public class PortfolioSorter {

    public static void main(String[] args) {
        Asset[] portfolio = {
            new Asset("AAPL", 12.0, 1.5),
            new Asset("TSLA", 8.0, 4.2),
            new Asset("GOOG", 15.0, 1.1),
            new Asset("MSFT", 12.0, 1.2) // Same return as AAPL, lower volatility
        };

        // 1. Merge Sort (Stable - keeps AAPL before MSFT)
        mergeSort(portfolio, 0, portfolio.length - 1);
        System.out.println("MergeSort (Ascending): " + Arrays.toString(portfolio));

        // 2. Quick Sort (Median-of-3, Descending Return + Ascending Volatility)
        quickSort(portfolio, 0, portfolio.length - 1);
        System.out.println("QuickSort (Desc Return + Asc Vol): " + Arrays.toString(portfolio));
    }

    // --- MERGE SORT (STABLE) ---
    public static void mergeSort(Asset[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Asset[] arr, int left, int mid, int right) {
        Asset[] temp = new Asset[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i].returnRate <= arr[j].returnRate) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // --- QUICK SORT (MEDIAN-OF-THREE) ---
    public static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(arr, low, high);
            quickSort(arr, low, pivotIdx - 1);
            quickSort(arr, pivotIdx + 1, high);
        }
    }

    private static int partition(Asset[] arr, int low, int high) {
        // Median-of-Three Pivot Selection
        int mid = low + (high - low) / 2;
        int pivotIdx = selectMedian(arr, low, mid, high);
        
        // Swap pivot to end for Lomuto partition
        swap(arr, pivotIdx, high);
        Asset pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            // Logic: Descending Return RATE, then Ascending VOLATILITY
            if (arr[j].returnRate > pivot.returnRate || 
               (arr[j].returnRate == pivot.returnRate && arr[j].volatility < pivot.volatility)) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static int selectMedian(Asset[] arr, int a, int b, int c) {
        if ((arr[a].returnRate <= arr[b].returnRate && arr[b].returnRate <= arr[c].returnRate) || 
            (arr[c].returnRate <= arr[b].returnRate && arr[b].returnRate <= arr[a].returnRate)) return b;
        if ((arr[b].returnRate <= arr[a].returnRate && arr[a].returnRate <= arr[c].returnRate) || 
            (arr[c].returnRate <= arr[a].returnRate && arr[a].returnRate <= arr[b].returnRate)) return a;
        return c;
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
