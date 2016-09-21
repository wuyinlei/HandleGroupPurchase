package yinlei.com.handlegrouppurchase.ui.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yinlei.com.handlegrouppurchase.R;
import yinlei.com.handlegrouppurchase.utils.AppUtils;
import yinlei.com.handlegrouppurchase.utils.DataCleanManager;
import yinlei.com.handlegrouppurchase.widget.swipebackfragment.SwipeBackFragment;

/**
 * 更多界面
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: MoreFragment.java
 * @author: 若兰明月
 * @date: 2016-08-24 21:04
 */

public class MoreFragment extends SwipeBackFragment {

    @Bind(R.id.iv_wifi_switch)
    CheckBox mIvWifiSwitch;
    @Bind(R.id.image_settings_layout)
    RelativeLayout mImageSettingsLayout;
    @Bind(R.id.textView1)
    TextView mTextView1;
    @Bind(R.id.iv_remind_switch)
    CheckBox mIvRemindSwitch;
    @Bind(R.id.set_remind_layout)
    RelativeLayout mSetRemindLayout;
    @Bind(R.id.share_setting_layout)
    RelativeLayout mShareSettingLayout;
    @Bind(R.id.cache_size)
    TextView mCacheSize;
    @Bind(R.id.clear_cache_layout)
    RelativeLayout mClearCacheLayout;
    @Bind(R.id.good_comment_layout)
    RelativeLayout mGoodCommentLayout;
    @Bind(R.id.kefu_icom)
    ImageView mKefuIcom;
    @Bind(R.id.tv_feedback)
    TextView mTvFeedback;
    @Bind(R.id.iv_feedback_hint)
    ImageView mIvFeedbackHint;
    @Bind(R.id.feedback_layout)
    RelativeLayout mFeedbackLayout;
    @Bind(R.id.tv_tel)
    TextView mTvTel;
    @Bind(R.id.tv_kf)
    TextView mTvKf;
    @Bind(R.id.kefu_layout)
    RelativeLayout mKefuLayout;
    @Bind(R.id.version)
    TextView mVersion;
    @Bind(R.id.tv_check_update)
    TextView mTvCheckUpdate;
    @Bind(R.id.iv_check_update_hint)
    ImageView mIvCheckUpdateHint;
    @Bind(R.id.rl_softvare_update)
    RelativeLayout mRlSoftvareUpdate;
    @Bind(R.id.help_layout)
    RelativeLayout mHelpLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, attachToSwipeBack(view));
        return attachToSwipeBack(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @OnClick({R.id.share_setting_layout, R.id.good_comment_layout, R.id.clear_cache_layout, R.id.kefu_layout, R.id.rl_softvare_update})
    public void click(View view){
        switch (view.getId()){
            case R.id.clear_cache_layout:
                DataCleanManager.clearAllCache(getActivity());
                Toast.makeText(getActivity(), "缓存清除成功!", Toast.LENGTH_SHORT).show();
                mCacheSize.setText("0KB");
                break;
            case R.id.kefu_layout:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "13718989054"));
                //开启系统拨号器
                startActivity(intent);
                break;
            case R.id.good_comment_layout:
                String packageName = AppUtils.getAppInfo(getContext()).getPackageName();
                goToMarket(packageName);
                break;
        }
    }


    public void goToMarket(String packageName){
        try {

            String str = "market://detail?id=" + packageName;

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse(str));

            startActivity(intent);

        }catch (Exception e){
            //打开应用商店失败 可能是因为手机没有安装应用市场
            Toast.makeText(getActivity(), "打开应用商店失败!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            //当应用上线
            String url = "";
            //调用浏览器进入商城
            openLinkByUrl(url);

        }
    }


    /***
     * 调用系统的浏览器打开网页
     * @param url
     */
    private void openLinkByUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
