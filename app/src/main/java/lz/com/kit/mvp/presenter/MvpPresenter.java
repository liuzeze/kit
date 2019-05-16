package lz.com.kit.mvp.presenter;

import com.lz.fram.base.RxPresenter;
import com.lz.fram.observer.CommonSubscriber;

import lz.com.kit.api.RequestApi;
import lz.com.kit.mvp.NewGoodsRequestBean;
import lz.com.tools.util.LzTimeUtils;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-26       创建class
 */
public class MvpPresenter extends RxPresenter<MvpContract.View> implements MvpContract.Presenter {

    private final RequestApi mRequestApi;

    public MvpPresenter() {
        mRequestApi = new RequestApi();
    }

    @Override
    public void getNewLists(String type) {
      /*  CommonSubscriber<String> commonSubscriber = mRequestApi
                .getNewLists(type, LzTimeUtils.getNowMills() / 1000 + "")
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    public void onNext(String s) {
                        mBaseView.getNewsListSuccess(s);
                    }
                });*/
        CommonSubscriber<String> commonSubscriber = mRequestApi
                .findgoods(new NewGoodsRequestBean()
                        .setNumPerPage(10)
                        .setPageNum(1))
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    public void onNext(String s) {
                        mBaseView.getNewsListSuccess(s);
                    }
                });
    }
}
