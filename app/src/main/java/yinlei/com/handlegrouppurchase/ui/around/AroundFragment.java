package yinlei.com.handlegrouppurchase.ui.around;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.widget.swipebackfragment.SwipeBackFragment;

/**
 * 周边fragment
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: AroundFragment.java
 * @author: 若兰明月
 * @date: 2016-08-24 21:04
 */

public class AroundFragment extends SwipeBackFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_around,container,false);
        return attachToSwipeBack(view);
    }
}
