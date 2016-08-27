package yinlei.com.handlegrouppurchase.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: WaitDialog.java
 * @author: 若兰明月
 * @date: 2016-08-27 10:06
 */

public class WaitDialog extends ProgressDialog {
    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage("正在请求,请稍后。。。");
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }
}
