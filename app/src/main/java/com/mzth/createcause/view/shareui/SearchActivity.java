package com.mzth.createcause.view.shareui;import android.graphics.Color;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.View;import android.widget.TextView;import com.mzth.createcause.R;import com.mzth.createcause.adapter.share.SearchAdapter;import com.mzth.createcause.adapter.share.SearchResultAdapter;import com.mzth.createcause.base.BaseActivity;import com.mzth.createcause.entity.SecrchResultEntity;import com.mzth.createcause.util.CommonUtil;import com.mzth.createcause.util.DividerItemDecoration;import java.util.ArrayList;import java.util.List;public class SearchActivity extends BaseActivity {    private RecyclerView rv_search;    //搜索    private TextView tv_search_search;    //搜索提示    private TextView tv_search_hint;    @Override    protected void setActivity() {        setContentView(R.layout.activity_search);    }    @Override    protected void initView() {        rv_search = CommonUtil.getCommonUtil().bindView(this, R.id.rv_search);        tv_search_search = CommonUtil.getCommonUtil().bindView(this, R.id.tv_search_search);        tv_search_hint = CommonUtil.getCommonUtil().bindView(this, R.id.tv_search_hint);    }    @Override    protected void initSet() {    }    @Override    protected void initAdapter() {    }    @Override    protected void initData() {        SearchAdapter mSearchAdapter = new SearchAdapter(mContext);        rv_search.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));        rv_search.addItemDecoration(new DividerItemDecoration(mContext,                LinearLayoutManager.HORIZONTAL,                CommonUtil.getCommonUtil().convertDIP(1),                Color.parseColor("#f2f2f2")));        rv_search.setAdapter(mSearchAdapter);        mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {            @Override            public void onItemClick(int position) {                CommonUtil.getCommonUtil().toast("" + position);            }        });    }    @Override    protected void initListener() {        tv_search_search.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                //搜索结果隐藏提示                tv_search_hint.setVisibility(View.GONE);                List<SecrchResultEntity> mSecrchResultEntitys = new ArrayList<>();                for (int i = 0; i < 10; i++) {                    SecrchResultEntity mSecrchResultEntity;                    if ((i % 2) == 0) {                        mSecrchResultEntity = new SecrchResultEntity(2);                        mSecrchResultEntity.setVideo("http://hhfykr1kpjkfwvc2wfd.exp.bcevod.com/mda-hh3qcvhpec7wdxfn/mda-hh3qcvhpec7wdxfn.mp4");                        mSecrchResultEntity.setImg("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D220/sign=569a592b17950a7b6a3549c63ad0625c/14ce36d3d539b60020610daae350352ac65cb77b.jpg");                    } else {                        mSecrchResultEntity = new SecrchResultEntity(1);                        mSecrchResultEntity.setVideo("http://hhfykr1kpjkfwvc2wfd.exp.bcevod.com/mda-hh3qcvhpec7wdxfn/mda-hh3qcvhpec7wdxfn.mp4");                        mSecrchResultEntity.setImg("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D220/sign=569a592b17950a7b6a3549c63ad0625c/14ce36d3d539b60020610daae350352ac65cb77b.jpg");                    }                    mSecrchResultEntitys.add(mSecrchResultEntity);                }                SearchResultAdapter mSearchResultAdapter = new SearchResultAdapter(mSecrchResultEntitys, mContext,SearchActivity.this);                rv_search.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));                rv_search.addItemDecoration(new DividerItemDecoration(mContext,                        LinearLayoutManager.HORIZONTAL,                        CommonUtil.getCommonUtil().convertDIP(1),                        Color.parseColor("#f2f2f2")));                rv_search.setAdapter(mSearchResultAdapter);            }        });    }}