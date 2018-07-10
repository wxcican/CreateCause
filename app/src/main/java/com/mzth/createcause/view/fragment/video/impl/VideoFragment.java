package com.mzth.createcause.view.fragment.video.impl;import android.app.Dialog;import android.content.Intent;import android.support.annotation.Nullable;import android.support.design.widget.TabLayout;import android.os.Bundle;import android.support.v4.app.Fragment;import android.support.v4.app.FragmentManager;import android.support.v4.app.FragmentPagerAdapter;import android.support.v4.view.ViewPager;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.RelativeLayout;import android.widget.TextView;import com.mzth.createcause.R;import com.mzth.createcause.base.BaseFragment;import com.mzth.createcause.entity.core.CoreTypeEntity;import com.mzth.createcause.presenter.core.VideoPresenter;import com.mzth.createcause.util.CommonUtil;import com.mzth.createcause.util.ConfigUtil;import com.mzth.createcause.view.fragment.video.IVideoFragment;import com.mzth.createcause.view.shareui.AllTypeActivity;import com.mzth.createcause.view.shareui.SearchActivity;import com.shuyu.gsyvideoplayer.GSYVideoManager;import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;import java.util.ArrayList;import java.util.List;public class VideoFragment extends BaseFragment implements IVideoFragment {    private TextView base_title;    private ImageView base_iv;    private TextView base_tv;    private RelativeLayout base_rl;    private LinearLayout base_ll;    private TabLayout tl_video;    //全部分类    private LinearLayout ll_video;    //资讯内容    private ViewPager vp_video;    private View view;    private VideoPresenter mVideoPresenter;    private Dialog dialog;    private List<Fragment> mFramgnets = new ArrayList<>();    public static int selectPosition = 0;    public static boolean idSelectPosition = false;    private List<String> titles = new ArrayList<>();    @Override    protected void initView() {        base_title = CommonUtil.getCommonUtil().bindView(view, R.id.base_title);        base_iv = CommonUtil.getCommonUtil().bindView(view, R.id.base_iv);        base_tv = CommonUtil.getCommonUtil().bindView(view, R.id.base_tv);        base_rl = CommonUtil.getCommonUtil().bindView(view, R.id.base_rl);        base_ll = CommonUtil.getCommonUtil().bindView(view, R.id.base_ll);        tl_video = CommonUtil.getCommonUtil().bindView(view, R.id.tl_video);        ll_video = CommonUtil.getCommonUtil().bindView(view, R.id.ll_video);        vp_video = CommonUtil.getCommonUtil().bindView(view, R.id.vp_video);    }    @Override    protected void initSet() {       // base_iv.setVisibility(View.VISIBLE);        base_iv.setVisibility(View.GONE);        base_tv.setVisibility(View.GONE);        base_ll.setVisibility(View.GONE);        base_iv.setImageResource(R.drawable.search_icon_white);        base_title.setText("视频");    }    @Override    protected void initAdapter() {    }    @Override    protected void initData() {        mVideoPresenter.getTypeData();    }    @Override    protected void initListner() {        //全部分类        ll_video.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent mIntent = new Intent();                mIntent.setClass(mContext, AllTypeActivity.class);                mIntent.putExtra("TYPE", "VIDEO");                startActivity(mIntent);            }        });        //搜索        base_rl.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Intent mIntent = new Intent();                mIntent.setClass(mContext, SearchActivity.class);                startActivity(mIntent);            }        });        tl_video.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {            @Override            public void onTabSelected(TabLayout.Tab tab) {            }            @Override            public void onTabUnselected(TabLayout.Tab tab) {                GSYVideoView.releaseAllVideos();            }            @Override            public void onTabReselected(TabLayout.Tab tab) {            }        });    }    @Override    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.activity_video_fragment, container, false);        mVideoPresenter = new VideoPresenter();        mVideoPresenter.initVideoFragment(this);        return view;    }    @Override    public void onRequestStart() {        dialog = CommonUtil.getCommonUtil().ejectLaoding(mContext, "正在加载");    }    @Override    public void onRequestFinish() {        if (dialog != null && dialog.isShowing()) {            dialog.dismiss();        }    }    @Override    public void onRequestError() {        CommonUtil.getCommonUtil().toast(ConfigUtil.HINT_OVERTIME);    }    @Override    public void onToast(String content) {    }    @Override    public void typeData(List<CoreTypeEntity.InfromactionTypeList> data) {        vp_video.setOffscreenPageLimit(data.size()-1);        for (int i = 0; i < data.size(); i++) {            titles.add(data.get(i).getCat_name());            mFramgnets.add(VideoContentFragment.getVideoContentFragment(data.get(i).getId()));        }        VideoPageAdpater mVideoPageAdpater = new VideoPageAdpater(getChildFragmentManager(), mFramgnets);        vp_video.setAdapter(mVideoPageAdpater);        tl_video.setupWithViewPager(vp_video);    }    /**     * ViewPage适配器     */    private class VideoPageAdpater extends FragmentPagerAdapter {        private List<Fragment> mFramgnets;        public VideoPageAdpater(FragmentManager fm, List<Fragment> framgnets) {            super(fm);            this.mFramgnets = framgnets;        }        @Override        public void destroyItem(ViewGroup container, int position, Object object) {            //super.destroyItem(container, position, object);        }        @Override        public Fragment getItem(int position) {            return mFramgnets.get(position);        }        @Override        public CharSequence getPageTitle(int position) {            return titles.get(position);        }        @Override        public int getCount() {            return mFramgnets != null ? mFramgnets.size() : 0;        }    }    @Override    public void onResume() {        super.onResume();        if (idSelectPosition) {            idSelectPosition = false;            vp_video.setCurrentItem(selectPosition);        }    }}