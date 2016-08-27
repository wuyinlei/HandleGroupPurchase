package yinlei.com.handlegrouppurchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.ui.mainfragment.GoodsBean;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: GoodsAdapter.java
 * @author: 若兰明月
 * @date: 2016-08-27 10:55
 */

public class GoodsAdapter extends BaseAdapter {


    private Context mContext;

    public GoodsAdapter(Context context, List<GoodsBean.ResultBean.GoodlistBean> goodlistBeen) {
        this.mContext = context;
        this.mGoodlistBeen = goodlistBeen;
    }

    private List<GoodsBean.ResultBean.GoodlistBean> mGoodlistBeen;



    @Override
    public int getCount() {
        return mGoodlistBeen == null ? 0 : mGoodlistBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return mGoodlistBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.goods_list_item,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        GoodsBean.ResultBean.GoodlistBean goodlistBean = mGoodlistBeen.get(i);
        Picasso.with(mContext).load(goodlistBean.getImages().get(1).getImage()).into(holder.mPhoto);
        holder.mTitle.setText(goodlistBean.getTitle());
        holder.mTvContent.setText(goodlistBean.getShort_title());
        holder.mPrice.setText(goodlistBean.getPrice());
        holder.mValue.setText(goodlistBean.getValue());

        return view;
    }

    class ViewHolder  {

        @Bind(R.id.photo)
        ImageView mPhoto;
        @Bind(R.id.iv_icon2)
        ImageView mIvIcon2;
        @Bind(R.id.appoitment_img)
        ImageView mAppoitmentImg;
        @Bind(R.id.loading)
        ProgressBar mLoading;
        @Bind(R.id.photo_layout)
        RelativeLayout mPhotoLayout;
        @Bind(R.id.tv_distance)
        TextView mTvDistance;
        @Bind(R.id.title)
        TextView mTitle;
        @Bind(R.id.fresh_order_layout)
        LinearLayout mFreshOrderLayout;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.comment_score)
        RatingBar mCommentScore;
        @Bind(R.id.price)
        TextView mPrice;
        @Bind(R.id.value)
        TextView mValue;
        @Bind(R.id.count)
        TextView mCount;
        @Bind(R.id.area)
        TextView mArea;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
