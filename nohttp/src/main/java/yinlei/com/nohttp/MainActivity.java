package yinlei.com.nohttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private TextView tv_result_post;

    String postUrl = "https://api.bmob.cn/1/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        tv_result_post = (TextView) findViewById(R.id.tv_post_resutl);
    }

    /**
     * NoHttp  get方式
     *
     * @param view
     */
    public void btnGet(View view) {
        String url = "http://www.baidu.com";

        //1.创建一个队列
        RequestQueue queue = NoHttp.newRequestQueue();

        //3、创建消息请求
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);

        //2.利用队列添加请求
        /**
         * what : 请求标识
         * request ： 请求
         * response : 请求回调监听
         */
        queue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                //设置结果
                result.setText(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        //


    }


    /**
     * post请求提交数据
     *
     * @param view
     */
    public void btnPost(View view) {
        RequestQueue queue = NoHttp.newRequestQueue();

        final Request<String> request = NoHttp.createStringRequest(postUrl, RequestMethod.POST);

        request.addHeader("X-Bmob-REST-API-Key", "2bfebfad889df974a01ab7867d411f75");
        request.addHeader("X-Bmob-Application-Id", "cae7c5400719de3e04d708f5c82d94e8");
        request.addHeader("Content-Type", "application/json");

        request.setDefineRequestBodyForJson("{\"username\" : \"1234567890\",\"password\" : \"654321\"}");


        queue.add(2, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                tv_result_post.setText(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

}
