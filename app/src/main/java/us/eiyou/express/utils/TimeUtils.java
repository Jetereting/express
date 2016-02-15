package us.eiyou.express.utils;

import android.os.CountDownTimer;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Au on 2016/1/27.
 */
public class TimeUtils {
    public static void descCount(final Button button,int descFrom) {
        CountDownTimer countDownTimer = new CountDownTimer(descFrom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setClickable(false);
                button.setText(millisUntilFinished / 1000 + "秒");
            }
            @Override
            public void onFinish() {
                button.setText("获取动态验证码");
                button.setClickable(true);
            }
        }.start();
    }
    public static String getCurrentTime(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return format.format(date);
    }
}
