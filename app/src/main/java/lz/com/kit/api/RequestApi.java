package lz.com.kit.api;


import com.lz.fram.base.LpLoadDialog;
import com.lz.fram.net.RxRequestUtils;
import com.lz.fram.observer.Transformer;

import io.reactivex.Observable;
import lz.com.tools.util.LzUtil;

/**
 * 网络请求
 */
public class RequestApi {

    private final LpLoadDialog mLpLoadDialog;

    public RequestApi() {
        mLpLoadDialog = new LpLoadDialog(LzUtil.getApp());
    }

    public Observable<String> getNewLists(String category, String time) {


        return
                RxRequestUtils
                        .create(ApiService.class)
                        .getNewsArticle2(category, time)
                        .compose(Transformer.switchSchedulersObser(mLpLoadDialog));


    }

}
