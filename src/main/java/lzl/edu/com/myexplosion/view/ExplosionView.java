package lzl.edu.com.myexplosion.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import lzl.edu.com.myexplosion.util.Utils;

/**
 * Created by admin on 2015/12/8.
 */
public class ExplosionView extends View{
    private ArrayList<ExplosionAnimator> list_Explosion;
    private static final Canvas mCanvas = new Canvas();
    private OnClickListener onClickListener;

    public ExplosionView(Context context) {
        super(context);
        init();
    }

    public ExplosionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ExplosionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        list_Explosion = new ArrayList<ExplosionAnimator>();
        attachActivity((Activity) getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(ExplosionAnimator animator : list_Explosion){
            animator.draw(canvas);
        }
    }

    /**
     * 将view爆破
     * @param view
     */
    private void explode(final View view){
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);  //获得view在手机屏幕中的位置
        rect.offset(0,-Utils.dp2px(25));//不知道什么意思啊

        final ExplosionAnimator animator = new ExplosionAnimator(this,
                crateGenerateBitmap(view),rect);
        list_Explosion.add(animator);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.animate().alpha(0f).setDuration(150).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().alpha(1f).setDuration(150).start();
                //动画结束时，将该动画移除
                list_Explosion.remove(animation);
                animation = null;
            }
        });
        animator.start();
    }
    private Bitmap crateGenerateBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),
                Bitmap.Config.ARGB_8888);
        if(bitmap!=null){
            synchronized (mCanvas){
                mCanvas.setBitmap(bitmap);
                view.draw(mCanvas);
                //画完之后为mCanvas设置为空
                mCanvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    /**
     * 给activity的所有view都加上破碎粒子的效果
     * @param activity
     */
    private void attachActivity(Activity activity){
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        rootView.addView(this,layoutParams);
    }

    /**
     * 那个view想加入爆破效果，调用该方法
     * @param view
     */
    public void addListener(View view){
        //如果view是ViewGroup对象，进入递归调用，知道是view对象为止
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for(int i=0;i<count;i++){
                addListener(viewGroup.getChildAt(i));
            }
        }else{
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }
    private OnClickListener getOnClickListener(){
        if(null == onClickListener){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExplosionView.this.explode(v);
                }
            };
        }
        return onClickListener;
    }
}
