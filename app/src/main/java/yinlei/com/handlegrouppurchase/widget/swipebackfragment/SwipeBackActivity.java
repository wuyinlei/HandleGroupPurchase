
package yinlei.com.handlegrouppurchase.widget.swipebackfragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import yinlei.com.handlegrouppurchase.R;


/**
 * SwipeBackActivity
 * Created by YoKeyword on 16/4/19.
 */
public abstract class SwipeBackActivity extends AppCompatActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private int mDefaultFragmentBackground = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onActivityCreate();
        init();
    }

    /**
     * 初始化布局等
     */
    private void init() {
        setContentView(getLayout());
        findView();
        loadData();
        setListener();
    }

    /**
     * 加载布局
     */
    protected abstract int getLayout();

    /**
     * 初始化组件
     */
    protected abstract void findView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 注册监听
     */
    protected abstract void setListener();



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this);
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return view;
    }

    void onActivityCreate() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    public boolean swipeBackPriority() {
        return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
    protected void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
        mDefaultFragmentBackground = backgroundRes;
    }

    int getDefaultFragmentBackground() {
        return mDefaultFragmentBackground;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right, R.anim.anit_out_left_right);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

}
