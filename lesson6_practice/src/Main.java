import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        SomeDoer a = new A();
        SomeDoer b = new B();

        SomeDoer proxyA = (SomeDoer) Proxy.newProxyInstance(SomeDoerInvocationHandler.class.getClassLoader(),
                new Class[]{SomeDoer.class}, new SomeDoerInvocationHandler(a));
        SomeDoer proxyB = (SomeDoer) Proxy.newProxyInstance(SomeDoerInvocationHandler.class.getClassLoader(),
                new Class[]{SomeDoer.class}, new SomeDoerInvocationHandler(b));

        proxyA.doSome();
        proxyB.doSome();
    }
}
