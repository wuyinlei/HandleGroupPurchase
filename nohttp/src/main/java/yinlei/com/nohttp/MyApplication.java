package yinlei.com.nohttp;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MyApplication.java
 * @author: 若兰明月
 * @date: 2016-08-27 09:07
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        NoHttp.initialize(this);
    }
}
