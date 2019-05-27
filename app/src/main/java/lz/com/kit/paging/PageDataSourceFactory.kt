package lz.com.kit.paging

import androidx.paging.DataSource

/**
 *-----------作者----------日期----------变更内容-----
 *-          刘泽      2019-05-24       创建class
 */
class PageDataSourceFactory : DataSource.Factory<String, String>() {
    override fun create(): DataSource<String, String> {
       return PageDataSource()
    }

}