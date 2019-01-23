import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TestClass tc = new TestClass(1, 2, 3d, (byte) 4, "HELLO");

        Serializer.serialize(tc, "C:\\Users\\Mikhail\\Desktop\\path\\serialize.txt");
        TestClass tc1 = (TestClass) Serializer.deSerialize("C:\\Users\\Mikhail\\Desktop\\path\\serialize.txt");

        System.out.println(tc.getL() + " " + tc.getI() + " " + tc.getD() + " " + tc.getB() + " " + tc.getS());
    }
}
