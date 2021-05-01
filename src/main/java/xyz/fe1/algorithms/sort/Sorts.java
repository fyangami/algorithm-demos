package xyz.fe1.algorithms.sort;

import java.util.Comparator;

public abstract class Sorts {

    protected void selectSort(Object[] arr, Comparator<Object> cmp) {
        int selected;
        int i, j;
        for (i = 0; i < arr.length - 1; ++i) {
            selected = i;
            for (j = i + 1; j < arr.length; ++j) {
                var compared = cmp.compare(arr[j], arr[selected]);
                if (compared < 0) selected = j;
            }
            if (i != selected) swap(arr, i, selected);
        }
    }

    protected void insertSort(Object[] arr, Comparator<Object> cmp) {
        int i, j;
        for (i = 1; i < arr.length; ++i) {
            for (j = 0; j < i; ++j) {
                var compared = cmp.compare(arr[j], arr[i]);
                if (compared > 0) break;
            }
            insert(arr, j, i);
        }
    }

    protected void shellSort(Object[] arr, Comparator<Object> cmp) {
        int step = 0;
        while (step < arr.length / 3) step = step * 3 + 1; // 计算一个起始步长
        while (step > 0) {
            int i, j;
            for (i = step; i < arr.length; ++i)
                for (j = i; j >= step && cmp.compare(arr[j], arr[j - step]) < 0; j -= step)
                    swap(arr, j, j - step);
            step /= 3;
        }
    }

    /**
     * insert source to target in arr.
     *      example: arr = [1, 2, 3]  target: 0, source: 2
     *      inserted: arr = [3, 1, 2]
     * @param arr arr
     * @param target target index
     * @param source source index
     */
    protected void insert(Object[] arr, int target, int source) {
        if (target == source) return;
        if (target >= arr.length || source >= arr.length || target < 0 || source < 0)
            throw new IndexOutOfBoundsException("target or source index out of bound! target: "
                    + target + "  source: " + source);
        var tmp = arr[source];
        if (target < source) {
            while (source != target) arr[--source + 1] = arr[source];
        } else {
            while (source != target) arr[++source - 1] = arr[source];
        }
        arr[target] = tmp;
    }

    protected void swap(Object[] arr, int i, int j) {
        var tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    protected boolean isSorted(Object[] arr, Comparator<Object> cmp) {
        var previousDifference = 0;
        for (var i = 0; i < arr.length - 1; ++i) {
            var compared = cmp.compare(arr[i], arr[i + 1]);
            if (compared == 0) continue;
            if (previousDifference == 0) previousDifference = compared;
            if (compared != previousDifference) return false;
            previousDifference = compared;
        }
        return true;
    }

    protected int abs(int num) {
        return num < 0 ? -num : num;
    }

}
