import java.util.Arrays;

/**
 * Класс содержит вызов статического метода утилитного класса {@code MergeSorter#merge(Integer[] arr_1, Integer[] arr_2)}
 * для выполнения сортировки переданного в качестве аргумента массива
 *
 * @author Михаил Морин
 */
public class Main {
    public static void main(String[] args) {
        Integer[] arr = {2, 1, 0, 6, 1, 9, 8, 7}; //сортируемый массив
        Integer[] sortArr = MergeSorter.sort(arr); //сортировка
        System.out.println(Arrays.toString(sortArr));// вывод отсортированного массива
    }
}