package yinlei.com.handlegrouppurchase.ui.main;

import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.ui.around.AroundFragment;
import yinlei.com.handlegrouppurchase.ui.base.BaseActivity;
import yinlei.com.handlegrouppurchase.ui.mainfragment.MainFragment;
import yinlei.com.handlegrouppurchase.ui.mine.MineFragment;
import yinlei.com.handlegrouppurchase.ui.more.MoreFragment;

public class MainActivity extends BaseActivity {

    //fragment
    private Class[] fragments = new Class[]{
            MainFragment.class,AroundFragment.class,MineFragment.class
            ,MoreFragment.class
    };

    //图片资源
    private int [] imgResources = new int[]{
            R.drawable.ic_home_tab_index_selector,
            R.drawable.ic_home_tab_near_selector,
            R.drawable.ic_home_tab_my_selector,
            R.drawable.ic_home_tab_more_selector
    };

    //标题
    private String[] tabIitles = new String[]{"首页","周边","我的","更多"
    };

    FragmentTabHost mTabHost;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void loadData() {
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        for (int i = 0; i < fragments.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.tab_item,null);
            TextView tab_tv = (TextView) view.findViewById(R.id.tv);
            ImageView tab_iv = (ImageView) view.findViewById(R.id.iv);
            tab_iv.setImageResource(imgResources[i]);
            tab_tv.setText(tabIitles[i]);
            mTabHost.addTab(mTabHost.newTabSpec(""+i).setIndicator(view), fragments[i], null);

        }
    }

    @Override
    protected void setListener() {

    }
}
