package lz.com.tools.inject;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-03       创建class
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@BaseEvent(eventMethod = "setOnClickListener",eventMethodType = View.OnClickListener.class,eventBackMethod = "onClick")
public @interface OnClick {
    int[] value();

}
