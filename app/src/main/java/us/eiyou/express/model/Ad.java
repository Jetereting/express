package us.eiyou.express.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Au on 2016/2/15.
 */
public class Ad extends BmobObject {
    String ad;

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}
