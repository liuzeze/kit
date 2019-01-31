package lz.com.kit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-30       创建class
 */
public class MyTextview extends TextView {
    public MyTextview(Context context) {
        super(context);
    }

    public MyTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    String text = "d都发给大家f";
    String textY = "Y1232";

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        paint.setColor(Color.parseColor("#333333"));
        if (getMeasuredWidth() >= paint.measureText(text)) {
            canvas.drawText(text, 0, paint.getTextSize(), paint);
            paint.setColor(Color.parseColor("#ff0000"));
            canvas.drawText(textY,0, paint.getTextSize() * 2, paint);
        } else {

            StringBuffer stringBuffer = new StringBuffer();
            String text2 = "";
            for (int i = 0; i < text.length(); i++) {
                float v = paint.measureText(text.substring(0, i));
                if (v >= getMeasuredWidth()) {
                    stringBuffer.append(text.substring(0, i - 1));
                    text2 = text.substring(i - 1);
                    break;
                }
            }
            canvas.drawText(stringBuffer.toString(), 0, paint.getTextSize(), paint);



            String s = text2 + textY;
            float s1 = paint.measureText(s);
            if (getMeasuredWidth() >= s1) {
                paint.setColor(Color.parseColor("#333333"));
                canvas.drawText(text2, 0, paint.getTextSize() * 2, paint);
                paint.setColor(Color.parseColor("#ff0000"));
                canvas.drawText(textY, paint.measureText(text2), paint.getTextSize() * 2, paint);
            } else {
                s = text2;
                float y1 = paint.measureText("..." + textY);
                float v1 = getMeasuredWidth() - y1;


                for (int i = 0; i < s.length(); i++) {
                    float v = paint.measureText(s.substring(0, i));
                    if (v >= v1) {
                        s = s.substring(0, i - 1) + "...";
                        break;
                    }
                }
                paint.setColor(Color.parseColor("#333333"));
                canvas.drawText(s, 0, paint.getTextSize() * 2, paint);
                paint.setColor(Color.parseColor("#ff0000"));
                canvas.drawText(textY, paint.measureText(s), paint.getTextSize() * 2, paint);
            }

        }



    }

}
