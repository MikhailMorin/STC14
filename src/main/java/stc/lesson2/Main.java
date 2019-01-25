package stc.lesson2;

import java.util.Arrays;

/**
 * Класс содержит вызов статического метода утилитного класса {@code MergeSorter#sort(int[] arr)}
 * для выполнения сортировки переданного в качестве аргумента массива
 *
 * @author Михаил Морин
 */
public class Main {
    public static void main(String[] args) {
        int[] arr = {2, 1, 0, 6, 1, 9, 8, 7}; //сортируемый массив
        int[] sortArr = MergeSorter.sort(arr); //сортировка
        System.out.println(Arrays.toString(sortArr));// вывод отсортированного массива
    }
}