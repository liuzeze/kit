package lz.com.kit.aac;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.Observer;

import java.util.List;

import lz.com.kit.BR;
import lz.com.kit.R;
import lz.com.kit.databinding.ActivityMvvmBinding;
import lz.com.kit.databinding.NewsItemBinding;
import lz.com.tools.inject.InjectLayout;

/**
 *
 * @author lz
 * @date 2018/7/23
 */
@InjectLayout(layoutId = R.layout.activity_mvvm, variableId = BR.newsViewModel, titleName = "mvvm框架")
public class MvvmActivity extends BaseMvvmActivity<ActivityMvvmBinding, NewsViewModel> {
    private BaseBindAdapter dataAdapter;
    private ObservableArrayList<NewsDataVo> mData;

    @Override
    protected void initData() {
        NewsDataVo newsDataVo = new NewsDataVo();
        newsDataVo.newsTitle.set("点击加载数据");
        mData = new ObservableArrayList<>();
        dataAdapter = new BaseBindAdapter<NewsDataVo, NewsItemBinding>(R.layout.news_item, BR.newsBean, mData);
        mBinding.dataListRv.setHasFixedSize(true);
        mBinding.dataListRv.setAdapter(dataAdapter);
        viewModel.mData.observe(this, newsDataVos -> {
            assert newsDataVos != null;
            mData.addAll(newsDataVos);
            dataAdapter.notifyDataSetChanged();
            newsDataVo.newsTitle.set("加载完成");
        });

        mBinding.setVariable(BR.datas, newsDataVo);

    }


}