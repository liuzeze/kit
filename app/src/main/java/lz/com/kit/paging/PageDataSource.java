package lz.com.kit.paging;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-24       创建class
 */
public class PageDataSource extends ItemKeyedDataSource<String, String> {
    int  size=10;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String> callback) {
        ArrayList<String> es = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            es.add(""+i);
        }
        callback.onResult(es, 0, 15);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String> callback) {
        ArrayList<String> es = new ArrayList<>();
        for (int i = size; i < size+10; i++) {
            es.add(""+i);
        }
        size+=10;
        callback.onResult(es);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String> callback) {

    }


    @NonNull
    @Override
    public String getKey(@NonNull String item) {
        return item;
    }


}
