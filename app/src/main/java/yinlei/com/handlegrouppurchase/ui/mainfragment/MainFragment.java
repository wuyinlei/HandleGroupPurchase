package yinlei.com.handlegrouppurchase.ui.mainfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yinlei.com.handlegrouppurchase.bean.HomeIconInfo;
import yinlei.com.handlegrouppurchase.adapter.GoodsAdapter;
import yinlei.com.handlegrouppurchase.adapter.MyGridAdapter;
import yinlei.com.handlegrouppurchase.adapter.MyPagerAdapter;
import yinlei.com.handlegrouppurchase.listener.MyPagerListner;
import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.adapter.ViewHolder;
import yinlei.com.handlegrouppurchase.common.CommenAdapter;
import yinlei.com.handlegrouppurchase.constant.Constant;
import yinlei.com.handlegrouppurchase.constant.MyConstant;
import yinlei.com.handlegrouppurchase.http.CallServer;
import yinlei.com.handlegrouppurchase.http.HttpListener;
import yinlei.com.handlegrouppurchase.widget.MyListView;
import yinlei.com.handlegrouppurchase.widget.ViewPagerIndicator;

/**
 * 主界面
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MainFragment.java
 * @author: 若兰明月
 * @date: 2016-08-24 21:03
 */

public class MainFragment extends Fragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener, HttpListener<String>, View.OnClickListener {

    @Bind(R.id.slider)
    SliderLayout mSliderLayout;

    @Bind(R.id.search_src_text)
    TextView search_src_text;

//    @Bind(R.id.index_home_viewpager)
//    WrapContentHeightViewPager viewPager;

    private List<String> mData = new ArrayList<>();

    private List<View> mViews = new ArrayList<>();

    /**
     * gridView两页的数据
     */
    private List<HomeIconInfo> mPagerOneData = new ArrayList<>();
    private List<HomeIconInfo> mPagerTwoData = new ArrayList<>();
    private List<HomeIconInfo> mPagerThreeData = new ArrayList<>();
    private ViewPagerIndicator mIndicator;

    private List<GoodsBean.ResultBean.GoodlistBean> mGoodlistBeen;
    private GoodsAdapter mGoodsAdapter;

    private GridView gridView1;
    private GridView gridView2;
    private GridView gridView3;

    @Bind(R.id.home_top_city)
    TextView home_top_city;

    @Bind(R.id.index_home_tip)
    ImageView index_home_tip;

    @Bind(R.id.image_scan)
    ImageView image_scan;

    TextView tv_title_1;

    TextView tv_title_11;

    TextView tv_title_2;

    TextView tv_title_22;

    TextView tv_title_3;

    TextView tv_title_33;

    TextView tv_title_4;

    TextView tv_title_44;

    LinearLayout ll_1;

    LinearLayout ll_2;

    LinearLayout ll_3;

    LinearLayout ll_4;

    @Bind(R.id.listView)
    MyListView mMyListView;

    private MyAdapter mMyAdapter;
//
//    //接受处理消息
//    private Handler handler = new Handler() {//暂时先让秒数动起来
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                initViewPager();//初始化 viewpager 解决切换不显示的问题
//            }
//        }
//    };

