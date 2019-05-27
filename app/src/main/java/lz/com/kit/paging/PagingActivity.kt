package lz.com.kit.paging

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.activity_paging.*
import lz.com.kit.BaseKitActivity
import lz.com.kit.R
import lz.com.tools.inject.InjectLayout

@InjectLayout(layoutId = R.layout.activity_paging, titleName = "paging组件使用")
class PagingActivity : BaseKitActivity() {
    override fun initData() {
        val pagingAdapter = PagingAdapter()
        page_recyclevie.adapter = pagingAdapter


        val build = PagedList.Config.Builder()
                .setPageSize(10)
                .setPrefetchDistance(2)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(false)
                .build()

        val liveData = LivePagedListBuilder(PageDataSourceFactory(), build).build()
        liveData.observe(this, Observer<PagedList<String>> { pagingAdapter.submitList(it) })
    }


}
