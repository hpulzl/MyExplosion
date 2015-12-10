package lzl.edu.com.myexplosion.view;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by admin on 2015/12/9.
 * 该类主要是为小球设置动画效果
 */
public class ExplosionAnimator extends ValueAnimator {
    private static final long DEFAULT_DURATION = 1500;  //设置动画的时间
    private Grain[][] mGrains;   //粒子的矩阵图
    private View mExplosionView;  //需要转换的view
    private Paint mPaint;   //画笔

    public ExplosionAnimator(View view, Bitmap bitmap, Rect bound){
        mExplosionView = view;
        mPaint = new Paint();

        setFloatValues(0.0f,1.0f);
        setDuration(DEFAULT_DURATION);

        mGrains = generateGrain(bitmap,bound);
    }
    public Grain[][] generateGrain(Bitmap bitmap,Rect bound){
        //获取view的宽高
        int w = bound.width();
        int h = bound.height();

        int partW_Count = w / Grain.GRAIN_WH;  //横向粒子个数。
        int partH_Count = h / Grain.GRAIN_WH; //竖向粒子个数

        int bitmap_part_W = bitmap.getWidth() / partW_Count;
        int bitmap_part_H = bitmap.getHeight() / partW_Count;

        //初始化粒子数组
       Grain[][] grains = new Grain[partH_Count][partW_Count];
        Point mPoint;
        for(int row=0;row<partH_Count;row++){
            for(int column=0; column<partW_Count;column++){
                mPoint = new Point(column,row);
               int color = bitmap.getPixel(column * bitmap_part_W,row * bitmap_part_H);
               grains[row][column] =  Grain.generateGrain(color,bound,mPoint);
            }
        }
        return grains;
    }
    public void draw(Canvas canvas){
        if(!isStarted()){  //游戏结束
            return;
        }
        for(Grain[] grains : mGrains){
            for(Grain grain : grains){
                grain.advance((Float)getAnimatedValue());
                mPaint.setColor(grain.color);
                mPaint.setAlpha((int) (Color.alpha(grain.color) * grain.alpha));
                canvas.drawCircle(grain.gx,grain.gy,grain.radius,mPaint);
            }
        }
        mExplosionView.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mExplosionView.invalidate();
    }
}
