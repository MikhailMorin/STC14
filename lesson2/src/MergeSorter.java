import java.util.Arrays;

/**
 * Утилитный класс для осуществления сортировки слиянием.
 *
 * @author Михаил Морин
 */
abstract class MergeSorter {
    /**
     * Метод сортировка элементов массива
     *
     * @param arr - сортируемый массив
     * @return возвращает отсортированный массив
     * @see MergeSorter#merge(Integer[], Integer[])
     */
    public static Integer[] sort(Integer[] arr) {
        if (arr.length < 2)
            return arr;

        int l = arr.length / 2;
        Integer[] arr_1 = Arrays.copyOfRange(arr, 0, l);
        Integer[] arr_2 = Arrays.copyOfRange(arr, l, arr.length);

        return merge(sort(arr_1), sort(arr_2));
    }

    /**
     * Слияние элементов.
     *
     * @param arr_1 - первый массив, учавствующий в слиянии
     * @param arr_2 - второй массив, учавствующий в слиянии
     * @return возвращает массив, образованый в результате слияния
     */
    private static Integer[] merge(Integer[] arr_1, Integer[] arr_2) {
        Integer[] arr = new Integer[arr_1.length + arr_2.length];

        int i1 = 0;
        int i2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i1 == arr_1.length) {
                arr[i] = arr_2[i2++];
            } else if (i2 == arr_2.length) {
                arr[i] = arr_1[i1++];
            } else {
                arr[i] = (arr_1[i1] < arr_2[i2]) ? arr_1[i1++] : arr_2[i2++];
            }
        }
        return arr;
    }
}