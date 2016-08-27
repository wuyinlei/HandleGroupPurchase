package yinlei.com.handlegrouppurchase.ui.category;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.common.Global;
import yinlei.com.handlegrouppurchase.ui.base.BaseActivity;
import yinlei.com.handlegrouppurchase.ui.category.food.FoodFragment;
import yinlei.com.handlegrouppurchase.ui.category.movie.MovieFragment;

public class CategoryActivity extends BaseActivity {

    private FrameLayout container;
    String categoryName;
    FragmentManager mFragmentManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void findView() {
        container = (FrameLayout) findViewById(R.id.container);
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * {"美食","电影","休闲娱乐","丽人","闪惠团购","外卖","酒店","周边游",
     * "足疗按摩","KTV","运动健身","购物","到家","结婚","亲自","家装",
     * "爱车","优惠","演出","咖啡","宠物","景点","医院","全部分类"}
     */
    @Override
    protected void loadData() {
        categoryName = getIntent().getStringExtra(Global.CATEGORY_NAME);
        if (categoryName != null && !TextUtils.isEmpty(categoryName)) {
            switch (categoryName) {
                case "美食":
                    mFragmentManager.beginTransaction().replace(R.id.container, new FoodFragment()).commit();
                    break;
                case "电影":
                    mFragmentManager.beginTransaction().replace(R.id.container, new MovieFragment()).commit();
                    break;
                case "休闲娱乐":

                    break;
                case "丽人":

                    break;
                case "闪惠团购":

                    break;
                case "外卖":

                    break;
                case "酒店":

                    break;
                case "周边游":

                    break;
                case "足疗按摩":

                    break;
                case "KTV":

                    break;
                case "运动健身":

                    break;
                case "购物":

                    break;
                case "到家":

                    break;
                case "结婚":

                    break;
                case "亲自":

                    break;
                case "家装":

                    break;
                case "爱车":

                    break;
                case "优惠":

                    break;
                case "演出":

                    break;
                case "咖啡":

                    break;
                case "宠物":

                    break;
                case "景点":

                    break;
                case "医院":

                    break;

                case "全部分类":

                    break;

            }
        } else {
            Toast.makeText(mContext, "传值出错", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void setListener() {

    }
}
