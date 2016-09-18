package yinlei.com.handlegrouppurchase.ui.around;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.adapter.ViewHolder;
import yinlei.com.handlegrouppurchase.bean.GoodsInfo;
import yinlei.com.handlegrouppurchase.common.CommenAdapter;
import yinlei.com.handlegrouppurchase.constant.Constant;
import yinlei.com.handlegrouppurchase.http.CallServer;
import yinlei.com.handlegrouppurchase.http.HttpListener;
import yinlei.com.handlegrouppurchase.ui.DetailActivity;
import yinlei.com.handlegrouppurchase.ui.location.LocationActivity;
import yinlei.com.handlegrouppurchase.widget.swipebackfragment.SwipeBackFragment;

/**
 * 周边fragment
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: AroundFragment.java
 * @author: 若兰明月
 * @date: 2016-08-24 21:04
 */

public class AroundFragment extends SwipeBackFragment implements HttpListener<String> {




    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.supplier_list_title_tv)
    TextView mSupplierListTitleTv;
    @Bind(R.id.supplier_list_cart_iv)
    ImageView mSupplierListCartIv;
    @Bind(R.id.titlebar_layout)
    FrameLayout mTitlebarLayout;
    @Bind(R.id.supplier_list_product_tv)
    TextView mProductTv;
    @Bind(R.id.supplier_list_product)
    LinearLayout mProduct;
    @Bind(R.id.supplier_list_sort_tv)
    TextView mSortTv;
    @Bind(R.id.supplier_list_sort)
    LinearLayout mSort;
    @Bind(R.id.supplier_list_activity_tv)
    TextView mActivityTv;
    @Bind(R.id.supplier_list_activity)
    LinearLayout mActivity;
    @Bind(R.id.supplier_list_lv)
    ListView mSupplierListLv;
    private ArrayList<Map<String, String>> mMenuData1;
    private ArrayList<Map<String, String>> mMenuData2;
    private ArrayList<Map<String, String>> mMenuData3;
    private ListView mPopListview;
    private PopupWindow popupMenu;
    private SimpleAdapter mMenuAdapter1;
    private SimpleAdapter mMenuAdapter2;
    private SimpleAdapter mMenuAdapter3;
    private int menuIndex = 0;

    private List<GoodsInfo.ResultBean.GoodlistBean> mDatalist = new ArrayList<>();
    private CommenAdapter<GoodsInfo.ResultBean.GoodlistBean> mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_around,container,false);

        ButterKnife.bind(this, view);

        //toolbar设置菜单按钮
        mToolbar.inflateMenu(R.menu.around_title_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        startActivity(new Intent(getActivity(), LocationActivity.class));
                        break;
                }
                return true;
            }
        });

        initData();
        initPopMenu();

        return attachToSwipeBack(view);
    }



    /***
     * 初始化popupwindow数据
     */
    private void initData() {
        Request<String> recommendRequest = NoHttp.createStringRequest(Constant.spRecommendURL_NEW, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 0, recommendRequest, this, true, true);


        mMenuData1 = new ArrayList<>();
        String[] menuStr1 = new String[]{"全部", "粮油", "衣服", "图书", "电子产品",
                "酒水饮料", "水果"};
        Map<String, String> map1;
        for (int i = 0, len = menuStr1.length; i < len; ++i) {
            map1 = new HashMap<>();
            map1.put("name", menuStr1[i]);
            mMenuData1.add(map1);
        }

        mMenuData2 = new ArrayList<>();
        String[] menuStr2 = new String[]{"综合排序", "配送费最低"};
        Map<String, String> map2;
        for (int i = 0, len = menuStr2.length; i < len; ++i) {
            map2 = new HashMap<>();
            map2.put("name", menuStr2[i]);
            mMenuData2.add(map2);
        }

        mMenuData3 = new ArrayList<>();
        String[] menuStr3 = new String[]{"优惠活动", "特价活动", "免配送费",
                "可在线支付"};
        Map<String, String> map3;
        for (int i = 0, len = menuStr3.length; i < len; ++i) {
            map3 = new HashMap<>();
            map3.put("name", menuStr3[i]);
            mMenuData3.add(map3);
        }

        mAdapter = new CommenAdapter<GoodsInfo.ResultBean.GoodlistBean>(mDatalist) {
            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                ViewHolder holder = ViewHolder.get(getActivity(), R.layout.goods_list_item, convertView, viewGroup);
                TextView tvTitle = holder.getView(R.id.title);
                TextView tvContent = holder.getView(R.id.tv_content);
                TextView tvPrice = holder.getView(R.id.price);
                TextView tvValue = holder.getView(R.id.value);
                TextView tvBought = holder.getView(R.id.count);


                tvTitle.setText(mDatalist.get(position).getProduct());
                tvContent.setText(mDatalist.get(position).getTitle());
                tvPrice.setText(mDatalist.get(position).getPrice());
                tvValue.setText(mDatalist.get(position).getValue());
                tvValue.setPaintFlags(tvValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvBought.setText("已售:" + mDatalist.get(position).getBought());

                Uri uri = Uri.parse(mDatalist.get(position).getImages().get(0).getImage());
                SimpleDraweeView draweeView = holder.getView(R.id.iv_icon2);
                draweeView.setImageURI(uri);
                return holder.getConvertView();
            }
        };
        mSupplierListLv.setAdapter(mAdapter);
        mSupplierListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String goods_id = mDatalist.get(i).getGoods_id();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("goods_id", goods_id);
                startActivity(intent);
            }
        });
    }

    /***
     * 初始化popupwindow
     */
    private void initPopMenu() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.popwin_supplier_list, null);
        mPopListview = (ListView) popView.findViewById(R.id.popwin_supplier_list_lv);
        popupMenu = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.MATCH_PARENT);
        popupMenu.setOutsideTouchable(true);
        popupMenu.setBackgroundDrawable(new BitmapDrawable());
        popupMenu.setFocusable(true);
        popupMenu.setAnimationStyle(R.style.popwin_anim_style);
        popupMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mProductTv.setTextColor(Color.parseColor("#5a5959"));
                mSortTv.setTextColor(Color.parseColor("#5a5959"));
                mActivityTv.setTextColor(Color.parseColor("#5a5959"));
            }
        });

        popView.findViewById(R.id.popwin_supplier_list_bottom)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupMenu.dismiss();
                    }
                });

        mMenuAdapter1 = new SimpleAdapter(getActivity(), mMenuData1, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mMenuAdapter2 = new SimpleAdapter(getActivity(), mMenuData2, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mMenuAdapter3 = new SimpleAdapter(getActivity(), mMenuData3, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mPopListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupMenu.dismiss();
                switch (menuIndex) {
                    case 0:
                        String currentProduct = mMenuData1.get(i).get("name");
                        mSupplierListTitleTv.setText(currentProduct);
                        mProductTv.setText(currentProduct);
                        break;
                    case 1:
                        String currentSort = mMenuData2.get(i).get("name");
                        mSupplierListTitleTv.setText(currentSort);
                        mSortTv.setText(currentSort);
                        break;
                    case 2:
                        String currentAct = mMenuData3.get(i).get("name");
                        mSupplierListTitleTv.setText(currentAct);
                        mActivityTv.setText(currentAct);
                        break;
                }
            }
        });


    }


    @OnClick({R.id.supplier_list_product, R.id.supplier_list_sort, R.id.supplier_list_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.supplier_list_product:
                mProductTv.setTextColor(Color.parseColor("#39ac69"));
                mPopListview.setAdapter(mMenuAdapter1);
                popupMenu.showAsDropDown(mProduct, 0, 2);
                menuIndex = 0;
                break;
            case R.id.supplier_list_sort:
                mSortTv.setTextColor(Color.parseColor("#39ac69"));
                mPopListview.setAdapter(mMenuAdapter2);
                popupMenu.showAsDropDown(mSort, 0, 2);
                menuIndex = 1;
                break;
            case R.id.supplier_list_activity:
                mActivityTv.setTextColor(Color.parseColor("#39ac69"));
                mPopListview.setAdapter(mMenuAdapter3);
                popupMenu.showAsDropDown(mActivity, 0, 2);
                menuIndex = 2;
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what){
            case 0:
                GoodsInfo goodsInfo = new Gson().fromJson(response.get(), GoodsInfo.class);
                List<GoodsInfo.ResultBean.GoodlistBean> goodlist = goodsInfo.getResult().getGoodlist();
                mDatalist.addAll(goodlist);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }

}
