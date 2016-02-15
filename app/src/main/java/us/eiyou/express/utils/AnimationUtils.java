package us.eiyou.express.utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Au on 2016/1/26.
 */
public class AnimationUtils {
    /**
     * 启动页动画
     * 动画执行完后控件 GONE
     *
     * @param imageView
     */
    public static void showLaunchAnimation(final ImageView imageView) {
        /** 设置透明度渐变动画 */
        final AlphaAnimation animation = new AlphaAnimation(0, 1);
        imageView.setAnimation(animation);
        animation.setDuration(2000);//设置动画持续时间
        animation.setRepeatCount(0);
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        /** 开始动画 */
        animation.startNow();

        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {

            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                animation.cancel();
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
    }

    /**
     * 引导页
     */
    public static void guidePage(final Context context, final ViewPager viewPager, final int[] imgs) {
        if (context.getSharedPreferences("isFirst", Context.MODE_PRIVATE).getBoolean("isFirst", true)) {
            context.getSharedPreferences("isFirst", Context.MODE_PRIVATE).edit().putBoolean("isFirst", false).commit();
            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setBackgroundResource(imgs[position]);
                    TextView textView = new TextView(context);
                    linearLayout.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                    linearLayout.setPadding(0, 0, 0, 22);
                    textView.setText(position + 1 + "/" + imgs.length);
                    linearLayout.addView(textView);
                    container.addView(linearLayout);
                    return linearLayout;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeAllViews();
                    viewPager.setVisibility(View.GONE);
                }

                @Override
                public int getCount() {
                    return imgs.length;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }
            });
        } else {
            viewPager.setVisibility(View.GONE);
        }
    }

    /**
     * 自动换页
     */
    public static void autoScrollPager(final Context context, final ViewPager viewPager, final int[] imgs) {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setBackgroundResource(imgs[position]);
                TextView textView = new TextView(context);
                linearLayout.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                linearLayout.setPadding(0, 0, 0, 22);
                textView.setText(position + 1 + "/" + imgs.length);
                linearLayout.addView(textView);
                container.addView(linearLayout);
                return linearLayout;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }

            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }
}
