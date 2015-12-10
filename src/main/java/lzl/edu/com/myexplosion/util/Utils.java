package lzl.edu.com.myexplosion.util;

import android.content.res.Resources;

/**
 * Created by admin on 2015/12/9.
 */
public class Utils {
        /**
         * 密度
         */
        public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

        public static int dp2px(int dp) {
            return Math.round(dp * DENSITY);
        }
}
