package yinlei.com.handlegrouppurchase.ui.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.widget.swipebackfragment.SwipeBackFragment;

/**
 * 更多界面
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MoreFragment.java
 * @author: 若兰明月
 * @date: 2016-08-24 21:04
 */

public class MoreFragment extends SwipeBackFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        return attachToSwipeBack(view);
    }
}
