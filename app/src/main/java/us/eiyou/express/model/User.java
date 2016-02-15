package us.eiyou.express.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Au on 2016/2/15.
 */
public class User extends BmobUser {
    String pass_word;

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }
}
