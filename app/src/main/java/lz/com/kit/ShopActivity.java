package lz.com.kit;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.widget.LinearLayout;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.shopview.SlideLayout;
import lz.com.tools.util.LzWebViewUtils;

/**
 * Created by yc on 2018/7/23.
 */

@InjectLayout(layoutId = R.layout.activity_shop)
public class ShopActivity extends BaseKitActivity {

    @BindView(R.id.slideDetailsLayout)
      SlideLayout mSlideDetailsLayout;
    private ShopMainFragment shopMainFragment;
    @BindView(R.id.wb_view)
     WebView webView;
    @BindView(R.id.ll_detail)
      LinearLayout mLlDetail;


    @Override
    public void initData() {
        initShopMainFragment();
        initSlideDetailsLayout();
        initWebView();
    }




    private void initShopMainFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (shopMainFragment == null) {
            shopMainFragment = new ShopMainFragment();
            fragmentTransaction
                    .replace(R.id.fl_shop_main, shopMainFragment)
                    .commit();
        } else {
            fragmentTransaction.show(shopMainFragment);
        }
    }

    private void initSlideDetailsLayout() {
        mSlideDetailsLayout.setOnSlideDetailsListener(new SlideLayout.OnSlideDetailsListener() {
            @Override
            public void onStatusChanged(SlideLayout.Status status) {
                if (status == SlideLayout.Status.OPEN) {
                    //当前为图文详情页
                    shopMainFragment.changBottomView(true);
                } else {
                    //当前为商品详情页
                    shopMainFragment.changBottomView(false);
                }
            }
        });
    }


    @SuppressLint({"ObsoleteSdkInt", "SetJavaScriptEnabled"})
    private void initWebView() {
        LzWebViewUtils.initWebView(mActivity,webView);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                LzWebViewUtils.loadUrl(webView,"https://github.com/liuzeze");
            }
        });
    }


}
