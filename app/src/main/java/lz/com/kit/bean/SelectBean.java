package lz.com.kit.bean;

import lz.com.tools.recycleview.bean.SelectItem;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class SelectBean extends SelectItem {
    public String name = "";


    @Override
    protected String getSelectIndex() {
        return name;
    }
}
