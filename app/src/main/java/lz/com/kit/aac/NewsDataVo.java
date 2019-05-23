package lz.com.kit.aac;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.List;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-23       创建class
 */
public class NewsDataVo {
    public ObservableField<String> id = new ObservableField<>();
    public ObservableField<String> newsTitle = new ObservableField<>();
    public ObservableField<String> newsContent = new ObservableField<>();
    public ObservableField<String> newsUrl = new ObservableField<>();
    public ObservableInt readNum = new ObservableInt();




}
