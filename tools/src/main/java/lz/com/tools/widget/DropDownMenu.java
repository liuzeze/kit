package lz.com.tools.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lz.com.tools.R;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-11-12       下拉筛选
 */
public class DropDownMenu extends LinearLayout {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器，包含popupMenuViews，maskView
    private FrameLayout containerView;
    //弹出菜单父布局
    private FrameLayout popupMenuViews;
    //遮罩半透明View，点击可关闭DropDownMenu
    private View maskView;
    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;

    //分割线颜色
    private int dividerColor = 0xffcccccc;
    //是否显示分割线
    private boolean showDividerColor = true;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;

    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;

    private int menuTitleHeigh = 50;
    private OnDropDownMenuListener mDropDownMenuListener;


    public DropDownMenu(Context context) {
        super(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);

        //为DropDownMenu添加自定义属性
        int menuBackgroundColor = 0xffffffff;
        int underlineColor = 0xffcccccc;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        underlineColor = a.getColor(R.styleable.DropDownMenu_underlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.DropDownMenu_separationDividerColor, dividerColor);
        showDividerColor = a.getBoolean(R.styleable.DropDownMenu_showSeparationDivider, showDividerColor);
        textSelectedColor = a.getColor(R.styleable.DropDownMenu_textSelectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.DropDownMenu_textUnselectedColor, textUnselectedColor);

        menuBackgroundColor = a.getColor(R.styleable.DropDownMenu_menuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.DropDownMenu_maskColor, maskColor);
        menuSelectedIcon = a.getResourceId(R.styleable.DropDownMenu_menuSelectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a.getResourceId(R.styleable.DropDownMenu_menuUnselectedIcon, menuUnselectedIcon);
        menuTitleHeigh = a.getDimensionPixelSize(R.styleable.DropDownMenu_menuTitleHeight, menuTitleHeigh);
        a.recycle();

        //初始化tabMenuView并添加到tabMenuView
        tabMenuView = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, menuTitleHeigh);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setBackgroundColor(menuBackgroundColor);
        tabMenuView.setLayoutParams(params);
        addView(tabMenuView, 0);

        if (showDividerColor) {
            tabMenuView.setShowDividers(SHOW_DIVIDER_MIDDLE);
            tabMenuView.setDividerPadding(menuTitleHeigh / 3);
            Drawable drawable = getResources().getDrawable(R.drawable.shape_horizontal_line);
            drawable.setTint(dividerColor);
            tabMenuView.setDividerDrawable(drawable);
        }

        //为tabMenuView添加下划线
        View underLine = new View(getContext());
        underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(0.5f)));
        underLine.setBackgroundColor(underlineColor);
        addView(underLine, 1);

        //初始化containerView并将其添加到DropDownMenu
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(containerView, 2);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 3) {
            View contentView = getChildAt(3);
            if (containerView != null) {
                ViewGroup parent = (ViewGroup) contentView.getParent();
                if (parent != null) {
                    parent.removeView(contentView);
                }
                containerView.removeView(contentView);
                containerView.addView(contentView, 0);
            }
        }

    }


    /**
     * 初始化DropDownMenu
     *
     * @param popupView
     */
    public void setDropDownMenu(@NonNull View... popupView) {
        setDropDownMenu(Arrays.asList(popupView));
    }

    public void setDropDownMenu(@NonNull List<View> popupViews) {
        if (maskView != null) {
            containerView.removeView(maskView);
        }
        if (popupMenuViews != null) {
            containerView.removeView(popupMenuViews);
            popupMenuViews.removeAllViews();
        }

        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        containerView.addView(maskView);
        maskView.setVisibility(GONE);


        popupMenuViews = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, dpTpPx(50));
        popupMenuViews.setLayoutParams(params);
        popupMenuViews.setVisibility(GONE);
        containerView.addView(popupMenuViews);

        for (int i = 0; i < popupViews.size(); i++) {
            popupViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            popupMenuViews.addView(popupViews.get(i), i);
        }

    }

    private ArrayList<TextView> mTitleArray = new ArrayList<>();

    public void addTab(@NonNull String[] tabTexts) {
        mTitleArray.clear();
        tabMenuView.removeAllViews();
        for (int i = 0; i < tabTexts.length; i++) {
            final LinearLayout inflate = (LinearLayout) View.inflate(getContext(), R.layout.filter_title_item, null);
            TextView tvTitle = inflate.findViewById(R.id.tv_title);
            mTitleArray.add(tvTitle);
            inflate.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            tvTitle.setText(tabTexts[i]);
            inflate.setSelected(false);
            inflate.setTag(false);
            inflate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchMenu(inflate);
                }
            });
            tabMenuView.addView(inflate);
        }
    }

    public void closeMenu() {
        if (current_tab_position != -1) {
            TextView textView = mTitleArray.get(current_tab_position);
            textView.setTextColor(textUnselectedColor);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(menuUnselectedIcon), null);
            tabMenuView.getChildAt(current_tab_position).setSelected((Boolean) tabMenuView.getChildAt(current_tab_position).getTag());

            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_out));
            popupMenuViews.setVisibility(View.GONE);
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.mask_out);
            maskView.animate().alpha(0).setDuration(250).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (mDropDownMenuListener != null) {
                        mDropDownMenuListener.onMenuClose(current_tab_position);
                    }
                    maskView.setVisibility(GONE);
                    current_tab_position = -1;
                }
            }).start();


        }

    }


    /**
     * DropDownMenu是否处于可见状态
     *
     * @return
     */
    public boolean isShowing() {
        return current_tab_position != -1;
    }

    public void openMenu(int position) {
        try {
            View childAt = tabMenuView.getChildAt(position);
            switchMenu(childAt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTabSelect(int position, String text) {
        try {
            TextView textView = mTitleArray.get(position);
            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
                tabMenuView.getChildAt(current_tab_position).setTag(true);
                tabMenuView.getChildAt(current_tab_position).setSelected(true);
            }
            textView.setTextColor(textSelectedColor);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(menuSelectedIcon), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 切换菜单
     *
     * @param target
     */
    private void switchMenu(View target) {
        if (popupMenuViews == null) {
            return;
        }
        for (int i = 0; i < tabMenuView.getChildCount(); i++) {
            if (target == tabMenuView.getChildAt(i)) {
                if (current_tab_position == i) {
                    closeMenu();
                } else {
                    if (current_tab_position == -1) {
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_in));
                        popupMenuViews.setVisibility(View.VISIBLE);
                        maskView.animate().alpha(1).setDuration(300).setListener(null).start();
                        maskView.setVisibility(VISIBLE);

                    }
                    popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);
                    current_tab_position = i;
                    mTitleArray.get(i).setTextColor(textSelectedColor);
                    tabMenuView.getChildAt(i).setSelected(true);
                    if (!TextUtils.isEmpty(mTitleArray.get(i).getText().toString().trim())) {
                        ((mTitleArray.get(i))).setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(menuSelectedIcon), null);
                    }
                }
            } else {
                if (!TextUtils.isEmpty(mTitleArray.get(i).getText().toString().trim())) {
                    mTitleArray.get(i).setCompoundDrawablesWithIntrinsicBounds(null, null,
                            getResources().getDrawable(menuUnselectedIcon), null);
                }
                mTitleArray.get(i).setTextColor(textUnselectedColor);
                tabMenuView.getChildAt(i).setSelected((Boolean) tabMenuView.getChildAt(i).getTag());
                popupMenuViews.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }


    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }


    public void setOnDropDownMenuListener(OnDropDownMenuListener dropDownMenuListener) {
        mDropDownMenuListener = dropDownMenuListener;
    }

    public interface OnDropDownMenuListener {
        void onMenuClose(int position);

    }
}
