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

    private static void mergesortBU(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        // Sz is the subarray unit size
        for (int sz = 1; sz < N; sz = sz*2) {
            for (int lo = 0; lo < N-sz; lo= lo + 2*sz) {
                int hi = Math.min(N-1, lo + sz*2-1);
                int mid = lo + sz - 1;
                merge(a, aux, lo, mid, hi);
            }
        }
    }

    public static void quicksort(Comparable[] a) {
        shuffle(a);
        quicksort(a, 0, a.length-1);
    }
    // Work with subset of array "a"
    private static void quicksort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int k = partition(a, lo, hi); // partition and get the divider point
        quicksort(a, lo, k-1);
        quicksort(a,k+1, hi);
    }
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo+1;
        int j = hi;

        while (i <= j) {
            while (i <= j && a[lo].compareTo(a[j]) <= 0) {
                j--;
            }
            while (i <= j && a[lo].compareTo(a[i]) >= 0) {
                i++;
            }
            if (i < j) {
                exchange(a, i, j);
            }
        }

        exchange(a, lo, j);
        return j;
    }

    // Given an array of N items, find the kth largest
    // This is linear time
    public static Comparable select(Comparable[] a, int k) {
        shuffle(a);
        int N = a.length;
        if (k < 1 || k > N) return null;

        int lo = 0;
        int hi = N-1;
        int j = -1;

        while (j+1 != k) {
            j = partition(a, lo, hi);
            if (j+1 == k) {
                return a[j];
            }
            else if (j+1 > k) {
                hi = j-1;
            }
            else {
                lo = j+1;
            }
        }
        return a[j];
    }

    // 3-way quicksort
    public static void quicksort3w(Comparable[] a) {
        quicksort3w(a, 0, a.length-1);
    }
    private static void quicksort3w(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        // The "current dude" to be partitioned is at index "lt"
        int lt = lo;
        int gt = hi;
        int i = lo+1;

        while (i <= gt) {
            if (a[i].compareTo(a[lt]) == 0) {
                i++;
            }
            else if (a[i].compareTo(a[lt]) < 0) {
                exchange(a, i, lt);
                i++;
                lt++;
            }
            else {
                exchange(a, i, gt);
                gt--;
            }
        }

        quicksort3w(a, lo, lt-1);
        quicksort3w(a, gt+1, hi);
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
        // Quicksort trials
        System.out.println("Quicksort trials");
        Integer[] group3 = {12, 4, 9, 6, 11, 1, 2, 4, 5, 3, 8, 7, 10, 10};
        Integer[] group4 = {0};
        Integer[] group5 = {1, 0};
        Integer[] group6 = {0, 2, 1};
        Sorter.quicksort(group3);
        System.out.println(Arrays.toString(group3));
        Sorter.quicksort(group4);
        System.out.println(Arrays.toString(group4));
        Sorter.quicksort(group5);
        System.out.println(Arrays.toString(group5));
        Sorter.quicksort(group6);
        System.out.println(Arrays.toString(group6));
        // Select trials
        System.out.println("Select trials");
        Integer[] group7 = {12, 9, 6, 11, 1, 2, 4, 5, 3, 8, 7, 10};
        System.out.println("Select 1st: " + select(group7, 1));
        System.out.println("Select 6th: " + select(group7, 6));
        System.out.println("Select 12th: " + select(group7, 12));
        // Three-way quicksort trials
        System.out.println("3-way Quicksort trials");
        Integer[] group8 = {12, 4, 9, 6, 11, 1, 2, 4, 5, 3, 8, 7, 10, 10};
        Integer[] group9 = {0};
        Integer[] group10 = {1, 0};
        Integer[] group11 = {0, 2, 1};
        Sorter.quicksort3w(group8);
        System.out.println(Arrays.toString(group8));
        Sorter.quicksort3w(group9);
        System.out.println(Arrays.toString(group9));
        Sorter.quicksort3w(group10);
        System.out.println(Arrays.toString(group10));
        Sorter.quicksort3w(group11);
        System.out.println(Arrays.toString(group11));
    }
}
