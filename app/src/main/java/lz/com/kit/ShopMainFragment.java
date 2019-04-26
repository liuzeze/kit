package lz.com.kit;


import android.widget.TextView;

import butterknife.BindView;
import lz.com.tools.inject.InjectView;
import lz.com.tools.inject.LayoutId;

@LayoutId(value = R.layout.include_shop_main)
public class ShopMainFragment extends BaseKitFragment {


    @BindView(R.id.tv_bottom_view)
     TextView mTvBottomView;


    public void changBottomView(boolean isDetail) {
        if (isDetail) {
            mTvBottomView.setText("下拉回到商品详情");
        } else {
            mTvBottomView.setText("继续上拉，查看图文详情");
        }
    }

    @Override
    protected void initData() {

    }


}
