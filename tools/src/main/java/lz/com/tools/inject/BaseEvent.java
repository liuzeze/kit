package lz.com.tools.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface BaseEvent {
    //set方法
    String eventMethod();

    //传入类型
    Class<?> eventMethodType();

    //回调方法
    String eventBackMethod();
}
