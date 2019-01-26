package stc.lesson6;

public class Main {
    public static void main(String[] args) {
        final String OBJECT_STORAGE = "./target/out/";

        TestClass tc = new TestClass(1, 2, 3d, (byte) 4, "HELLO");
        try {
            Serializer.serialize(tc, OBJECT_STORAGE);

            TestClass tc1 = (TestClass) Serializer.deSerialize(OBJECT_STORAGE + TestClass.class.getSimpleName() + ".txt");
            System.out.println(tc1.getL() + " " +
                    tc1.getI() + " " +
                    tc1.getD() + " " +
                    tc1.getB() + " " +
                    tc1.getS());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
