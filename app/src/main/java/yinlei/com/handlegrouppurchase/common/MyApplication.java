package yinlei.com.handlegrouppurchase.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yolanda.nohttp.NoHttp;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MyApplication.java
 * @author: 若兰明月
 * @date: 2016-08-26 08:34
 */

public class MyApplication extends Application{

    private boolean flag = true;

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);

        //Fresco图片加载框架初始化
        Fresco.initialize(this);
        ZXingLibrary.initDisplayOpinion(this);


        //bmob的初始化
        Bmob.initialize(this, "2d2003b367611282feeb0533deb70120");

        if (flag == true) {
            flag = false;
            BmobUpdateAgent.initAppVersion();
        }
    }
}
