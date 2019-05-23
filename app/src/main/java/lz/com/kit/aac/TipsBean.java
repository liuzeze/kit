package lz.com.kit.aac;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-23       创建class
 */
class TipsBean {
    /**
     * type : app
     * display_duration : 2
     * display_info : 今日头条推荐引擎有16条更新
     * display_template : 今日头条推荐引擎有%s条更新
     * open_url :
     * web_url :
     * download_url :
     * app_name : 今日头条
     * package_name :
     */

    private String type;
    private int display_duration;
    private String display_info;
    private String display_template;
    private String open_url;
    private String web_url;
    private String download_url;
    private String app_name;
    private String package_name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDisplay_duration() {
        return display_duration;
    }

    public void setDisplay_duration(int display_duration) {
        this.display_duration = display_duration;
    }

    public String getDisplay_info() {
        return display_info;
    }

    public void setDisplay_info(String display_info) {
        this.display_info = display_info;
    }

    public String getDisplay_template() {
        return display_template;
    }

    public void setDisplay_template(String display_template) {
        this.display_template = display_template;
    }

    public String getOpen_url() {
        return open_url;
    }

    public void setOpen_url(String open_url) {
        this.open_url = open_url;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
