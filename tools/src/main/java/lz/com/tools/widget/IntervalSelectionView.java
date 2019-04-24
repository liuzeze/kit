package lz.com.tools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.math.BigDecimal;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import lz.com.tools.R;
import lz.com.tools.util.LzDp2Px;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-12-5        区间选择控件
 */

public class IntervalSelectionView extends View {
    private Paint paintBackground;//背景线的画笔
    private TextPaint paintStartText;//起点数值的笔
    private Paint paintEndText;//终点数值的画笔
    private Paint paintResultText;//顶部结果数值的画笔
    private Paint paintConnectLine;//起始点连接线的画笔

    private int height = 0;//控件的高度
    private int width = 0;//控件的宽度

    private float centerVertical = 0;//y轴的中间位置

    private float backLineWidth = 5;//底线的宽度

    private float marginHorizontal = 1;//横向边距

    private float marginTop = 60;//文字距基线顶部的距离
    private float marginBottom = 45;//文字距基线底部的距离

    private float pointStart = 0;//起点的X轴位置

    private float pointEnd = 0;//始点的Y轴位置

    private float circleRadius = 35;//起始点圆环的半径

    private float numStart = 0;//数值的开始值

    private float numEnd = 0;//数值的结束值

    private int textSize = 12;//文字的大小

    private String leftUnit;//左侧单位
    private String rightUnit;//右侧单位

    private String strConnector = " - ";//连接符

    private boolean isRunning = false;//是否可以滑动

    private boolean isStart = true;//起点还是终点 true：起点；false：终点。

    private boolean isInteger = false;//是否保留整形
    private boolean isShowResult = false;//是否显示结果值，默认显示。

    private int precision = 2;//保留精度，默认为2。
    //进度范围
    private float startNum = 0.00F;
    private float endNum = 100.00F;

    private int startValueColor;//开始文字颜色
    private int endValueColor;//终点文字颜色
    private int resultValueColor;//结果值文字颜色

    private int backLineColor;//基线颜色
    private int connectLineColor;//连接线颜色

    private float scaling; //取值比例
    int paddingStart;
    int paddingEnd;

    private boolean isCoincide = true;

    private OnChangeListener mOnChangeListener;
    private int mCircleImgRes;
    private Bitmap mCircleBitmap;
    private String titleText;
    private int titleTextColor;
    private int titleTextSize;
    private Paint paintTitleText;
    private Paint paintTitleResult;

    public IntervalSelectionView(Context context) {
        super(context);
        init();
    }

