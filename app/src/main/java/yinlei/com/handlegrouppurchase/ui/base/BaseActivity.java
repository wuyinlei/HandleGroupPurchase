package yinlei.com.handlegrouppurchase.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: BaseActivity.java
 * @author: 若兰明月
 * @date: 2016-08-27 17:22
 */

public abstract class BaseActivity extends AppCompatActivity {
    private String mtype;//  手机型号
    private String imei; //设备号 手机的唯一标示
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化布局等
     */
    private void init() {
        setContentView(getLayout());
        mContext = BaseActivity.this;
        findView();
        loadData();
        setListener();
    }


    /**
     * 描述：Toast提示文本.
     *
     * @param text
     */
    public void showToast(String text) {
        Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resourse
     */
    public void showToast(int resourse) {
        Toast.makeText(this, resourse, Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：展示错误日志
     *
     * @param tag
     * @param msg
     */
    protected void showLogError(String tag, String msg) {
        Log.e(tag, msg);
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


    /**
     * 获取IMEI号，IESI号，手机型号 返回值：0为唯一设备号 1为手机型号
     */
    public ArrayList<String> getInfo() {
        TelephonyManager mTm = (TelephonyManager) this
                .getSystemService(TELEPHONY_SERVICE);
        imei = mTm.getDeviceId();// 唯一设备号
        mtype = android.os.Build.MODEL;// 手机型号
        ArrayList<String> minfo = new ArrayList<String>();

        if (null == imei && imei.equals("") && imei.length() <= 0) {
            minfo.add(0, "其他");
        } else {
            minfo.add(0, imei);
        }

        if (null == mtype && mtype.equals("") && mtype.length() <= 0) {
            minfo.add(1, "其他");
        } else {
            minfo.add(1, mtype);
        }

        return minfo;
    }



}
