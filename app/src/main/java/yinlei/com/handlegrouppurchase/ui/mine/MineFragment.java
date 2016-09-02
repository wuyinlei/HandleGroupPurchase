package yinlei.com.handlegrouppurchase.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.adapter.GoodsAdapter;
import yinlei.com.handlegrouppurchase.ui.mainfragment.GoodsBean;
import yinlei.com.handlegrouppurchase.widget.swipebackfragment.SwipeBackFragment;

/**
 * 我的界面
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MineFragment.java
 * @author: 若兰明月
 * @date: 2016-08-24 21:04
 */

public class MineFragment extends SwipeBackFragment {


    private List<GoodsBean.ResultBean.GoodlistBean> mGoodlistBeen;
    private GoodsAdapter mGoodsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        return attachToSwipeBack(view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
