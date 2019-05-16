package lz.com.kit.mvp;

public class NewGoodsRequestBean {

    /**
     * 0价格升序,1价格降序,2销量降序,3好评率降序,4好评率升序价格降序-非必填
     */
    public String identification=null;
    /**
     * 0标准可采，1尊享尚品(非必填不传则是全部)
     */
    public String label=null;
    public int numPerPage;
    public int pageNum;

    public String getIdentification() {
        return identification;
    }

    public NewGoodsRequestBean setIdentification(String identification) {
        this.identification = identification;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public NewGoodsRequestBean setLabel(String label) {
        this.label = label;
        return this;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public NewGoodsRequestBean setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public NewGoodsRequestBean setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }
}
