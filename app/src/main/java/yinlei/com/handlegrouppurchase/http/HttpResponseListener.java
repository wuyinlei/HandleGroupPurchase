package yinlei.com.handlegrouppurchase.http;

import android.content.Context;
import android.content.DialogInterface;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import yinlei.com.handlegrouppurchase.widget.WaitDialog;

/**
 * 请求响应的结果
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: HttpResponseListener.java
 * @author: 若兰明月
 * @date: 2016-08-27 09:59
 */

public class HttpResponseListener<T> implements OnResponseListener<T> {

    private HttpListener mHttpListener;
    private WaitDialog mWaitDialog;
    boolean isLoading;
    private Request<T> mTRequest;

    public HttpResponseListener(HttpListener listener,Request<T> request, Context context, boolean canCancel, boolean isLoading) {
        this.isLoading = isLoading;
        this.mTRequest = request;
        this.mHttpListener = listener;
        if (context != null) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancel);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mWaitDialog.cancel();
                }
            });
        }
    }

    @Override
    public void onStart(int what) {
        if (isLoading && mWaitDialog != null && !mWaitDialog.isShowing()) {
            mWaitDialog.show();
        }

    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mHttpListener != null) {
            mHttpListener.onSucceed(what, response);
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        if (mHttpListener != null) {
            mHttpListener.onFailed(what, response);
        }
    }

    @Override
    public void onFinish(int what) {
        if (isLoading && mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
        }
    }
}
