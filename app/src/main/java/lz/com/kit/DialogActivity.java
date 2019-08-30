package lz.com.kit;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

import butterknife.OnClick;
import lz.com.kit.bean.SelectBean;
import lz.com.tools.dialog.LzPopWindow;
import lz.com.tools.dialog.LzAlertDialog;
import lz.com.tools.dialog.LzBottomDialog;
import lz.com.tools.dialog.LzDialogUtils;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.CheckHelper;
import lz.com.tools.recycleview.checked.SingleCheckedHelper;

@InjectLayout(layoutId = R.layout.activity_dialog)
public class DialogActivity extends BaseKitActivity {

    @OnClick({R.id.show, R.id.show1, R.id.show2, R.id.show3, R.id.show4, R.id.show5, R.id.show6, R.id.show7, R.id.show8})
    public void onShow(View view) {
        switch (view.getId()) {
            case R.id.show:
                LzAlertDialog alertDialog = LzDialogUtils.alertConfirmDialog(this, "标题", "");
                break;
            case R.id.show1:

                View inflate = View.inflate(this, R.layout.layout_dialog, null);
                LzDialogUtils.alertViewDialog(this, inflate, null, null, null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.show2:
                LzDialogUtils.alertConfirmDialog(this, " ", "内容", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.show3:
                LzDialogUtils.alertContentDialog(this, "内容", null, null, null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.show4:
                LzBottomDialog dialog = LzDialogUtils.alertBottomDialog(this, "标题", getListView(), null, null, null, null);
                break;
            case R.id.show5:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(getListView());
                bottomSheetDialog.show();

                break;
            case R.id.show6:


                View inflate1 = View.inflate(this, R.layout.layout_dialog, null);
                LzDialogUtils.alertViewDialog(this, inflate1);
                break;
            case R.id.show7:
                LzDialogUtils.alertTimetDialog(this, "标题", "定时4秒之后关闭当前对话框", 4000);

                break;
            case R.id.show8:
                inflate = View.inflate(mActivity, R.layout.layout_dialog, null);
                LzPopWindow customPopWindow = new LzPopWindow.PopupWindowBuilder(mActivity)
                        .setView(inflate)
                        .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        .setBgDarkAlpha(0.5f)
                        .enableBackgroundDark(true)
                        .setClippingEnable(false)
                        .setFocusable(true)
                        .setOutsideTouchable(true)
                        .create();

                customPopWindow.showAtLocation(view, Gravity.RIGHT, -0, 0);
                break;

            default:

                break;

        }
    }

    private ReboundReyclerView getListView() {
        ReboundReyclerView recyclerView = new ReboundReyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<SelectBean> mStrings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            SelectBean selectBean = new SelectBean();
            selectBean.name = "位置+" + i;
            mStrings.add(selectBean);
        }
        SelectBean selectBean = new SelectBean();
        selectBean.name = "不限+";
        mStrings.add(0, selectBean);
        BaseRecycleAdapter mAdapter = new BaseRecycleAdapter<SelectBean, BaseViewHolder>(R.layout.item_text_select) {

            @Override
            protected void onBindView(BaseViewHolder holder, SelectBean item) {
                holder.setText(R.id.tv_1, item.name);

            }
        };

        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mStrings);
        SingleCheckedHelper<SelectBean> checkedHelper = new SingleCheckedHelper<>();
        checkedHelper.setOnCheckedListener(
                (CheckHelper.OnCheckedListener<SelectBean, BaseViewHolder>) (holder, obj, isChecked) -> {
                    holder.itemView.setBackgroundColor(isChecked ? 0xFF73E0E4 : 0xffffffff);
                    holder.setChecked(R.id.checkbox, isChecked);
                });

        //添加默认选中数据
        checkedHelper.setAlwaysSelectItem(mStrings.get(0));
        mAdapter.setCheckHelper(checkedHelper);
        return recyclerView;
    }


    @Override
    protected void initData() {

    }
}
