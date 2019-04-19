package lz.com.kit;

import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lz.com.kit.bean.SelectBean;
import lz.com.tools.dialog.LzDialogUtils;
import lz.com.tools.dialog.LzBottomDialog;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.inject.OnClick;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.checked.CheckHelper;
import lz.com.tools.recycleview.checked.SingleUnCancelCheckedHelper;

@LayoutId(R.layout.activity_dialog)
public class DialogActivity extends BaseActivity {

    @OnClick({R.id.show, R.id.show1, R.id.show2, R.id.show3, R.id.show4, R.id.show5})
    public void onShow(View view) {
        switch (view.getId()) {
            case R.id.show:
                LzDialogUtils.alertDialog(this, "标题", "");
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
                LzDialogUtils.alertDialog(this, "", "内容", null, new View.OnClickListener() {
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
                LzDialogUtils.alertBottomDialog(this, getListView(), null, null, null, null);
                break;
            case R.id.show5:


                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(getListView());
                bottomSheetDialog.show();


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
        SingleUnCancelCheckedHelper<SelectBean> checkedHelper = new SingleUnCancelCheckedHelper<>();
        checkedHelper.setOnCheckedListener(
                new CheckHelper.OnCheckedListener<SelectBean, BaseViewHolder>() {

                    @Override
                    public void onChecked(BaseViewHolder holder, SelectBean obj) {
                        holder.itemView.setBackgroundColor(0xFF73E0E4);
                        holder.setChecked(R.id.checkbox, true);
                    }

                    @Override
                    public void onUnChecked(BaseViewHolder holder, SelectBean obj) {
                        holder.itemView.setBackgroundColor(0xFFFFFFFF);
                        holder.setChecked(R.id.checkbox, false);
                    }

                    @Override
                    public void onSelectitem(List<SelectBean> itemLists) {
                    }
                });

        //添加默认选中数据
        checkedHelper.setUnCancelItem(mStrings.get(0));
        mAdapter.setCheckHelper(checkedHelper);
        return recyclerView;
    }


}
