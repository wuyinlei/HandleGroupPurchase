package yinlei.com.handlegrouppurchase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 */
public class ObservableScrollView extends ScrollView {
    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 滑动接口回调
     */
    public interface ScrollViewLinstener {
        void onScrollViewScrollChanged(int x, int y, int oldl, int oldy);
    }

    private ScrollViewLinstener mLinstener;

    /**
     * 对外提供设置监听的方法
     */
    public void setLinstener(ScrollViewLinstener linstener) {
        mLinstener = linstener;
    }

    /**
     * 重写滑动变化的函数
     *
     * @param l    Current horizontal scroll origin.
     * @param t    Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mLinstener != null) {
            mLinstener.onScrollViewScrollChanged(l, t, oldl, oldt);
        }
    }
}
