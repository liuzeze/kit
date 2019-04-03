package lz.com.tools.inject;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private final Object mTarget;
    private HashMap<String, Method> mMethodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        mTarget = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (mTarget != null) {
            String name = method.getName();
            Method mth = mMethodHashMap.get(name);
            if (mth != null) {
                return mth.invoke(mTarget, args);
            }
        }
        return method.invoke(proxy, args);
    }

    public void setMetond(String backMethod, Method method) {
        mMethodHashMap.put(backMethod, method);

    }
}