    private View mInflate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //防止重新加载数据
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, mInflate);
            initData();
            initView();
            initSlideLayout();
        }
        return mInflate;
    }


    /***
     * 初始化gridview
     */
    private void initView() {
        View headViewOne = LayoutInflater.from(getActivity()).inflate(R.layout.home_header_one, null);
        initHeaderViewOne(headViewOne);
        View headViewTwo = LayoutInflater.from(getActivity()).inflate(R.layout.home_headviewall, null);

        mIndicator = (ViewPagerIndicator) headViewTwo.findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) headViewTwo.findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(new MyPagerListner(mIndicator));

        //第一页数据
        View pagerOne = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView gridView01 = (GridView) pagerOne.findViewById(R.id.gridView);
        gridView01.setAdapter(new MyGridAdapter(mPagerOneData, getActivity()));
        gridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "我是第gridView01" + i + 1 + "页的第" + i + 1 + "项", Toast.LENGTH_SHORT).show();
            }
        });
        //第二页数据
        View pagerTwo = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView gridView02 = (GridView) pagerTwo.findViewById(R.id.gridView);
        gridView02.setAdapter(new MyGridAdapter(mPagerTwoData, getActivity()));
        gridView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "我是第gridView02" + i + 1 + "页的第" + i + 1 + "项", Toast.LENGTH_SHORT).show();
            }
        });

        View pagerThree = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView gridView03 = (GridView) pagerThree.findViewById(R.id.gridView);
        gridView03.setAdapter(new MyGridAdapter(mPagerThreeData, getActivity()));
        gridView03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "我是gridView03" + "页的第" + i + 1 + "项", Toast.LENGTH_SHORT).show();
            }
        });


        //添加到ViewPager中
        mViews.add(pagerOne);
        mViews.add(pagerTwo);
        mViews.add(pagerThree);
        viewPager.setAdapter(new MyPagerAdapter(mViews));
        //添加listview的头部
        mMyListView.addHeaderView(headViewTwo);
        mMyListView.addHeaderView(headViewOne);
        //  mMyAdapter = new MyAdapter(mDatalist);

       // mMyListView.setAdapter(mMyAdapter);
    }

    private void initHeaderViewOne(View headViewOne) {
        tv_title_1 = (TextView) headViewOne.findViewById(R.id.tv_title_1);
        tv_title_11 = (TextView) headViewOne.findViewById(R.id.tv_title_11);
        tv_title_2 = (TextView) headViewOne.findViewById(R.id.tv_title_2);
        tv_title_22 = (TextView) headViewOne.findViewById(R.id.tv_title_22);
        tv_title_3 = (TextView) headViewOne.findViewById(R.id.tv_title_3);
        tv_title_33 = (TextView) headViewOne.findViewById(R.id.tv_title_33);
        tv_title_4 = (TextView) headViewOne.findViewById(R.id.tv_title_4);
        tv_title_44 = (TextView) headViewOne.findViewById(R.id.tv_title_44);
        ll_1 = (LinearLayout) headViewOne.findViewById(R.id.ll_1);
        ll_1.setOnClickListener(this);
        ll_2 = (LinearLayout) headViewOne.findViewById(R.id.ll_2);
        ll_3 = (LinearLayout) headViewOne.findViewById(R.id.ll_3);
        ll_4 = (LinearLayout) headViewOne.findViewById(R.id.ll_4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_top_city:

                break;
            case R.id.search_src_text:

                break;
            case R.id.index_home_tip:

                break;
            case R.id.image_scan:

                break;
            case R.id.ll_1:
                Toast.makeText(getActivity(), "点击我了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_2:

                break;
            case R.id.ll_3:

                break;
            case R.id.ll_4:

                break;
            default:

                break;

        }
    }

    class MyAdapter extends CommenAdapter {

        public MyAdapter(List data) {
            super(data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
//            ViewHolder holder = null;
            //第二步,判断条件的封装
//            if (convertView == null){
//                holder = new ViewHolder(getActivity(),R.layout.goods_list_item,viewGroup);
//                //第一步:
////                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.goods_list_item, null);
////                convertView.setTag(holder);
//            }else {
//                holder = (ViewHolder) convertView.getTag();
//            }

            ViewHolder holder = ViewHolder.get(getActivity(), R.layout.goods_list_item, convertView, viewGroup);

            //第三步
            //商品子视图
//            holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
//            holder.tvTitle.setText(mDatalist.get(position).getProduct());
            TextView tv = holder.getView(R.id.title);
            //  tv.setText(mDatalist.get(position).getProduct());
            //Uri uri = Uri.parse(mDatalist.get(position).getImages().get(0).getImage());
            //SimpleDraweeView draweeView = holder.getView(R.id.iv_icon2);
            //draweeView.setImageURI(uri);
            return holder.getConvertView();
        }
    }

//    static class ViewHolder{
//        TextView tvTitle;
//    }


    /**
     * 猜你喜欢的数据解析
     */
    private void initData() {

        //获取资源文件的数据
        String[] iconName = MyConstant.navSort;
        int[] images = MyConstant.navSortImages;

        for (int i = 0; i < iconName.length; i++) {
            if (i < 8) {
                mPagerOneData.add(new HomeIconInfo(iconName[i], images[i]));
            } else if (i < 16) {
                mPagerTwoData.add(new HomeIconInfo(iconName[i], images[i]));
            } else {
                mPagerThreeData.add(new HomeIconInfo(iconName[i], images[i]));
            }
        }

        Request<String> request = NoHttp.createStringRequest(Constant.spRecommendURL, RequestMethod.GET);
        CallServer.getInstance().add(getActivity()
                , 0, request, this, true, true);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case 0:
                Gson gson = new Gson();
                GoodsBean goodsBean = gson.fromJson(response.get(), GoodsBean.class);
                mGoodlistBeen = goodsBean.getResult().getGoodlist();
                mGoodsAdapter = new GoodsAdapter(getActivity(), mGoodlistBeen);
                //mGoodsAdapter.setGoodsBeen(mGoodlistBeen);
                mMyListView.setAdapter(mGoodsAdapter);
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

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

//
//    /**
//     * 初始化gridview
//     */
//    private void initGridView() {
//        gridView1 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
//        gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
//        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "点击我了" + i, Toast.LENGTH_SHORT).show();
//            }
//        });
//        gridView2 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
//        gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
//        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "点击我了" + i, Toast.LENGTH_SHORT).show();
//            }
//        });
//        gridView3 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
//        gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
//        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "点击我了" + i, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//
//    private void initViewPager() {   //初始化viewpager
//        List<View> list = new ArrayList<View>();  //以下实现动态添加三组gridview
//        list.add(gridView1);
//        list.add(gridView2);
//        list.add(gridView3);
//        viewPager.setAdapter(new MyViewPagerAdapter(list));
//        //viewPager .setOffscreenPageLimit(2);   //meiyong
//        rb1.setChecked(true);//设置默认  下面的点选中的是第一个
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {  //实现划到那个页面，那个页面下面的点会被选中
//                // TODO Auto-generated method stub
//                if (position == 0) {
//                    rb1.setChecked(true);
//                } else if (position == 1) {
//                    rb2.setChecked(true);
//                } else if (position == 2) {
//                    rb3.setChecked(true);
//                }
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
