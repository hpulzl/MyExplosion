package lzl.edu.com.myexplosion.view;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by admin on 2015/12/8.
 * 该类设置每个粒子的属性，并通过静态的方法实例化Grain对象。
 *
 */
public class Grain {
    public static final int GRAIN_WH = 8;  //粒子默认的宽度
     int color ;  //粒子的颜色
     float alpha;   //粒子的透明度
    //粒子变化中的坐标,以及粒子的半径
     float gx;
     float gy;
     float radius;
   static Random random = new Random();
    Rect mRect;

    /**
     * 用于实例化小球对象
     * @param color
     * @param bound
     * @param mPoint
     * @return
     */
    public static Grain generateGrain(int color, Rect bound, Point mPoint){
        int row = mPoint.y;  //行表示高
        int column = mPoint.x;  //列表示宽

        Grain grain = new Grain();
        grain.mRect = bound;
        grain.color = color;
        grain.alpha = 1f;

        grain.radius = GRAIN_WH;
        grain.gx = bound.left + GRAIN_WH * column;  //x坐标
        grain.gy = bound.top + GRAIN_WH * row;     //y坐标

        return grain;
    }

    /**
     * 设置粒子随机移动的位置。
     * @param factor
     */
    public void advance(float factor){
        gx = gx + factor * random.nextInt(mRect.width()) * (random.nextFloat() - 0.5f);
        gy = gy + factor * random.nextInt(mRect.height() / 2);

        radius = radius - factor * random.nextInt(2);

        alpha = (1f - factor) * (1 + random.nextFloat());
    }
}
