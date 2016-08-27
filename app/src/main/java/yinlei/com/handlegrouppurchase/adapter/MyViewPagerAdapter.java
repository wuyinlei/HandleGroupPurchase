package yinlei.com.handlegrouppurchase.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MyViewPagerAdapter.java
 * @author: 若兰明月
 * @date: 2016-08-27 10:52
 */

public class MyViewPagerAdapter extends  PagerAdapter {

    List<View> list;

    //List<String> titles;
    public MyViewPagerAdapter(List<View> list) {
        // TODO Auto-generated constructor stub

        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    //  判断  当前的view 是否是  Object 对象
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(list.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "1";  //暂时没用的
    }
}
