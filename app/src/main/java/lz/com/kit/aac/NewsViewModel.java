package lz.com.kit.aac;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.lz.fram.observer.CommonSubscriber;

import java.util.ArrayList;
import java.util.List;

import lz.com.kit.api.RequestApi;
import lz.com.tools.util.LzTimeUtils;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-23       创建class
 */
public class NewsViewModel extends BaseViewModel {

    private final RequestApi mRequestApi;
    public MutableLiveData<List<NewsDataVo>> mData;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mData = new MutableLiveData<>();
        mRequestApi = new RequestApi();
    }


    /**
     * 模拟获取网络数据
     */
    public void httpGetData(View view) {

        CommonSubscriber<String> commonSubscriber = mRequestApi
                .getNewLists("推荐", LzTimeUtils.getNowMills() / 1000 + "")
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    public void onNext(String s) {
                        ArrayList<NewsDataVo> value = new ArrayList<>();
                        int len = 30;
                        for (int i = 0; i < len; i++) {
                            NewsDataVo dataVo = new NewsDataVo();
                            dataVo.id.set("1223" + i);
                            dataVo.newsTitle.set("Android AccArchitecture框架简介" + i);
                            dataVo.newsContent.set("Android Architecture Components," +
                                    "\n简称 AAC,一个处理UI的生命" +
                                    "\n周期与数据的持久化的架构" + i);
                            dataVo.newsUrl.set("https://upload.jianshu.io/users/upload_avatars/8669504/e210988a-4cea-4310-8b6f-0f8af4ab0ee6.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240");
                            dataVo.readNum.set(i);
                            value.add(dataVo);
                        }
                        mData.setValue(value);
                    }
                });


    }
}