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

    public static void main(String[] args) {
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
    }
}
