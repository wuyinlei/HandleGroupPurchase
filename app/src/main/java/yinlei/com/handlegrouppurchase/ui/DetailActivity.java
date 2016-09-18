package yinlei.com.handlegrouppurchase.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.ButterKnife;
import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.bean.DetailInfo;
import yinlei.com.handlegrouppurchase.constant.Constant;
import yinlei.com.handlegrouppurchase.http.CallServer;
import yinlei.com.handlegrouppurchase.http.HttpListener;
import yinlei.com.handlegrouppurchase.widget.ObservableScrollView;

public class DetailActivity extends AppCompatActivity implements HttpListener<String>,ObservableScrollView.ScrollViewLinstener {

    @Bind(R.id.iv_detail)
    SimpleDraweeView mIvDetail;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_decs)
    TextView mTvDecs;
    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.tv_bought)
    TextView mTvBought;
    @Bind(R.id.tv_title2)
    TextView mTvTitle2;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.web_detail)
    WebView mWebDetail;
    @Bind(R.id.web_notice)
    WebView mWebNotice;
    @Bind(R.id.list_recommend)
    ListView mListRecommend;
    @Bind(R.id.scrollView)
    ObservableScrollView mScrollView;
    @Bind(R.id.tv_titlebar)
    TextView mTvTitlebar;
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.iv_favor)
    ImageView mIvFavor;
    @Bind(R.id.iv_share)
    ImageView mIvShare;
    @Bind(R.id.layout_title)
    RelativeLayout mLayoutTitle;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_value)
    TextView mTvValue;
    @Bind(R.id.btn_buy)
    Button mBtnBuy;
    @Bind(R.id.layout_buy)
    RelativeLayout mLayoutBuy;


    private String mGoods_id;
    private DetailInfo mDetailInfo;
    private int mImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mGoods_id = intent.getStringExtra("goods_id");
        /**商品详情的请求**/
        Request<String> recommendRequest = NoHttp.createStringRequest(Constant.baseUrl + mGoods_id + ".txt", RequestMethod.GET);
        CallServer.getInstance().add(this, 0, recommendRequest, this, true, true);
        initListener();
    }

    private void initListener() {
        ViewTreeObserver vto = mIvDetail.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                mImageHeight = mIvDetail.getHeight();

                mScrollView.setLinstener(DetailActivity.this);
            }
        });

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case 0:
                Gson gson = new Gson();
                mDetailInfo = gson.fromJson(response.get(), DetailInfo.class);
                //本单详情的网页信息
                String details = mDetailInfo.getResult().getDetails();
                //温馨提示
                String notice = mDetailInfo.getResult().getNotice();

                mWebDetail.loadDataWithBaseURL("about:blank", details, "text/html", "UTF-8", null);
                mWebNotice.loadDataWithBaseURL("about:blank", notice, "text/html", "UTF-8", null);
                //标题
                mTvTitle.setText(mDetailInfo.getResult().getProduct());
                //描述
                mTvDecs.setText(mDetailInfo.getResult().getTitle());
                //已售
                mTvBought.setText(mDetailInfo.getResult().getValue());
                //详情界面的图片
                Uri uri = Uri.parse(mDetailInfo.getResult().getImages().get(0).getImage());
                mIvDetail.setImageURI(uri);
                break;

        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }



    @Override
    public void onScrollViewScrollChanged(int x, int y, int oldl, int oldy) {
        if (y <= 0) {
            mTvTitlebar.setVisibility(View.GONE);
            mLayoutTitle.setBackgroundColor(Color.argb(0, 0, 0, 0));
        } else if (y > 0 && y <= mImageHeight) {
            float scale = (float) y / mImageHeight;
            float alpha = (255 * scale);
            mTvTitlebar.setVisibility(View.VISIBLE);
            mTvTitlebar.setText(mDetailInfo.getResult().getProduct());
            mTvTitlebar.setTextColor(Color.argb((int) alpha, 0, 0, 0));
            mLayoutTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {
            mTvTitlebar.setVisibility(View.VISIBLE);
            mTvTitlebar.setText(mDetailInfo.getResult().getProduct());
            mTvTitlebar.setTextColor(Color.argb((int) 255, 0, 0, 0));
            mLayoutTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }
}
