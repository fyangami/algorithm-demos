package xyz.fe1.algorithms.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class IntegerSorts extends Sorts {

    private static final Random rand = new Random();
    public static final int RAND_NUMBER_MAXIMUM = Integer.MAX_VALUE;  // random bounded
    public static final int RAND_NUMBER_MINIMUM = 100;  // random bounded
    public static final int VALID_SORT_ORDER_LENGTH = 8;   // arr items number test sorted used.
    public static final int VALID_SORT_WASTE_LENGTH = 20000; // arr items number test waste used.

    public void bubbleSort(Integer[] arr) {
        super.bubbleSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public void selectSort(Integer[] arr) {
        super.selectSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public void insertSort(Integer[] arr) {
        super.insertSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public void shellSort(Integer[] arr) {
        super.shellSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public void margeSort(Integer[] arr) {
        super.margeSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public void quickSort(Integer[] arr) {
        super.quickSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public void heapSort(Integer[] arr) {
        super.heapSort(arr, Comparator.comparingInt(a -> (int) a));
    }

    public boolean isSorted(Integer[] arr) {
        return super.isSorted(arr, Comparator.comparingInt(a -> (int) a));
    }

    public static void main(String[] args) {
        var sorter = new IntegerSorts();
        sorter.validSortMethod("bubbleSort", sorter::bubbleSort);
        sorter.validSortMethod("selectSort", sorter::selectSort);
        sorter.validSortMethod("insertSort", sorter::insertSort);
        sorter.validSortMethod("shellSort", sorter::shellSort, 2 << 16);
        sorter.validSortMethod("margeSort", sorter::margeSort, 2 << 16);
        sorter.validSortMethod("quickSort", sorter::quickSort, 2 << 20);
        sorter.validSortMethod("heapSort", sorter::heapSort, 2 << 20);
    }

    /**
     * testing sort method
     * @param sortName sort name
     * @param sort sort functional interface
     */
    private void validSortMethod(String sortName, Sort sort) {
        validSortMethod(sortName, sort, VALID_SORT_WASTE_LENGTH);
    }

    private void validSortMethod(String sortName, Sort sort, int wasteArrLength) {
        System.out.println("==========================================================");
        System.out.printf("[%s] valid output: \n", sortName);
        var arr = genRandSort(VALID_SORT_ORDER_LENGTH, RAND_NUMBER_MINIMUM);
        System.out.printf("     before sort:        %s\n", Arrays.toString(arr));
        sort.sort(arr);
        System.out.printf("     after sort:         %s\n", Arrays.toString(arr));
        System.out.printf("     sorted status:      %s\n", isSorted(arr));
        arr = genRandSort(wasteArrLength);
        var begin = System.currentTimeMillis();
        sort.sort(arr);
        var end = System.currentTimeMillis();
        System.out.printf("     sorted %.2fw items waste:    %.3fs\n", wasteArrLength / 10000.0, (end - begin) / 1000.0);
        System.out.println("==========================================================");
    }

    @FunctionalInterface
    private interface Sort {
        void sort(Integer[] arr);
    }

    private Integer[] genRandSort(int len, int round) {
        Integer[] arr = new Integer[len];
        while (len != 0) arr[len-- - 1] = rand.nextInt(round);
        return arr;
    }

    private Integer[] genRandSort(int len) {
        return genRandSort(len, RAND_NUMBER_MAXIMUM);
    }
}
