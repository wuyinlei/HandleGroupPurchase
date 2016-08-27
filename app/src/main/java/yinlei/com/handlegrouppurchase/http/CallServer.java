package yinlei.com.handlegrouppurchase.http;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: CallServer.java
 * @author: 若兰明月
 * @date: 2016-08-27 10:17
 */

public class CallServer {

    private static CallServer mCallServer;
    private final RequestQueue mRequestQueue;

    private CallServer() {
        mRequestQueue = NoHttp.newRequestQueue();
    }

    public synchronized static CallServer getInstance() {
        if (mCallServer == null) {
            mCallServer = new CallServer();
        }
        return mCallServer;
    }

    /**
     * 添加一个请求到队列中
     */
    public <T> void add(Context context, int what, Request<T> request, HttpListener<T> listener, boolean canCancel, boolean isLoading) {
        mRequestQueue.add(what, request, new HttpResponseListener<T>(listener, request, context, canCancel, isLoading));
    }
}
