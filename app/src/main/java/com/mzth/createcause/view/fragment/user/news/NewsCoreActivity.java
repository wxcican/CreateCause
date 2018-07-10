package com.mzth.createcause.view.fragment.user.news;import android.graphics.Color;import android.support.design.widget.TabLayout;import android.support.v4.app.FragmentTransaction;import android.view.View;import android.widget.ImageView;import android.widget.TextView;import com.mzth.createcause.R;import com.mzth.createcause.base.BaseActivity;import com.mzth.createcause.util.CommonUtil;/** * 消息中心 */public class NewsCoreActivity extends BaseActivity {    private TextView base_title;    private ImageView base_iv;    private TextView base_tv;    private TextView tv_news_core_1;    private TextView tv_news_core_2;    private View v_news_core_1;    private View v_news_core_2;    private final int TYPE_NEWS = 100;    private final int TYPE_NOTICE = 101;    //通知    private NewsCoreFragment mNewsCoreFragment;    //公告    private NoticeFragment mNoticeFragment;    @Override    protected void setActivity() {        setContentView(R.layout.activity_news_core);    }    @Override    protected void initView() {        base_title = CommonUtil.getCommonUtil().bindView(this, R.id.base_title);        base_iv = CommonUtil.getCommonUtil().bindView(this, R.id.base_iv);        base_tv = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv);        tv_news_core_1 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_news_core_1);        tv_news_core_2 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_news_core_2);        v_news_core_1 = CommonUtil.getCommonUtil().bindView(this, R.id.v_news_core_1);        v_news_core_2 = CommonUtil.getCommonUtil().bindView(this, R.id.v_news_core_2);    }    @Override    protected void initSet() {        base_title.setText("消息中心");        base_iv.setVisibility(View.GONE);        base_tv.setVisibility(View.GONE);        base_tv.setText("清空");        initButtonStatus(TYPE_NEWS);        newsCoreFragment();    }    @Override    protected void initAdapter() {    }    @Override    protected void initData() {    }    @Override    protected void initListener() {        tv_news_core_1.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                initButtonStatus(TYPE_NEWS);            }        });        tv_news_core_2.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                initButtonStatus(TYPE_NOTICE);            }        });    }    /**     * 消息     */    private void newsCoreFragment() {        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();        if (mNewsCoreFragment == null) {            mNewsCoreFragment = new NewsCoreFragment();            mFragmentTransaction.add(R.id.fl_user_news_core, mNewsCoreFragment);            mNewsCoreFragment.getTitleTextView(tv_news_core_1);        }        mFragmentTransaction.show(mNewsCoreFragment);        if (mNoticeFragment != null) {            mFragmentTransaction.hide(mNoticeFragment);        }        mFragmentTransaction.commitAllowingStateLoss();    }    /**     * 公告     */    private void noticeFragment() {        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();        if (mNoticeFragment == null) {            mNoticeFragment = new NoticeFragment();            mFragmentTransaction.add(R.id.fl_user_news_core, mNoticeFragment);            mNoticeFragment.getNoticeTextView(tv_news_core_2);        }        mFragmentTransaction.show(mNoticeFragment);        if (mNewsCoreFragment != null) {            mFragmentTransaction.hide(mNewsCoreFragment);        }        mFragmentTransaction.commitAllowingStateLoss();    }    /**     * 初始化按钮状态     *     * @param type     */    private void initButtonStatus(int type) {        switch (type) {            case TYPE_NEWS:                tv_news_core_1.setTextColor(getResources().getColor(R.color.colorMain));                tv_news_core_2.setTextColor(Color.parseColor("#505050"));                v_news_core_1.setVisibility(View.VISIBLE);                v_news_core_2.setVisibility(View.INVISIBLE);                newsCoreFragment();                break;            case TYPE_NOTICE:                tv_news_core_2.setTextColor(getResources().getColor(R.color.colorMain));                tv_news_core_1.setTextColor(Color.parseColor("#505050"));                v_news_core_2.setVisibility(View.VISIBLE);                v_news_core_1.setVisibility(View.INVISIBLE);                noticeFragment();                break;        }    }}