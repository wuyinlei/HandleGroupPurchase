package yinlei.com.handlegrouppurchase.http;

import com.yolanda.nohttp.rest.Response;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: HttpListener.java
 * @author: 若兰明月
 * @date: 2016-08-27 10:02
 */

public interface HttpListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);
}
