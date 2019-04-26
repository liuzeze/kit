package lz.com.kit.mvp;

import android.widget.TextView;

import com.lz.fram.scope.AttachView;

import butterknife.BindView;
import butterknife.OnClick;
import lz.com.kit.BaseKitActivity;
import lz.com.kit.R;
import lz.com.tools.inject.LayoutId;

/**
 * Created by yc on 2018/7/23.
 */

@LayoutId(value = R.layout.activity_mvp, titleName = "mvp框架")
public class MvpActivity extends BaseKitActivity implements MvpContract.View {


    @AttachView
    MvpPresenter mMvpPresenter;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    public void initData() {

    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        mMvpPresenter.getNewLists("推荐");
    }

    @Override
    public void getNewsListSuccess(String s) {
        tv.setText(s);
    }
}
