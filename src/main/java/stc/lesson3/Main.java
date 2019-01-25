package stc.lesson3;

/**
 * Класс содержит вызов статического метода утилитного класса {@code MergeSorter#merge(Integer[] arr_1, Integer[] arr_2)}
 * для выполнения сортировки переданного в качестве аргумента массива
 *
 * @author Михаил Морин
 */
public class Main {
    public static void main(String[] args) {
        Integer[] arr = {1, 2, 4, 5, 2, 67, 2};
        MathBox mb = new MathBox(arr);
        System.out.println(mb);
        System.out.println(mb.summator());
        System.out.println(mb.splitter(3));
        System.out.println();

        Byte[] arr1 = {1, 2, 4, 5, 2, 67, 2,};
        MathBox mb1 = new MathBox(arr1);
        System.out.println(mb1);
        System.out.println(mb1.summator());
        System.out.println(mb1.splitter(3));
        System.out.println();

        Double[] arr2 = {1.0, 2.0, 4.0, 5.0, 2.0, 67.0, 2.1};
        MathBox mb2 = new MathBox(arr2);
        System.out.println(mb2);
        System.out.println(mb2.summator());
        System.out.println(mb2.splitter(2));
        System.out.println();

    }
}
