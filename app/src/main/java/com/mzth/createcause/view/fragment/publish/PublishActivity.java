package com.mzth.createcause.view.fragment.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzth.createcause.R;
import com.mzth.createcause.base.BaseActivity;
import com.mzth.createcause.base.BaseFragment;
import com.mzth.createcause.util.CommonUtil;

public class PublishActivity extends BaseActivity {
    private TextView base_title;
    private ImageView base_iv;
    private TextView base_tv,base_tv_publish;


    @Override
    protected void setActivity() {
        setContentView(R.layout.activity_publish);

    }

    @Override
    protected void initView() {
        base_title = CommonUtil.getCommonUtil().bindView(this, R.id.base_title);
        base_iv = CommonUtil.getCommonUtil().bindView(this, R.id.base_iv);
        base_tv = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv);
        base_tv_publish = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv_publish);
    }

    @Override
    protected void initSet() {
        base_iv.setVisibility(View.GONE);
        base_tv.setVisibility(View.GONE);
        base_title.setText("发布图文");
    }

    @Override
    protected void initAdapter() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
