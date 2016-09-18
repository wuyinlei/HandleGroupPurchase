package yinlei.com.handlegrouppurchase.ui.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.reflect.TypeToken;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.adapter.GoodsAdapter;
import yinlei.com.handlegrouppurchase.adapter.MyGridAdapter;
import yinlei.com.handlegrouppurchase.adapter.MyPagerAdapter;
import yinlei.com.handlegrouppurchase.bean.HomeIconInfo;
import yinlei.com.handlegrouppurchase.common.Global;
import yinlei.com.handlegrouppurchase.constant.MyConstant;
import yinlei.com.handlegrouppurchase.http.HttpListener;
import yinlei.com.handlegrouppurchase.listener.MyPagerListner;
import yinlei.com.handlegrouppurchase.ui.DetailActivity;
import yinlei.com.handlegrouppurchase.ui.category.CategoryActivity;
import yinlei.com.handlegrouppurchase.ui.location.LocationActivity;
import yinlei.com.handlegrouppurchase.ui.search.SearchActivity;
import yinlei.com.handlegrouppurchase.ui.tip.TipActivity;
import yinlei.com.handlegrouppurchase.utils.Utils;
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

    SliderLayout mSliderLayout;

    TextView search_src_text;


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

//    private GridView gridView1;
//    private GridView gridView2;
//    private GridView gridView3;

    TextView home_top_city;

    ImageView index_home_tip;

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

    MyListView mMyListView;


    private View mInflate;
    private MyPagerAdapter mMyPagerAdapter = new MyPagerAdapter(mViews);
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //防止重新加载数据   //这一步也是解决了在切换fragment的时候，因为里面有了pagerAdapter
        //如果没有重新加载数据，那么就会在次添加mViews，这个时候由于count和当前的mViews个数不一样(mViews的数量又多一倍)
        //这个时候就会报异常，所以用这个不重新加载，就可以防止重新加载PagerAdapter
        if (mInflate == null) {

            mInflate = inflater.inflate(R.layout.fragment_main, container, false);

            mMyPagerAdapter.notifyDataSetChanged();
            initData();
            new MyThread().start();
            init();
            initView();
            initSlideLayout();
            initListener();
        }

        return mInflate;
    }

    private void init() {
        mSliderLayout = (SliderLayout) mInflate.findViewById(R.id.slider);
        home_top_city = (TextView) mInflate.findViewById(R.id.home_top_city);
        index_home_tip = (ImageView) mInflate.findViewById(R.id.index_home_tip);
        search_src_text = (TextView) mInflate.findViewById(R.id.search_src_text);
        image_scan = (ImageView) mInflate.findViewById(R.id.image_scan);
        mMyListView = (MyListView) mInflate.findViewById(R.id.listView);

    }

    private void initListener() {
        home_top_city.setOnClickListener(this);
        index_home_tip.setOnClickListener(this);
        search_src_text.setOnClickListener(this);
        image_scan.setOnClickListener(this);
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
                HomeIconInfo homeIconInfo = mPagerOneData.get(i);
                String cateName = homeIconInfo.getIconName();
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(Global.CATEGORY_NAME, cateName);
                getActivity().startActivity(intent);
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
                HomeIconInfo homeIconInfo = mPagerTwoData.get(i);
                String cateName = homeIconInfo.getIconName();
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(Global.CATEGORY_NAME, cateName);
                startActivity(intent);
                Toast.makeText(getActivity(), "我是第gridView02" + i + 1 + "页的第" + i + 1 + "项", Toast.LENGTH_SHORT).show();
            }
        });

        View pagerThree = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView gridView03 = (GridView) pagerThree.findViewById(R.id.gridView);
        gridView03.setAdapter(new MyGridAdapter(mPagerThreeData, getActivity()));
        gridView03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeIconInfo homeIconInfo = mPagerThreeData.get(i);
                String cateName = homeIconInfo.getIconName();
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(Global.CATEGORY_NAME, cateName);
                startActivity(intent);
                Toast.makeText(getActivity(), "我是gridView03" + "页的第" + i + 1 + "项", Toast.LENGTH_SHORT).show();
            }
        });


        //添加到ViewPager中
        mViews.add(pagerOne);
        mViews.add(pagerTwo);
        mViews.add(pagerThree);

        viewPager.setAdapter(new MyPagerAdapter(mViews));

        //  mMyPagerAdapter.notifyDataSetChanged();

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
            case R.id.home_top_city:  //定位
                getActivity().startActivity(new Intent(getActivity(), LocationActivity.class));
                break;
            case R.id.search_src_text:  //搜索
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.index_home_tip:  //信箱
                getActivity().startActivity(new Intent(getActivity(), TipActivity.class));
                break;
            case R.id.image_scan:  //扫描
                getActivity().startActivityForResult(new Intent(getActivity(), CaptureActivity.class), REQUEST_CODE);
                break;
            case R.id.ll_1: //折扣
                Toast.makeText(getActivity(), "点击我了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_2:  //闪慧

                break;
            case R.id.ll_3:  //半价

                break;
            case R.id.ll_4:  //自助

                break;
            default:

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
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


        // String result = Utils.getJson(getActivity(), "GoodsInfo.json");

        //Request<String> request = NoHttp.createStringRequest(Constant.spRecommendURL, RequestMethod.GET);
        //CallServer.getInstance().add(getActivity()
        //      , 0, request, this, true, true);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mGoodlistBeen = (List<GoodsBean.ResultBean.GoodlistBean>) msg.obj;
            mGoodsAdapter = new GoodsAdapter(getActivity(), mGoodlistBeen);
            // mGoodsAdapter.notify();
            mMyListView.setAdapter(mGoodsAdapter);
            mMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i >= 2) {
                        String goods_id = mGoodlistBeen.get(i - 2).getGoods_id();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("goods_id", goods_id);
                        Toast.makeText(getContext(), "i:" + i, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            });
        }
    };


    /**
     * 解析本地json文件
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            String result = Utils.getJson(getActivity(), "GoodsInfo.json");
            Gson gson = new Gson();
            GoodsBean goodsBean = null;
            goodsBean = gson.fromJson(result, new TypeToken<GoodsBean>() {
            }.getType());
            mGoodlistBeen = goodsBean.getResult().getGoodlist();
            Message message = Message.obtain();
            message.obj = mGoodlistBeen;
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
//        switch (what) {
//            case 0:
//                Gson gson = new Gson();
//                String data = response.get();
//                GoodsBean goodsBean = null;
//                goodsBean = gson.fromJson(data, new TypeToken<GoodsBean>() {
//                }.getType());
//                mGoodlistBeen = goodsBean.getResult().getGoodlist();
//                mGoodsAdapter = new GoodsAdapter(getActivity(), mGoodlistBeen);
//                mMyListView.setAdapter(mGoodsAdapter);
//        }
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

    @Override
    public void onStop() {
        mSliderLayout.stopAutoCycle();
        // mMyPagerAdapter.notifyDataSetChanged();
        // mViews.clear();
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
