package lz.com.kit.mvp;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lz.fram.scope.AttachPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import lz.com.kit.BaseKitActivity;
import lz.com.kit.R;
import lz.com.kit.mvp.presenter.MvpContract;
import lz.com.kit.mvp.presenter.MvpPresenter;
import lz.com.tools.inject.InjectLayout;


/**
 * @author Administrator
 */
@InjectLayout(layoutId = R.layout.activity_mvp, titleName = "mvp框架")
public class MvpActivity extends BaseKitActivity implements MvpContract.View {


    @BindView(R.id.tv)
    TextView tv;
    @AttachPresenter
    MvpPresenter mMvpPresenter;

    @Override
    public void initData() {

    }

    @Override
    protected void initLisenter() {
        
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