    public IntervalSelectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public IntervalSelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void handleAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.intervalSelectionView, defStyleAttr, 0);

        backLineColor = ta.getColor(R.styleable.intervalSelectionView_backLineColor, Color.CYAN);
        connectLineColor = ta.getColor(R.styleable.intervalSelectionView_connectLineColor, Color.BLUE);


        startValueColor = ta.getColor(R.styleable.intervalSelectionView_startValueColor, Color.MAGENTA);
        endValueColor = ta.getColor(R.styleable.intervalSelectionView_endValueColor, Color.MAGENTA);
        resultValueColor = ta.getColor(R.styleable.intervalSelectionView_resultValueColor, Color.MAGENTA);
        isShowResult = ta.getBoolean(R.styleable.intervalSelectionView_isShowResult, true);
        isInteger = ta.getBoolean(R.styleable.intervalSelectionView_isInteger, false);
        isCoincide = ta.getBoolean(R.styleable.intervalSelectionView_isCoincide, true);
        precision = ta.getInteger(R.styleable.intervalSelectionView_valuePrecision, 2);
        startNum = ta.getFloat(R.styleable.intervalSelectionView_startValue, startNum);
        endNum = ta.getFloat(R.styleable.intervalSelectionView_endValue, endNum);
        titleText = ta.getString(R.styleable.intervalSelectionView_tipText);
        titleTextColor = ta.getColor(R.styleable.intervalSelectionView_tipTextColor, Color.MAGENTA);
        titleTextSize = ta.getInt(R.styleable.intervalSelectionView_tipTextSize, 18);
        if (ta.getString(R.styleable.intervalSelectionView_leftUnit) != null) {
            leftUnit = ta.getString(R.styleable.intervalSelectionView_leftUnit);
        }
        if (ta.getString(R.styleable.intervalSelectionView_rightUnit) != null) {
            rightUnit = ta.getString(R.styleable.intervalSelectionView_rightUnit);
        }

        mCircleImgRes = ta.getResourceId(R.styleable.intervalSelectionView_circleImg, R.mipmap.seekbar);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取控件的宽高、中线位置、起始点、起始数值
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        paddingStart = getPaddingLeft();
        paddingEnd = getPaddingRight();

        centerVertical = height / 2;

        pointStart = marginHorizontal + paddingStart;
        pointEnd = width - marginHorizontal - paddingEnd - 2 * circleRadius;

    }

    /**
     * 初始化基础值
     */
    private void initBaseData() {
        // （父级控件宽度-左右边距-圆直径）/（结束值-起点值）
        scaling = (width - 2 * marginHorizontal - (paddingStart + paddingEnd) - 3 * circleRadius) / (endNum - startNum);

        pointStart = (numStart - startNum) * scaling + paddingStart + marginHorizontal;
        if (numEnd != 0) {
            pointEnd = (numEnd - startNum) * scaling + paddingStart + marginHorizontal;
        } else {
            pointEnd = width - marginHorizontal - paddingEnd - 2 * circleRadius;
        }

        numStart = getProgressNum(pointStart);
        numEnd = getProgressNum(pointEnd);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //如果点击的点在第一个圆内就是起点,并且可以滑动
                if (event.getX() >= (pointStart) && event.getX() <= (pointStart + 2 * circleRadius)) {
                    isRunning = true;
                    isStart = true;

                    pointStart = event.getX();
                    //如果点击的点在第二个圆内就是终点,并且可以滑动
                } else if (event.getX() <= (pointEnd + 2 * circleRadius) && event.getX() >= (pointEnd)) {
                    isRunning = true;
                    isStart = false;

                    pointEnd = event.getX();
                } else {
                    //如果触控点不在圆环内，则不能滑动
                    isRunning = false;
                }
                getParent().requestDisallowInterceptTouchEvent(isRunning);
                break;
            case MotionEvent.ACTION_MOVE:

                if (isRunning) {
                    if (isStart) {
                        //起点滑动时，重置起点的位置和进度值
                        pointStart = event.getX();
                        if (pointStart < marginHorizontal + paddingStart) {
                            pointStart = marginHorizontal + paddingStart;
                            numStart = startNum;
                        } else {
                            if (pointStart > width - marginHorizontal - paddingEnd - circleRadius) {
                                //防止终点和起点在终点相连时，往右移动，起点点不动，而值减小的问题。
                                pointStart = width - marginHorizontal - paddingEnd - circleRadius;
                            }

                            numStart = getProgressNum(pointStart);
                        }
                    } else {
                        //始点滑动时，重置始点的位置和进度值
                        pointEnd = event.getX();
                        if (pointEnd > width - marginHorizontal - paddingEnd - 3 * circleRadius) {
                            numEnd = endNum;
                        } else if (pointEnd > width - marginHorizontal - paddingEnd - 2 * circleRadius) {
                            pointEnd = width - marginHorizontal - paddingEnd - 2 * circleRadius;
                            numEnd = endNum;
                        } else {
                            if (pointEnd < marginHorizontal + paddingStart - circleRadius) {
                                //防止终点和起点在起始点相连时，往左移动，终点不动，而值减小的问题。
                                pointEnd = marginHorizontal + paddingStart - circleRadius;
                            }
                            numEnd = getProgressNum(pointEnd);
                        }
                    }

                    flushState();//刷新状态
                }

                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);

                flushState();
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 刷新状态和屏蔽非法值
     */
    private void flushState() {

        //起点非法值
        if (pointStart < marginHorizontal + paddingStart) {
            pointStart = marginHorizontal + paddingStart;
        }
        //终点非法值
        if (pointEnd > width - marginHorizontal - paddingEnd - 2 * circleRadius) {
            pointEnd = width - marginHorizontal - paddingEnd - 2 * circleRadius;
        }


        //起点终点位置跟随
        if (pointStart > pointEnd - scaling) {
            if (isStart) {
                pointEnd = pointStart + scaling;
                numEnd = getProgressNum(pointEnd);//更新终点
            } else {
                pointStart = pointEnd - scaling;
                numStart = getProgressNum(pointStart);//更新起点值
            }
        }


        //防止终点把起点推到线性范围之外
        if (pointEnd < marginHorizontal + paddingStart + scaling) {
            pointEnd = marginHorizontal + paddingStart + scaling;
            pointStart = marginHorizontal + paddingStart;
        }


        //防止起点终点话滑出去
        if (pointStart > width - marginHorizontal - paddingEnd - 2 * circleRadius - scaling) {
            pointEnd = width - marginHorizontal - paddingEnd - 2 * circleRadius;
            pointStart = pointEnd - scaling;
        }
        invalidate();//这个方法会导致onDraw方法重新绘制
        if (mOnChangeListener != null) {// call back listener.
            if (isInteger) {
                if (pointEnd > width - marginHorizontal - paddingEnd - 3 * circleRadius + scaling)
                    numEnd = 0;
                mOnChangeListener.leftCursor(String.valueOf(((int) numStart) * 10));
                mOnChangeListener.rightCursor(String.valueOf(((int) numEnd) * 10));
            } else {
                mOnChangeListener.leftCursor(String.valueOf(numStart));
                mOnChangeListener.rightCursor(String.valueOf(numEnd > endNum ? endNum : numEnd));
            }
        }
    }

    //计算进度数值
    private float getProgressNum(float progress) {
        if (progress <= marginHorizontal + paddingStart) {
            // 处理边界问题
            return startNum;
        }
        if (progress > width - marginHorizontal - paddingEnd - 3 * circleRadius) {
            return endNum;
        }
        float v = Math.round((progress - marginHorizontal - paddingStart) / scaling) + startNum;
        return v > endNum ? endNum : v;
    }

    /**
     * 初始化画笔
     */
    private void init() {

        paintBackground = new Paint();
        paintBackground.setColor(backLineColor);
        paintBackground.setStrokeWidth(backLineWidth);
        paintBackground.setAntiAlias(true);


        paintStartText = new TextPaint();
        paintStartText.setColor(startValueColor);
        paintStartText.setTextSize(LzDp2Px.dp2px(getContext(),textSize));
        paintStartText.setAntiAlias(true);

        paintEndText = new Paint();
        paintEndText.setColor(endValueColor);
        paintEndText.setTextSize(LzDp2Px.dp2px(getContext(),textSize));
        paintEndText.setAntiAlias(true);
        paintEndText.setTextAlign(Paint.Align.RIGHT);

        paintResultText = new Paint();
        paintResultText.setColor(resultValueColor);
        paintResultText.setTextSize(textSize);
        paintResultText.setAntiAlias(true);

        paintConnectLine = new Paint();
        paintConnectLine.setColor(connectLineColor);
        paintConnectLine.setStrokeWidth(backLineWidth);
        paintConnectLine.setAntiAlias(true);

        paintTitleText = new Paint();
        paintTitleText.setColor(titleTextColor);
        paintTitleText.setStrokeWidth(backLineWidth);
        paintTitleText.setTextSize(LzDp2Px.dp2px(getContext(),titleTextSize));
        paintTitleText.setFakeBoldText(true);
        paintTitleText.setAntiAlias(true);

        paintTitleResult = new Paint();
        paintTitleResult.setColor(resultValueColor);
        paintTitleResult.setStrokeWidth(backLineWidth);
        paintTitleResult.setTextSize(LzDp2Px.dp2px(getContext(),16));
        paintTitleResult.setFakeBoldText(true);
        paintTitleResult.setAntiAlias(true);


        mCircleBitmap = resizeBitmap(BitmapFactory.decodeResource(getResources(), mCircleImgRes), ((int) (circleRadius * 2)), ((int) (circleRadius * 2)));


    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //背景线
        canvas.drawLine(marginHorizontal + paddingStart, centerVertical, width - marginHorizontal - paddingEnd, centerVertical, paintBackground);

        canvas.drawCircle(marginHorizontal + paddingStart + LzDp2Px.dp2px(getContext(),4), centerVertical, LzDp2Px.dp2px(getContext(),4), paintBackground);
        canvas.drawCircle(width - marginHorizontal - paddingEnd - LzDp2Px.dp2px(getContext(),4), centerVertical, LzDp2Px.dp2px(getContext(),4), paintBackground);
        //起始点连接线
        canvas.drawLine(pointStart, centerVertical, pointEnd, centerVertical, paintConnectLine);


        //下面部分 需要用 图片 绘制
        if (mCircleBitmap != null) {
            canvas.drawBitmap(mCircleBitmap, pointStart, centerVertical - circleRadius, paintConnectLine);
            canvas.drawBitmap(mCircleBitmap, pointEnd, centerVertical - circleRadius, paintConnectLine);
        }


        //计算字体宽度

        Rect rect = new Rect();
        //起点数值
        String text = assembleStartText();
        paintStartText.getTextBounds(text, 0, text.length(), rect);
        //终点数值
        Rect rect1 = new Rect();
        String text1 = assembleEndText();
        paintStartText.getTextBounds(text1, 0, text1.length(), rect1);
        //全部
        String startAndEndText = assembleStartAndEndText();
        Rect rect2 = new Rect();
        paintStartText.getTextBounds(startAndEndText, 0, startAndEndText.length(), rect2);
        int i = !startAndEndText.contains("\n") ? 2 : 4;
        if (pointEnd - pointStart < (rect.width() + rect1.width()) / i) {

            if (startAndEndText.contains("\n")) {
                StaticLayout staticLayout = new StaticLayout(startAndEndText, paintStartText, rect2.width(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                canvas.save();
                canvas.translate(pointStart + (pointEnd - pointStart) / 2 - (rect2.width()) / 3, centerVertical + marginBottom - circleRadius + LzDp2Px.dp2px(getContext(),12));
                staticLayout.draw(canvas);
                canvas.restore();
            } else {
                canvas.drawText(startAndEndText, pointStart + (pointEnd - pointStart) / 2 - (rect.width()) / 2, centerVertical + marginBottom + circleRadius + LzDp2Px.dp2px(getContext(),12), paintStartText);
            }
        } else {


            if (text.contains("\n")) {
                StaticLayout staticLayout = new StaticLayout(text, paintStartText, rect.width(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                canvas.save();
                canvas.translate(pointStart + circleRadius - rect.width() / 2, centerVertical + marginBottom - circleRadius + LzDp2Px.dp2px(getContext(),12));
                staticLayout.draw(canvas);
                canvas.restore();
            } else {
                canvas.drawText(text, pointStart + circleRadius - rect.width() / 2, centerVertical + marginBottom + circleRadius + LzDp2Px.dp2px(getContext(),12), paintStartText);
            }


            if (text1.contains("\n")) {
                StaticLayout staticLayout = new StaticLayout(text1, paintStartText, rect1.width(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                canvas.save();
                canvas.translate(pointEnd + circleRadius - rect1.width() / 2, centerVertical + marginBottom - circleRadius + LzDp2Px.dp2px(getContext(),12));
                staticLayout.draw(canvas);
                canvas.restore();
            } else {
                canvas.drawText(text1, pointEnd + circleRadius + rect1.width() / 2, centerVertical + marginBottom + circleRadius + LzDp2Px.dp2px(getContext(),12), paintEndText);
            }

        }

        if (isShowResult) {
            //结果值
            canvas.drawText(titleText, marginHorizontal + paddingStart, centerVertical - LzDp2Px.dp2px(getContext(),32), paintTitleText);
            canvas.drawText(assembleResultText(), marginHorizontal + paddingStart + LzDp2Px.dp2px(getContext(),38), centerVertical - LzDp2Px.dp2px(getContext(),32), paintTitleResult);
        }
    }

    private String assembleTitleText() {
        StringBuffer sb = new StringBuffer();
        sb.append(titleText);
        return sb.toString();
    }

    /**
     * 处理起点值精度
     */
    private float handleNumStartPrecision(float value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * 处理终点值精度
     */
    private float handleNumEndPrecision(float value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
        return bd.floatValue();
    }

    /**
     * 组装起点文字
     */
    private String assembleStartText() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(leftUnit)) sb.append(leftUnit);
        //必须在此调用String.valueOf()来提前转化为String，否则会因为append()重载而导致整形无效的问题。
        sb.append(isInteger ? String.valueOf(((int) numStart) * 10) : String.valueOf(handleNumStartPrecision(numStart)));
        if (!TextUtils.isEmpty(rightUnit)) sb.append(" ").append(rightUnit);
        return sb.toString();
    }

    /**
     * 组装起点终点文字
     */
    private String assembleStartAndEndText() {

        StringBuilder sb = new StringBuilder();
        if (pointEnd == paddingStart) {
            sb.append("不限");
        } else if (pointEnd > width - marginHorizontal - paddingEnd - 3 * circleRadius + scaling) {
            sb.append((isInteger ? String.valueOf(((int) numStart) * 10) : String.valueOf(handleNumStartPrecision(numStart))));
            if (!TextUtils.isEmpty(rightUnit)) sb.append(rightUnit);
            sb.append(rightUnit.contains("\n")?"以上":"\n以上");
        } else {

            if (!TextUtils.isEmpty(leftUnit)) sb.append(leftUnit);
            //必须在此调用String.valueOf()来提前转化为String，否则会因为append()重载而导致整形无效的问题。
            sb.append(isInteger ? String.valueOf(((int) numStart) * 10) : String.valueOf(handleNumStartPrecision(numStart)));
            sb.append(" - ");

            sb.append(isInteger ? String.valueOf(((int) numEnd) * 10) : String.valueOf(handleNumStartPrecision(numEnd)));
            if (!TextUtils.isEmpty(rightUnit)) sb.append(rightUnit);
        }
        return sb.toString();
    }

    /**
     * 组装终点文字
     */
    private String assembleEndText() {

        StringBuilder sb = new StringBuilder();


        if (pointEnd > width - marginHorizontal - paddingEnd - 3 * circleRadius + scaling) {
            sb.append("不限");
        } else {
            if (!TextUtils.isEmpty(leftUnit)) sb.append(leftUnit);
            sb.append(isInteger ? String.valueOf(((int) numEnd) * 10) : handleNumEndPrecision(numEnd));
            if (!TextUtils.isEmpty(rightUnit)) sb.append(" ").append(rightUnit);
        }
        return sb.toString();
    }

    /**
     * 组装结果值
     */
    private String assembleResultText() {
        StringBuilder sb = new StringBuilder();
        if (pointStart <= paddingStart + marginHorizontal && pointEnd == paddingStart + marginHorizontal
                || pointStart <= paddingStart + marginHorizontal && pointEnd > width - marginHorizontal - paddingEnd - 3 * circleRadius + scaling) {
            sb.append("(不限)");
        } else if (pointEnd > width - marginHorizontal - paddingEnd - 3 * circleRadius + scaling) {
//            sb.append("( 高于");
            if (!TextUtils.isEmpty(leftUnit)) sb.append(leftUnit);
            sb.append("( "+(isInteger ? String.valueOf(((int) numStart) * 10) : handleNumStartPrecision(numStart)));
            if (!TextUtils.isEmpty(rightUnit)) sb.append(rightUnit);
            sb.append("以上");
            sb.append(" )");

        } else {
            if (!TextUtils.isEmpty(leftUnit)) sb.append(leftUnit);
            sb.append("( ").append(isInteger ? String.valueOf(((int) numStart) * 10) : handleNumStartPrecision(numStart));
            sb.append(strConnector);
            if (!TextUtils.isEmpty(leftUnit)) sb.append(leftUnit);
            sb.append(isInteger ? String.valueOf(((int) numEnd) * 10) : handleNumEndPrecision(numEnd));
            if (!TextUtils.isEmpty(rightUnit)) sb.append(rightUnit).append(" )");
        }
        return sb.toString();
    }


    /**
     * 左侧单位
     */
    public IntervalSelectionView setLeftUnit(String leftUnit) {
        this.leftUnit = leftUnit;
        return this;
    }

    /**
     * 右侧单位
     */
    public IntervalSelectionView setRightUnit(String rightUnit) {
        this.rightUnit = rightUnit;
        return this;
    }

    /**
     * 主题名称
     */
    public IntervalSelectionView setTitleText(String titleText) {
        this.titleText = titleText;
        return this;
    }


    public String getResultStartValue() {
        return String.valueOf(numStart);
    }

    public String getResultEndValue() {
        return String.valueOf(endNum);
    }


    /**
     * 是否保留整形
     */
    public IntervalSelectionView setInteger(boolean integer) {
        isInteger = integer;
        return this;
    }

    /**
     * 是否显示结果值，默认显示。
     */
    public IntervalSelectionView setShowResult(boolean showResult) {
        isShowResult = showResult;
        return this;
    }

    /**
     * 保留精度，默认为2。
     */
    public IntervalSelectionView setPrecision(int precision) {
        this.precision = precision;
        return this;
    }

    /**
     * 起始值
     */
    public IntervalSelectionView setStartNum(float startNum) {
        this.startNum = startNum / 10;
        return this;
    }

    /**
     * 结束值
     */
    public IntervalSelectionView setEndNum(float endNum) {
        this.endNum = endNum / 10;
        return this;
    }


    /**
     * 开始文字颜色
     */
    public IntervalSelectionView setStartValueColor(int startValueColor) {
        this.startValueColor = startValueColor;
        return this;
    }

    /**
     * 终点文字颜色
     */
    public IntervalSelectionView setEndValueColor(int endValueColor) {
        this.endValueColor = endValueColor;
        return this;
    }

    /**
     * 结果值文字颜色
     */
    public IntervalSelectionView setResultValueColor(int resultValueColor) {
        this.resultValueColor = resultValueColor;
        return this;
    }

    /**
     * 基线颜色
     */
    public IntervalSelectionView setBackLineColor(int backLineColor) {
        this.backLineColor = backLineColor;
        return this;
    }

    /**
     * 连接线颜色
     */
    public IntervalSelectionView setConnectLineColor(int connectLineColor) {
        this.connectLineColor = connectLineColor;
        return this;
    }


    /**
     * 通知刷新
     */
    public void apply() {
        init();
        initBaseData();
        invalidate();//这个方法会导致onDraw方法重新绘制
    }

    /**
     * 主要充值起点和终点的画笔值
     */
    public IntervalSelectionView reSetValue() {
        pointStart = marginHorizontal + paddingStart + circleRadius;
        pointEnd = width - marginHorizontal - paddingEnd - circleRadius;
        return this;
    }

    public IntervalSelectionView setNumStart(float numStart) {
        this.numStart = numStart / 10;
        return this;
    }

    public IntervalSelectionView setNumEnd(float numEnd) {
        this.numEnd = numEnd / 10;
        return this;
    }


    /**
     * 使用Matrix将Bitmap压缩到指定大小
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }


    public void setOnChangeListener(OnChangeListener onChangeListener) {
        mOnChangeListener = onChangeListener;
    }

    public interface OnChangeListener {
        void leftCursor(String resultValue);

        void rightCursor(String resultValue);
    }

}
