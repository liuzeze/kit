package lz.com.tools.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * 存取SharedPreferences工具类，存储用户登录账户信息，用户信息，查询记录等
 * Created by donghongyu on 2016/5/30.
 */
public class LzSPUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "lz_data";

    private LzSPUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key 要获取的key
     * @param obj 任意数据类型
     */
    public static void put(String key, Object obj) {

        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, obj.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key        要获取的key
     * @param defaultObj 任意数据类型
     * @return 返回查询到的sp中存在的key对应的数据
     */
    public static Object get(String key, Object defaultObj) {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key 要获取的key
     */
    public static void remove(String key) {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putString(String key, String value) {
        put(key, value);
    }


    public static String getString(String key) {
        return getString(key, "");
    }


    public static String getString(String key, String def) {
        return (String) get(key, def);
    }


    public static void putInt(String key, int value) {
        put(key, value);
    }


    public static int getInt(String key) {
        return getInt(key, -1);
    }


    public static int getInt(String key, int defaultValue) {
        return (int) get(key, defaultValue);
    }


    /**
     * 保存boolean数据的方法
     *
     * @param key
     */
    public static void putBoolean(String key, boolean value) {
        put(key, value);
    }

    /**
     * 获取boolean 方法
     *
     * @param key 要获取的目标key
     * @return
     */
    public static boolean getBoolean(String key) {

        return getBoolean(key, false);
    }

    /**
     * 获取boolean 方法
     *
     * @param key 要获取的目标key
     * @return
     */
    public static boolean getBoolean(String key, boolean def) {

        return (Boolean) get(key, def);
    }


    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key 要获取的key
     * @return true-要查询的key已存在，false-不存在
     */
    public static boolean contains(String key) {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return 返回sp文件所有的键值对应数据
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return Method方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor 编辑器
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public static void setObject(String key, Object object) {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key, Class<T> clazz) {
        SharedPreferences sp = LzUtil.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
