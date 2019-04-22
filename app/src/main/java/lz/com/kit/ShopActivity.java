package lz.com.kit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import androidx.annotation.ContentView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import lz.com.tools.inject.InjectView;
import lz.com.tools.inject.LayoutId;
import lz.com.tools.shopview.SlideLayout;

/**
 * Created by yc on 2018/7/23.
 */

@LayoutId(R.layout.activity_shop)
public class ShopActivity extends BaseActivity {

    @InjectView(R.id.slideDetailsLayout)
    private  SlideLayout mSlideDetailsLayout;
    private ShopMainFragment shopMainFragment;
    @InjectView(R.id.wb_view)
    private WebView webView;
    @InjectView(R.id.ll_detail)
    private  LinearLayout mLlDetail;


    @Override
    public void init() {
        super.init();
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
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("https://www.jianshu.com/p/d745ea0cb5bd");
            }
        });
    }


}
