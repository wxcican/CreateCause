package com.mzth.createcause.view.fragment.user.news;import android.support.annotation.Nullable;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.TextView;import com.mzth.createcause.R;import com.mzth.createcause.base.BaseFragment;public class NoticeFragment extends BaseFragment {    private View view;    private TextView noticeTitle;    @Override    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.activity_notice_fragment,container,false);        return view;    }    @Override    protected void initView() {    }    @Override    protected void initSet() {    }    @Override    protected void initAdapter() {    }    @Override    protected void initListner() {    }    @Override    protected void initData() {    }    /**     * 获取公告标题控件     * @param textView     */    public void getNoticeTextView(TextView textView) {        this.noticeTitle = textView;    }}