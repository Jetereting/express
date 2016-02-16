package us.eiyou.express.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Au on 2016/2/16.
 */
public class Probability extends BmobObject {
    Double probability;

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
