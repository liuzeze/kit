package lz.com.tools.inject;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lz.com.tools.R;
import lz.com.tools.toolbar.TitleToolbar;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */
public class InjectManager {


    /**
     * fragment  view 里注册
     *
     * @param target
     */
    public static int getLayoutId(Object target) {
        Class<?> aClass = target.getClass();
        InjectLayout layoutId = aClass.getDeclaredAnnotation(InjectLayout.class);
        if (layoutId != null) {
            return layoutId.layoutId();
        }


        return -1;
    }

    public static void inject(Object target, View inflate) {
        initInjectFragmetnField(inflate, target);
        initInjectFragmentEvent(target, inflate);
    }

    private static void initInjectFragmetnField(View view, Object target) {
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            InjectView annotation = field.getAnnotation(InjectView.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    field.set(target, view.findViewById(annotation.value()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Activity注册
     *
     * @param target
     */
    public static void getLayoutId(Activity target) {
        Class<? extends Activity> aClass = target.getClass();

        initInjectLayout(target, aClass);
        //initInjectField(target, aClass);
        // initInjectEvent(target, aClass);
    }

    private static void initInjectFragmentEvent(Object target, View view) {
        Method[] declaredMethods = target.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                BaseEvent baseEvent = annotationType.getAnnotation(BaseEvent.class);
                if (baseEvent != null) {
                    String eventMethods = baseEvent.eventMethod();
                    Class<?> eventMethodType = baseEvent.eventMethodType();
                    String backMethod = baseEvent.eventBackMethod();
                    try {
                        Method value = annotationType.getMethod("layoutId");
                        int[] values = (int[]) value.invoke(annotation);

                        ListenerInvocationHandler invocationHandler = new ListenerInvocationHandler(target);
                        invocationHandler.setMetond(backMethod, method);
                        Object listener = Proxy.newProxyInstance(eventMethodType.getClassLoader(), new Class[]{eventMethodType},
                                invocationHandler);

                        for (int viewId : values) {
                            View view1 = view.findViewById(viewId);
                            Method eventMethod = view1.getClass().getMethod(eventMethods, eventMethodType);
                            eventMethod.invoke(view1, listener);
                        }


                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    private static void initInjectEvent(Activity target, Class<? extends Object> aClass) {
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                BaseEvent baseEvent = annotationType.getAnnotation(BaseEvent.class);
                if (baseEvent != null) {
                    String eventMethods = baseEvent.eventMethod();
                    Class<?> eventMethodType = baseEvent.eventMethodType();
                    String backMethod = baseEvent.eventBackMethod();
                    try {
                        Method value = annotationType.getMethod("layoutId");
                        int[] values = (int[]) value.invoke(annotation);

                        ListenerInvocationHandler invocationHandler = new ListenerInvocationHandler(target);
                        invocationHandler.setMetond(backMethod, method);
                        Object listener = Proxy.newProxyInstance(eventMethodType.getClassLoader(), new Class[]{eventMethodType},
                                invocationHandler);

                        for (int viewId : values) {
                            View view = target.findViewById(viewId);
                            Method eventMethod = view.getClass().getMethod(eventMethods, eventMethodType);
                            eventMethod.invoke(view, listener);
                        }


                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }


    private static void initInjectField(Activity target, Class<? extends Activity> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            InjectView annotation = field.getAnnotation(InjectView.class);
            if (annotation != null) {
                View view = target.findViewById(annotation.value());

//                    Method method = aClass.getDeclaredMethod("findViewById", int.class);
//                    Object view = method.invoke(target, annotation.layoutId());

                try {
                    field.setAccessible(true);
                    field.set(target, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void initInjectLayout(Activity target, Class<? extends Activity> aClass) {
        InjectLayout injectLayout = aClass.getDeclaredAnnotation(InjectLayout.class);
        if (injectLayout != null) {
            int value = injectLayout.layoutId();
            if (injectLayout.isShowActTitle()) {
                LinearLayout root = (LinearLayout) target.getLayoutInflater().inflate(R.layout.layout_root, null);
                TitleToolbar titleToolbar = root.findViewById(R.id.common_toolbar);
                titleToolbar.setTitle(injectLayout.titleName());
                titleToolbar.setBackVisible(injectLayout.isShowBackIcon());
                target.getLayoutInflater().inflate(value, root);
                target.setContentView(root);
            } else {
                target.setContentView(value);
            }
          /*  try {
                Method method = aClass.getDeclaredMethod("injectLayout", int.class);
                method.invoke(target, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/

        }
    }


}
