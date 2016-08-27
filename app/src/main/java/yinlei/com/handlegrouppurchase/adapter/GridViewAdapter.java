package yinlei.com.handlegrouppurchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.constant.MyConstant;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: GridViewAdapter.java
 * @author: 若兰明月
 * @date: 2016-08-27 10:51
 */

public class GridViewAdapter extends BaseAdapter {

    //我的数据在utils包下的MyConstant中定义好了
    private LayoutInflater inflater;
    private int page;

    /**
     * 根据传入的page来判断是要显示的第几页的数据
     *
     * @param context 上下文
     * @param page    第几页
     */
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
//                vh.iv_navsort.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        // TODO Auto-generated method stub
//                    }
//                });
        } else {
            //vh.iv_navsort.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                    }
//                });
//
            //   }
        }

        return convertView;
    }

    //gridview 适配器的holder类
    class ViewHolder {
        ImageView iv_navsort;
        TextView tv_navsort;
    }

}