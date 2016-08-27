package yinlei.com.handlegrouppurchase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MyListView.java
 * @author: 若兰明月
 * @date: 2016-08-27 12:11
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 重写了onMeasure方法  作用是为了自设定测量规则  是的ListView能在ScrollView里面显示完全
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        //一定要把这个测量规则传入下面的方法里面  要不没有作用
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
