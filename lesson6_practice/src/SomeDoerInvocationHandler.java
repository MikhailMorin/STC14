import java.lang.reflect.*;

public class SomeDoerInvocationHandler implements InvocationHandler {
    SomeDoer someDoer;
    public SomeDoerInvocationHandler(SomeDoer someDoer){
        this.someDoer = someDoer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(someDoer, args);
        if (someDoer.getClass().getAnnotation(Logging.class) != null) {
            long startTime = System.currentTimeMillis();
            System.out.print("method " + method.getName() + " called, ");
            System.out.println("time = " + (System.currentTimeMillis() - startTime));
        }
        return result;
    }
}
