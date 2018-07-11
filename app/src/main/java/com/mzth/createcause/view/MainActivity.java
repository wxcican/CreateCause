package com.mzth.createcause.view;import android.content.BroadcastReceiver;import android.content.Context;import android.content.Intent;import android.content.IntentFilter;import android.graphics.Color;import android.support.v4.app.FragmentTransaction;import android.text.TextUtils;import android.view.LayoutInflater;import android.view.View;import android.widget.Button;import android.widget.FrameLayout;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.PopupWindow;import android.widget.TextView;import com.mzth.createcause.R;import com.mzth.createcause.base.BaseActivity;import com.mzth.createcause.util.CommonUtil;import com.mzth.createcause.util.RetentionDataUtil;import com.mzth.createcause.util.premiss.RunStart;import com.mzth.createcause.view.fragment.home.HomeFragment;import com.mzth.createcause.view.fragment.news.impl.NewsFragment;import com.mzth.createcause.view.fragment.publish.PublishActivity;import com.mzth.createcause.view.fragment.user.UserFragment;import com.mzth.createcause.view.fragment.video.impl.VideoFragment;import com.mzth.createcause.view.login.LoginActivity;import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;public class MainActivity extends BaseActivity {    public static final int STATUS_HOME = 0;    public static final int STATUS_NEWS = 1;    public static final int STATUS_VIDEO = 2;    public static final int STATUS_USER = 3;    private FrameLayout fl_main;    //首页    private LinearLayout ll_main_1;    private TextView tv_main_1;    private ImageView iv_main_1;    //资讯    private LinearLayout ll_main_2;    private TextView tv_main_2;    private ImageView iv_main_2;    //视频    private LinearLayout ll_main_3;    private TextView tv_main_3;    private ImageView iv_main_3;    //我的    private LinearLayout ll_main_4;    private TextView tv_main_4;    private ImageView iv_main_4;    //发布，圈子    private Button main_btn_publish;    private MyBroadcastReceiver mMyBroadcastReceiver;    private boolean testFlag = false;    private HomeFragment mHomeFragment;    private NewsFragment mNewsFragment;    private VideoFragment mVideoFragment;    private UserFragment mUserFragment;    @Override    protected void setActivity() {        exitStatus(true);        setContentView(R.layout.activity_main);        RunStart.requestPermissions(this);    }    @Override    protected void initView() {        ll_main_1 = CommonUtil.getCommonUtil().bindView(this, R.id.ll_main_1);        ll_main_2 = CommonUtil.getCommonUtil().bindView(this, R.id.ll_main_2);        ll_main_3 = CommonUtil.getCommonUtil().bindView(this, R.id.ll_main_3);        ll_main_4 = CommonUtil.getCommonUtil().bindView(this, R.id.ll_main_4);        tv_main_1 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_main_1);        tv_main_2 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_main_2);        tv_main_3 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_main_3);        tv_main_4 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_main_4);        iv_main_1 = CommonUtil.getCommonUtil().bindView(this, R.id.iv_main_1);        iv_main_2 = CommonUtil.getCommonUtil().bindView(this, R.id.iv_main_2);        iv_main_3 = CommonUtil.getCommonUtil().bindView(this, R.id.iv_main_3);        iv_main_4 = CommonUtil.getCommonUtil().bindView(this, R.id.iv_main_4);        fl_main = CommonUtil.getCommonUtil().bindView(this, R.id.fl_main);        main_btn_publish = CommonUtil.getCommonUtil().bindView(this,R.id.main_btn_publish);    }    @Override    protected void initSet() {        selectStatus(STATUS_HOME);    }    @Override    protected void initAdapter() {    }    @Override    protected void initData() {    }    @Override    protected void initListener() {        ClickListener mClickListener = new ClickListener();        ll_main_1.setOnClickListener(mClickListener);        ll_main_2.setOnClickListener(mClickListener);        ll_main_3.setOnClickListener(mClickListener);        ll_main_4.setOnClickListener(mClickListener);        main_btn_publish.setOnClickListener(mClickListener);    }    /**     * 按钮状态     *     * @param status     */    public void selectStatus(int status) {        GSYVideoView.releaseAllVideos();        switch (status) {            case STATUS_HOME:                showHomeFragment();                iv_main_1.setImageResource(R.drawable.homepage_icon_sel);                iv_main_2.setImageResource(R.drawable.news_icon);                iv_main_3.setImageResource(R.drawable.m_video_icon);                iv_main_4.setImageResource(R.drawable.user_icon);                tv_main_1.setTextColor(Color.parseColor("#0f9aeb"));                tv_main_2.setTextColor(Color.parseColor("#505050"));                tv_main_3.setTextColor(Color.parseColor("#505050"));                tv_main_4.setTextColor(Color.parseColor("#505050"));                break;            case STATUS_NEWS:                showNewsFragment();                iv_main_1.setImageResource(R.drawable.homepage_icon);                iv_main_2.setImageResource(R.drawable.news_icon_sel);                iv_main_3.setImageResource(R.drawable.m_video_icon);                iv_main_4.setImageResource(R.drawable.user_icon);                tv_main_2.setTextColor(Color.parseColor("#0f9aeb"));                tv_main_1.setTextColor(Color.parseColor("#505050"));                tv_main_3.setTextColor(Color.parseColor("#505050"));                tv_main_4.setTextColor(Color.parseColor("#505050"));                break;            case STATUS_VIDEO:                showVideoFragment();                iv_main_1.setImageResource(R.drawable.homepage_icon);                iv_main_2.setImageResource(R.drawable.news_icon);                iv_main_3.setImageResource(R.drawable.m_video_icon_sel);                iv_main_4.setImageResource(R.drawable.user_icon);                tv_main_3.setTextColor(Color.parseColor("#0f9aeb"));                tv_main_2.setTextColor(Color.parseColor("#505050"));                tv_main_1.setTextColor(Color.parseColor("#505050"));                tv_main_4.setTextColor(Color.parseColor("#505050"));                break;            case STATUS_USER:                if (testFlag) {                    showUserFragment();                    iv_main_1.setImageResource(R.drawable.homepage_icon);                    iv_main_2.setImageResource(R.drawable.news_icon);                    iv_main_3.setImageResource(R.drawable.m_video_icon);                    iv_main_4.setImageResource(R.drawable.user_icon_sel);                    tv_main_4.setTextColor(Color.parseColor("#0f9aeb"));                    tv_main_2.setTextColor(Color.parseColor("#505050"));                    tv_main_3.setTextColor(Color.parseColor("#505050"));                    tv_main_1.setTextColor(Color.parseColor("#505050"));                    if(mMyBroadcastReceiver!=null){                        unregisterReceiver(mMyBroadcastReceiver);                        mMyBroadcastReceiver=null;                    }                }else {                    Intent mIntent = new Intent();                    mIntent.setClass(mContext, LoginActivity.class);                    startActivity(mIntent);                }                break;        }    }    private class ClickListener implements View.OnClickListener {        @Override        public void onClick(View view) {            switch (view.getId()) {                case R.id.ll_main_1:                    selectStatus(STATUS_HOME);                    break;                case R.id.ll_main_2:                    selectStatus(STATUS_NEWS);                    break;                case R.id.ll_main_3:                    selectStatus(STATUS_VIDEO);                    break;                case R.id.ll_main_4:                    if (TextUtils.isEmpty(RetentionDataUtil.getRetention().getString("userId", "")) || TextUtils.isEmpty(RetentionDataUtil.getRetention().getString("userKey", ""))) {                        testFlag = false;                    } else {                        testFlag = true;                    }                    selectStatus(STATUS_USER);                    break;                case R.id.main_btn_publish:                    //弹出底部选择框                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);                    View popupView = inflater.inflate(R.layout.popup_publish,null);                    final PopupWindow popupWindow = CommonUtil.getCommonUtil().showPopWindowIsButtom(MainActivity.this,popupView,getWindow().getDecorView(),getWindow());                    //根据选择不同，进入不同样式的“发布页面”                    popupView.findViewById(R.id.sendtext_popup_publish).setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View view) {                            PublishActivity.startPublishActivity(MainActivity.this,PublishActivity.MODE_TEXT);                            popupWindow.dismiss();                        }                    });                    popupView.findViewById(R.id.sendvideo_popup_publish).setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View view) {                            // TODO: 2018/7/10  跳转到发布视频页面                            CommonUtil.getCommonUtil().toast("跳转到发布视频页面");                            popupWindow.dismiss();                        }                    });                    //点击取消关闭                    popupView.findViewById(R.id.cancel_popup_publish).setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View view) {                            popupWindow.dismiss();                        }                    });                    break;            }        }    }    @Override    public void onResume() {        super.onResume();        mMyBroadcastReceiver = new MyBroadcastReceiver();        IntentFilter intentFilter = new IntentFilter();        intentFilter.addAction(MAIN_ACTION);        registerReceiver(mMyBroadcastReceiver, intentFilter);    }    private class MyBroadcastReceiver extends BroadcastReceiver {        @Override        public void onReceive(Context context, Intent intent) {            if (TextUtils.equals(intent.getAction(), MAIN_ACTION)) {                testFlag = true;                selectStatus(STATUS_USER);            }        }    }    /**     * 首页     */    private void showHomeFragment() {        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();        if (mHomeFragment == null) {            mHomeFragment = new HomeFragment();            mFragmentTransaction.add(R.id.fl_main, mHomeFragment);        }        mFragmentTransaction.show(mHomeFragment);        if (mNewsFragment != null) {            mFragmentTransaction.hide(mNewsFragment);        }        if (mVideoFragment != null) {            mFragmentTransaction.hide(mVideoFragment);        }        if (mUserFragment != null) {            mFragmentTransaction.hide(mUserFragment);        }        mFragmentTransaction.commitAllowingStateLoss();    }    /**     * 资讯     */    private void showNewsFragment() {        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();        if (mNewsFragment == null) {            mNewsFragment = new NewsFragment();            mFragmentTransaction.add(R.id.fl_main, mNewsFragment);        }        mFragmentTransaction.show(mNewsFragment);        if (mHomeFragment != null) {            mFragmentTransaction.hide(mHomeFragment);        }        if (mVideoFragment != null) {            mFragmentTransaction.hide(mVideoFragment);        }        if (mUserFragment != null) {            mFragmentTransaction.hide(mUserFragment);        }        mFragmentTransaction.commitAllowingStateLoss();    }    /**     * 视频     */    private void showVideoFragment() {        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();        if (mVideoFragment == null) {            mVideoFragment = new VideoFragment();            mFragmentTransaction.add(R.id.fl_main, mVideoFragment);        }        mFragmentTransaction.show(mVideoFragment);        if (mNewsFragment != null) {            mFragmentTransaction.hide(mNewsFragment);        }        if (mHomeFragment != null) {            mFragmentTransaction.hide(mHomeFragment);        }        if (mUserFragment != null) {            mFragmentTransaction.hide(mUserFragment);        }        mFragmentTransaction.commitAllowingStateLoss();    }    /**     * 我的     */    private void showUserFragment() {        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();        if (mUserFragment == null) {            mUserFragment = new UserFragment();            mFragmentTransaction.add(R.id.fl_main, mUserFragment);        }        mFragmentTransaction.show(mUserFragment);        if (mNewsFragment != null) {            mFragmentTransaction.hide(mNewsFragment);        }        if (mVideoFragment != null) {            mFragmentTransaction.hide(mVideoFragment);        }        if (mHomeFragment != null) {            mFragmentTransaction.hide(mHomeFragment);        }        mFragmentTransaction.commitAllowingStateLoss();    }}