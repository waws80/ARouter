package pw.androidthanatos.arouter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created on 2017/9/27.
 * by liuxiongfei
 */

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    public View scrollUpChild;

    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (scrollUpChild == null){
            return super.canChildScrollUp();
        }else {
            return scrollUpChild.canScrollVertically(-1);
        }

    }
}
