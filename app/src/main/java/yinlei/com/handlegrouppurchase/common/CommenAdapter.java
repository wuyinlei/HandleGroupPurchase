package yinlei.com.handlegrouppurchase.common;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongkl on 16/8/13.
 * 抽取的listview的适配器
 */
public abstract class CommenAdapter<T> extends BaseAdapter{

    List<T> mData = new ArrayList<>();

    public CommenAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
