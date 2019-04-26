package lz.com.kit.mvp;

import com.lz.fram.base.BasePresenter;
import com.lz.fram.base.BaseView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-04-26       创建class
 */
public class MvpContract {

    interface View extends BaseView {
        void getNewsListSuccess(String s);
    }

    interface Presenter extends BasePresenter {
        void getNewLists(String type);
    }
}
