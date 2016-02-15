package us.eiyou.express.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Au on 2016/2/15.
 */
public class Express_info extends BmobObject {
//    快递单号
    String number;
//    用户手机号
    String user;
//    地址
    String loc;
//    快递公司
    String express;
//    确认码
    String code;
//    收件人手机号
    String phone;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
