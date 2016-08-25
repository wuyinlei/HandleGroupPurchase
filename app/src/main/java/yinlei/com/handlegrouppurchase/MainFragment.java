package yinlei.com.handlegrouppurchase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.handlegrouppurchase.MainFragment.java
 * @author: myName
 * @date: 2016-08-24 21:03
 */

public class MainFragment extends Fragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    @Bind(R.id.slider)
    SliderLayout mSliderLayout;

    @Bind(R.id.search_src_text)
    TextView search_src_text;

    @Bind(R.id.index_home_viewpager)
    WrapContentHeightViewPager viewPager;

    private GridView gridView1;
    private GridView gridView2;
    private GridView gridView3;

    @Bind(R.id.index_home_rb1)
    RadioButton rb1;
    @Bind(R.id.index_home_rb2)
    RadioButton rb2;
    @Bind(R.id.index_home_rb3)
    RadioButton rb3;

    @Bind(R.id.home_top_city)
    TextView home_top_city;

    @Bind(R.id.index_home_tip)
    ImageView index_home_tip;

    @Bind(R.id.image_scan)
    ImageView image_scan;

    @Bind(R.id.tv_title_1)
    TextView tv_title_1;

    @Bind(R.id.tv_title_11)
    TextView tv_title_11;

    @Bind(R.id.tv_title_2)
    TextView tv_title_2;

    @Bind(R.id.tv_title_22)
    TextView tv_title_22;
    @Bind(R.id.tv_title_3)
    TextView tv_title_3;

    @Bind(R.id.tv_title_33)
    TextView tv_title_33;

    @Bind(R.id.tv_title_4)
    TextView tv_title_4;

    @Bind(R.id.tv_title_44)
    TextView tv_title_44;

    @Bind(R.id.ll_1)
    LinearLayout ll_1;

    @Bind(R.id.ll_2)
    LinearLayout ll_2;

    @Bind(R.id.ll_3)
    LinearLayout ll_33;

    @Bind(R.id.ll_4)
    LinearLayout ll_44;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    //接受处理消息
    private Handler handler = new Handler() {//暂时先让秒数动起来

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                initViewPager();//初始化 viewpager 解决切换不显示的问题
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        initSlideLayout();
        initGridView();
        //虽然有了下面的发线程来初始化viewpager  不过这一步还是需要的，(具体原因不太清楚，不过可以解决)
        initViewPager();
        handler.sendEmptyMessage(1);//发线程 初始化viewpager 解决切换页面导致viewpager中的内容为空
        return view;
    }


    /**
     * 初始化广告轮播条
     */
    private void initSlideLayout() {
        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
//
        HashMap<String, Integer> file_maps = new HashMap<>();
        file_maps.put("Hannibal", R.drawable.hannibal);
        file_maps.put("Big Bang Theory", R.drawable.bigbang);
        file_maps.put("House of Cards", R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(4000);
        mSliderLayout.addOnPageChangeListener(this);
    }


    /**
     * 初始化gridview
     */
    private void initGridView() {
        gridView1 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
        gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
        gridView2 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
        gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
        gridView3 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
        gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
    }


    //gridview 的适配器
    public class GridViewAdapter extends BaseAdapter {

        //我的数据在utils包下的MyConstant中定义好了
        private LayoutInflater inflater;
        private int page;

        public GridViewAdapter(Context context, int page) {
            super();
            this.inflater = LayoutInflater.from(context);
            this.page = page;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (page != -1) {
                return 8;
            } else {
                return MyConstant.navSort.length;
            }
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {

            ViewHolder vh = null;

            if (convertView == null) {
                vh = new ViewHolder();
                convertView = inflater.inflate(R.layout.index_home_grid_item, null);
                vh.iv_navsort = (ImageView) convertView.findViewById(R.id.index_home_iv_navsort);
                vh.tv_navsort = (TextView) convertView.findViewById(R.id.index_home_tv_navsort);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            vh.iv_navsort.setImageResource(MyConstant.navSortImages[position + 8 * page]);
            vh.tv_navsort.setText(MyConstant.navSort[position + 8 * page]);
            if (position == 8 - 1 && page == 2) {
                vh.iv_navsort.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                    }
                });
            } else {
                vh.iv_navsort.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                    }
                });
            }


            return convertView;
        }
    }

    //gridview 适配器的holder类
    private class ViewHolder {
        ImageView iv_navsort;
        TextView tv_navsort;
    }


    private void initViewPager() {   //初始化viewpager
        List<View> list = new ArrayList<View>();  //以下实现动态添加三组gridview
        list.add(gridView1);
        list.add(gridView2);
        list.add(gridView3);
        viewPager.setAdapter(new MyViewPagerAdapter(list));
        //viewPager .setOffscreenPageLimit(2);   //meiyong
        rb1.setChecked(true);//设置默认  下面的点选中的是第一个
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {  //实现划到那个页面，那个页面下面的点会被选中
                // TODO Auto-generated method stub
                if (position == 0) {
                    rb1.setChecked(true);
                } else if (position == 1) {
                    rb2.setChecked(true);
                } else if (position == 2) {
                    rb3.setChecked(true);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    //自定义viewpager的适配器
    private class MyViewPagerAdapter extends PagerAdapter {

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


    @Override
    public void onStop() {
        mSliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
