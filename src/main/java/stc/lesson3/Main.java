package stc.lesson3;

/**
 * Класс осуществляет тестирование {@code MathBox<>}.
 * Тестируемые методы {@code MathBox#toString()}, {@code MathBox#summator()} {@code MathBox#splitter(int s)}.
 *
 * @author Михаил Морин
 */
public class Main {
    public static void main(String[] args) {
        Integer[] arrInt = {1, 2, 4, 5, 2, 67, 2};
        MathBox<Integer> mbInt = new MathBox<>(arrInt);
        System.out.println(mbInt);
        System.out.println(mbInt.summator());
        System.out.println(mbInt.splitter(3));
        System.out.println();

        Byte[] arrByte = {1, 2, 4, 5, 2, 67, 2,};
        MathBox<Byte> mbByte = new MathBox<>(arrByte);
        System.out.println(mbByte);
        System.out.println(mbByte.summator());
        System.out.println(mbByte.splitter(2));
        System.out.println();

        Double[] arrDouble = {1.0, 2.0, 4.0, 5.0, 2.0, 67.0, 2.1};
        MathBox<Double> mbDouble = new MathBox<>(arrDouble);
        System.out.println(mbDouble);
        System.out.println(mbDouble.summator());
        System.out.println(mbDouble.splitter(4));
        System.out.println();
    }

}
