package stc.lesson2;

import java.util.Arrays;

/**
 * Утилитный класс для осуществления сортировки слиянием.
 *
 * @author Михаил Морин
 */
class MergeSorter {
    private MergeSorter() {
    }

    /**
     * Метод сортировки элементов массива
     *
     * @param arr - сортируемый массив
     * @return возвращает отсортированный массив
     * @see MergeSorter#merge(int[], int[])
     */
    static int[] sort(int[] arr) {
        if (arr.length < 2) {
            return arr;
        }

        int l = arr.length / 2;
        int[] arr1 = Arrays.copyOfRange(arr, 0, l);
        int[] arr2 = Arrays.copyOfRange(arr, l, arr.length);

        return merge(sort(arr1), sort(arr2));
    }

    /**
     * Слияние элементов.
     *
     * @param arr1 - первый массив, учавствующий в слиянии
     * @param arr2 - второй массив, учавствующий в слиянии
     * @return возвращает массив, образованый в результате слияния
     */
    private static int[] merge(int[] arr1, int[] arr2) {
        int[] arr = new int[arr1.length + arr2.length];

        int i1 = 0;
        int i2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i1 == arr1.length) {
                arr[i] = arr2[i2++];
            } else if (i2 == arr2.length) {
                arr[i] = arr1[i1++];
            } else {
                arr[i] = (arr1[i1] < arr2[i2]) ? arr1[i1++] : arr2[i2++];
            }
        }
        return arr;
    }
}