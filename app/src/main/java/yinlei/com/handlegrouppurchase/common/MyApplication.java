package yinlei.com.handlegrouppurchase.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yolanda.nohttp.NoHttp;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MyApplication.java
 * @author: 若兰明月
 * @date: 2016-08-26 08:34
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);

        //Fresco图片加载框架初始化
        Fresco.initialize(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
