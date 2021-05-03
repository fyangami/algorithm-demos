package xyz.fe1.algorithms.sort;

import java.util.Comparator;

public abstract class Sorts {

    protected void bubbleSort(Object[] arr, Comparator<Object> cmp) {
        int i, j;
        for (i = 0; i < arr.length; ++i)
            for (j = i + 1; j < arr.length; ++j)
                if (cmp.compare(arr[i], arr[j]) > 0) swap(arr, i, j);
    }

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

    /**
     * 希尔排序，插入排序的加强版，不是很理解 (:
     * @param arr 待排数组
     * @param cmp 比较函数
     */
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
     * 归并排序:
     *      分治思想，将数组切分为两个子数组，归并两个子数组，核心: marge函数
     * @param arr 待排数组
     * @param cmp 比较函数
     */
    protected void margeSort(Object[] arr, Comparator<Object> cmp) {
        Object[] marge = new Object[arr.length];  // 申请一块临时空间
        class Closure {
            void recursion(int begin, int end) {
                if (begin >= end) return;
                var mid = (begin + end) / 2;
                recursion(begin, mid);
                recursion(mid + 1, end);
                marge(begin, end, mid);
            }

            /**
             * 归并过程，按照comparator结果，将两个子数组分别追加到arr中，形成有序列
             * @param begin 起始索引
             * @param end 结束索引
             * @param mid 中间值(分割两个子数组)
             */
            void marge(int begin, int end, int mid) {
                copyArray(arr, marge, begin, end);
                int i = begin, j = mid + 1, cursor = begin;
                while (i <= mid || j <= end) {
                    if (i <= mid && j <= end) arr[cursor++] = cmp.compare(marge[i], marge[j]) < 0 ? marge[i++] : marge[j++];
                    else if (i <= mid) arr[cursor++] = marge[i++];
                    else arr[cursor++] = marge[j++];
                }
            }
        }
        new Closure().recursion(0, arr.length - 1);
    }

    /**
     * 快排(效率取决于中间元素的选择)，分治思想 核心函数: separation
     *      - 相对于归并排序，归并排序先分再治，快排先治(未彻底治理)再分(最细粒度即可完成治理)
     *      - 核心思想，在数组中选取一个元素E，根据这个元素进行分类，使当前数组呈现: [...] < E < [...] 然后递归处理子序列
     *      - 分割后的数组只相对于选中的元素E有序！
     * @param arr 待排数组
     * @param cmp 比较函数
     */
    protected void quickSort(Object[] arr, Comparator<Object> cmp) {
        class Closure {
            void recursion(int begin, int end) {
                if (begin >= end) return;
                var sep = separation(begin, end);  // 分割数组，返回分割后元素E的下标
                recursion(begin, sep - 1);
                recursion(sep + 1, end);
            }

            /**
             * 对指定序列进行分类
             * @param begin 起始下标
             * @param end 结束下标
             * @return 中间元素E的下标
             */
            int separation(int begin, int end) {
                Object selected = arr[begin];  // 选择数组的第一个元素为中间元素
                int left = begin + 1, right = end;
                for (;;) {
                    while (cmp.compare(selected, arr[left]) > 0) if (left++ >= end) break;
                    while (cmp.compare(selected, arr[right]) < 0) if (right-- <= begin) break;
                    if (left >= right) break;
                    swap(arr, left, right);
                }
                swap(arr, begin, right);  // 将选中的元素放在中间位置
                return right;
            }
        }
        new Closure().recursion(0, arr.length - 1);
    }

    protected void heapSort(Object[] arr, Comparator<Object> cmp) {
        class Heap {

            /**
             * 排序方法：
             *      1. 从数组头部开始构造二叉堆                  96 -> 88 59
             *      2. 从数组尾部开始构造有序列                  88 -> 79 2  59 -> 24 30  79 -> 70
             *      notice:
             *          由于一直在同数组进行操作，需注意下标越界问题
             *          构造二叉堆的过程这里使用了将末尾元素上浮的方法，更好的方法是对数组的前二分之一元素进行下沉操作，
             *              这里就不做实现了
             */
            void sort() {
                int i = 1, j = arr.length - 1;
                while (i <= j) swim(0, i++);  // 堆构造
                while (j > 0) {  // 排序
                    swap(arr, 0, j--);
                    sink(0, j);
                }
            }

            /**
             * 二叉堆的下沉操作
             * @param begin 堆的起始坐标
             * @param end 堆的结束下标
             */
            void sink(int begin, int end) {
                var target = begin;  // 选择第一个元素为下沉元素
                for (;;) {
                    var swap = leftChild(target);
                    if (swap > end) break;
                    if ((swap + 1 <= end) && cmp.compare(arr[swap], arr[swap + 1]) < 0) swap++;
                    if (cmp.compare(arr[target], arr[swap]) > 0) break;
                    swap(arr, swap, target);
                    target = swap;
                }
            }

            /**
             * 二叉堆的上浮操作
             * @param begin 堆的起始下标
             * @param end 堆的结束下标
             */
            void swim(int begin, int end) {
                var target = end;  // 选取数组的最后一个元素进行上浮操作
                while (target > begin) {
                    if (cmp.compare(arr[target], arr[target / 2]) < 0) break;
                    swap(arr, target, target / 2);
                    target = target / 2;
                }
            }

            /**
             * 获取左子节点的索引下标
             * @param parent 父节点下标
             * @return 左子节点
             */
            int leftChild(int parent) {
                if (parent == 0) return 1;
                return (parent * 2) + 1;
            }

            /**
             * 获取右子节点的索引下标
             * @param parent 父节点下标
             * @return 右子节点
             */
            int rightChild(int parent) {
                if (parent == 0) return 2;
                return (parent * 2) + 2;
            }

        }
        new Heap().sort();
    }

    public static void copyArray(Object[] source, Object[] target) {
        copyArray(source, target, 0, source.length);
    }

    private static void copyArray(Object[] source, Object[] target, int begin, int end) {
        if (begin < 0 || end < 0 || begin > end || end >= source.length || end >= target.length)
            throw new IllegalArgumentException();
        while (begin <= end) target[begin] = source[begin++];
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
        if (target < source) while (source != target) arr[--source + 1] = arr[source];
        else while (source != target) arr[++source - 1] = arr[source];
        arr[target] = tmp;
    }

    protected void swap(Object[] arr, int i, int j) {
        var tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 验证一个数组是否有序，需提供比较函数
     * @param arr 待验证数组
     * @param cmp 比较函数
     * @return 是否有序
     */
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
