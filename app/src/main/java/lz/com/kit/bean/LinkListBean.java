package lz.com.kit.bean;

import androidx.annotation.Nullable;

import lz.com.tools.linkedlist.LinkBean;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class LinkListBean extends LinkBean {

    private String titleText = "";




    public String getTitleText() {
        return titleText;
    }

    public LinkListBean setTitleText(String titleText) {
        this.titleText = titleText;
        return this;
    }


}
