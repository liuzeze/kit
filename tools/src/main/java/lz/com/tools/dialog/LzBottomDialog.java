package lz.com.tools.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import lz.com.tools.R;


/**
 * @author 刘泽
 */
public class LzBottomDialog {
    private LzBottomDialog mLpAlertDialog;
    private Context context;
    private Dialog dialog;
    private LinearLayout ll_btn_group;
    private TextView txt_title;
    private TextView btn_neg;
    private TextView btn_pos;
    private Display display;
    private boolean showTitle = false;
    private boolean showContentView = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean mCancel = true;


    /**
     * 倒计时总时长，默认60000毫秒=60秒
     */
    private int mMillisInFuture = 500;


    /**
     * 倒计时间隔时常，默认1000毫秒=1秒
     */
    private int mCountDownInterval = 1;

    private Timer t;

    private TimerTask tt;
    private FrameLayout mContentLayout;
    private View mView;


    public LzBottomDialog(Context context) {
        this.context = context;
        mLpAlertDialog = this;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public LzBottomDialog builder() {
        // 获取Dialog布局
        mView = LayoutInflater.from(context).inflate(
                R.layout.view_bottom_dialog, null);

        // 获取自定义Dialog布局中的控件
        ll_btn_group = (LinearLayout) mView.findViewById(R.id.ll_btn_group);

        mContentLayout = (FrameLayout) mView.findViewById(R.id.fl_rootview);
        txt_title = (TextView) mView.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.INVISIBLE);
        btn_neg = (TextView) mView.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.INVISIBLE);
        btn_pos = (TextView) mView.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.INVISIBLE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animations);
        dialog.setCanceledOnTouchOutside(true);
        mView.setPadding(0, display.getHeight() / 3, 0, 0);
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && mCancel) {
                    dialog.dismiss();
                }
            }
        });
        return this;
    }

    /**
     * 设置自动关闭对话框的时间
     *
     * @param autoDismissTime 关闭时间长度
     * @return 当前帮助类
     */
    public LzBottomDialog setAutoDismissTime(int autoDismissTime) {
        mMillisInFuture = autoDismissTime;
        startTimer();
        return this;
    }

    public LzBottomDialog setTitle(String title) {
        showTitle = true;
        if (TextUtils.isEmpty(title)) {
            txt_title.setText("请输入提示标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }


    public LzBottomDialog setContentView(View view) {
        showContentView = true;
        if (view == null) {
            showContentView = false;
        } else {
            mContentLayout.addView(view);
        }
        return this;
    }

    public LzBottomDialog setCancelable(boolean cancel) {
        mCancel = cancel;
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 确定按钮自带取消dialog
     *
     * @param text
     * @param listener
     * @return
     */
    public LzBottomDialog setPositiveButton(String text,
                                            final OnClickListener listener) {
        showPosBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    public LzBottomDialog setNegativeButton(String text,
                                            final OnClickListener listener) {
        showNegBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    /**
     * dialog  dismiss监听
     *
     * @param onDismisListener
     */
    public LzBottomDialog setOnDismisListener(DialogInterface.OnDismissListener onDismisListener) {
        if (onDismisListener != null) {
            dialog.setOnDismissListener(onDismisListener);
        }
        return this;
    }


    private void setLayout() {
        //标题
        if (!showTitle) {
            txt_title.setVisibility(View.INVISIBLE);
        } else {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showPosBtn) {
            btn_pos.setVisibility(View.VISIBLE);
        }

        if (showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
        }

        if (!showTitle && !showPosBtn && !showNegBtn) {
            ll_btn_group.setVisibility(View.GONE);
        } else {
            ll_btn_group.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 显示出对话框
     *
     * @return 返回对话框对象
     */
    public LzBottomDialog show() {
        try {
            setLayout();
            dialog.show();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth());
            dialog.getWindow().setAttributes(lp);
            dialog.setContentView(mView);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mLpAlertDialog;
    }


    /**
     * 供外部调用关闭对话框
     */
    public void dismiss() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对话框是否展示
     *
     * @return true-展示中，false-没有展示
     */
    public boolean isShowing() {
        if (dialog != null) {
            dialog.isShowing();
        }
        return false;
    }

    /**
     * 开启时间计时器，一般是调用接口成功后手动调用此方法进行倒计时开启操作
     */
    public void startTimer() {
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };

        t.schedule(tt, 0, mCountDownInterval);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mMillisInFuture -= mCountDownInterval;
            if (mMillisInFuture < 0) {
                dismiss();
                clearTimer();
            }
        }
    };

    /**
     * 释放资源
     */
    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        clearTimer();
    }


    /**
     * 设置确定是否可以点击
     *
     * @param clickable
     */
    public LzBottomDialog setBtnPosClickable(boolean clickable) {
        btn_pos.setClickable(clickable);
        return this;
    }

    /**
     * 设置取消是否可以点击`
     *
     * @param clickable
     */
    public LzBottomDialog setBtnNegClickable(boolean clickable) {
        btn_neg.setClickable(clickable);
        return this;
    }

}
