/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Sorter {

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exchange(Comparable[] a, int i , int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void shellsort(Comparable[] a) {

        int N = a.length;

        int h = 1;

        // 1, 4, 13, 40, ...
        while (h < N/3) {
            h = 3*h + 1;
        }
        // N=1 thru 5:  h = 1  [ 0, 1, 2, 3, 4 ]
        // N=6 thru 14:  h = 4
        // N=15 thru 41:  h = 13
        // Need at least two rounds of sweeping

        // Stride control
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h; j = j - h) {
                    if (less(a[j], a[j-h])) {
                        exchange(a, j, j-h);
                    }
                    else {
                        break;
                    }
                }
            }

            h = h / 3;
        }
    }

    // Knuth Shuffle, O(n)
    public static void shuffle(Comparable[] a) {
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniform(i+1);
            exchange(a, r, i);
        }
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo+1; i <= hi; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        // Copy over array into aux
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // Alter array "a" appropriately
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j];
                j++;
            }
            else if (j > hi) {
                a[k] = aux[i];
                i++;
            }
            else {
                if (less(aux[j], aux[i])) {
                    a[k] = aux[j];
                    j++;
                }
                else {
                    a[k] = aux[i];
                    i++;
                }
            }
        }
        assert isSorted(a, lo, hi);
    }

    public static void mergesort(Comparable[] a) { // Wrapper method
        int N = a.length;
        if (N == 0 || N ==1) {
            return;
        }
        Comparable[] aux = new Comparable[N];
        mergesort(a, aux, 0, N-1);
    }
    private static void mergesort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi-lo) / 2;

        mergesort(a, aux, lo, mid);
        mergesort(a, aux, mid+1, hi);

        merge(a, aux, lo, mid, hi);
    }

    public static void main(String[] args) {
        // Shellsort trials
        System.out.println("Shellsort trials");
        Sorter.shellsort(new Integer[0]);
        Integer[] singleton = {1};
        Sorter.shellsort(singleton);
        System.out.println(Arrays.toString(singleton));
        Integer[] pair = {2, 1};
        Sorter.shellsort(pair);
        System.out.println(Arrays.toString(pair));
        Integer[] group = {9, 6, 1, 2, 4, 5, 3, 8, 7, 10};
        Sorter.shellsort(group);
        System.out.println(Arrays.toString(group));

        // Mergesort trials
        System.out.println("Mergesort trials");
        Sorter.mergesort(new Integer[0]);
        Integer[] singleton2 = {1};
        Sorter.mergesort(singleton2);
        System.out.println(Arrays.toString(singleton2));
        Integer[] pair2 = {2, 1};
        Sorter.mergesort(pair2);
        System.out.println(Arrays.toString(pair2));
        Integer[] group2 = {9, 6, 1, 2, 4, 5, 3, 8, 7, 10};
        Sorter.mergesort(group2);
        System.out.println(Arrays.toString(group2));
    }
}
