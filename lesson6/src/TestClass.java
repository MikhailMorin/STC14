/**
 * Тестовый класс.
 * Содержит поля со всеми возможными модификаторами доступа,
 * что позволит протестировать сериализацию/десериализацию.
 * Содержит геттеры и сеттеры для всех полей.
 *
 * @author Михаил Морин
 */
public class TestClass {
    public TestClass() {
    }

    public TestClass(long l, int i, double d, byte b, String s) {
        this.l = l;
        this.i = i;
        this.d = d;
        this.b = b;
        this.s = s;
    }

    private long l;
    protected int i;
    public double d;
    byte b;
    private String s;

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
