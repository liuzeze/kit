package lz.com.kit.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import lz.com.kit.R;

public class BolangAnim extends View {


    public BolangAnim(Context context) {
        this(context, null);
    }

    public BolangAnim(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BolangAnim(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setFocusable(true);

        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.lizi);

        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();

        int index = 0;
        //算出所有端点的原始坐标
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bitmapWidth * x / WIDTH;

                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy + 100;

                index += 1;
            }
        }

        time = 0;                //记录绘制的时间
        period = 4000;        //周期为2000毫秒
        amplitude = 50;        //振幅为50px
        preTime = System.currentTimeMillis();
        setBackgroundColor(Color.BLACK);
    }


    private final int WIDTH = 200;  //X轴方向的块数
    private final int HEIGHT = 200; //Y轴方向的块数
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);  //总的端点数
    private float[] verts = new float[COUNT * 2]; //储存所有端点的坐标值
    private float[] orig = new float[COUNT * 2];  //储存所有端点在图片上对应的原始坐标
    private Bitmap bitmap;
    private int time;
    private int period;
    private int amplitude;
    private long preTime;


    @Override
    protected void onDraw(Canvas canvas) {
        long current = System.currentTimeMillis();
        time += (int) (current - preTime);        //算出两次绘制的时间差，并累加到time上
        preTime = current;
        time %= period;                        //让time对周期去余数，得到当前处于一个周期的时间
        flagWave();                                //计算每个顶点的坐标

        canvas.save();
        Camera camera = new Camera();
        camera.save(); // 保存 Camera 的状态
        camera.setLocation(0,0,80);
        camera.rotateX(40); // 旋转 Camera 的三维空间
        camera.rotateZ(30);
        camera.rotateY(30);
        canvas.translate(0, HEIGHT); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(0, HEIGHT); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore(); // 恢复 Camera 的状态
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        canvas.restore();

        invalidate();
    }

    private void flagWave() {
        for (int j = 0; j <= HEIGHT; j++) {
            for (int i = 0; i <= WIDTH; i++) {
                //每个点的X轴坐标不变，Y轴坐标按照正弦波形变化，注意X坐标对应的索引值，这是最关键的代码

                int i1 = (int) (HEIGHT *1.5f/ 4);
                int i2 = WIDTH * 2 / 4;

                int i5 = (int) (HEIGHT * 2 / 4);
                int i6 = WIDTH * 2 / 4;


                int i3 = (int) (HEIGHT  / 2);
                int i4 = WIDTH / 2;
                verts[(j * (WIDTH + 1) + i) * 2 + 0] += 0;

                if (j > i1 - 50 && j < i1 + 45 && i < i2 + 50 && i > i2 - 50) {
                    verts[(j * (WIDTH + 1) + i) * 2 + 1] = orig[(j * WIDTH + i) * 2 + 1] +
                            (float) Math.sin(i * 2.0 / WIDTH * 3.1416 + time * 2 * 3.1416 / period) * (amplitude - (i - i1));
                } else if (j > i3 - 20 && j < i3 + 20 && i < i4 + 20 && i > i4 - 20) {
                    verts[(j * (WIDTH + 1) + i) * 2 + 1] = orig[(j * WIDTH + i) * 2 + 1] +
                            (float) Math.sin(i * 2.0 / WIDTH * 3.1416 + time * 2 * 3.1416 / period) * (amplitude -(i - i4));
                }  else if (j > i5 - 20 && j < i5 + 20 && i < i6 + 20 && i > i6 - 20) {
                    verts[(j * (WIDTH + 1) + i) * 2 + 1] = orig[(j * WIDTH + i) * 2 + 1] +
                            (float) Math.sin(i * 2.0 / WIDTH * 3.1416 + time * 2 * 3.1416 / period) * (amplitude -(i - i6));
                } else {

                }

                    verts[(j * (WIDTH + 1) + i) * 2 + 1] = orig[(j * WIDTH + i) * 2 + 1] +
                            (float) Math.cos(i * 1.0 / WIDTH * 3.1416 + time * 1 * 3.1416 / period) * amplitude;


            }
        }
    }

    private void touchwrap(float x, float y) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            //x/y轴每个点坐标与当前x/y坐标的距离
            float dx = x - orig[i + 0];
            float dy = y - orig[i + 1];
            float dd = dx * dx + dy * dy;
            //计算每个坐标点与当前点（x、y）之间的距离
            float d = (float) Math.sqrt(dd);
            //计算扭曲度，距离当前点越远的点扭曲度越小
            float pull = 80000 / ((float) (dd * d));
            //对verts重新赋值
            if (pull >= 1) {
                verts[i + 0] = x;
                verts[i + 1] = y;
            } else {
                verts[i + 0] = orig[i + 0] + dx * pull;
                verts[i + 1] = orig[i + 1] + dy * pull;
            }

        }
        invalidate();

    }

}