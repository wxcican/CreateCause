package com.mzth.createcause.view.fragment.news.impl;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mzth.createcause.R;
import com.mzth.createcause.adapter.news.NewsAdapter;
import com.mzth.createcause.base.BaseFragment;
import com.mzth.createcause.entity.core.CoreContentEntity;
import com.mzth.createcause.util.CommonUtil;
import com.mzth.createcause.util.ConfigUtil;
import com.mzth.createcause.util.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class AuthorNewsFragment extends BaseFragment {

    private View view;
    private String authId;
    private Dialog dialog;
    private List<CoreContentEntity.InfromationContentList> mInfromationContentList = new ArrayList<>();
    private SwipeRefreshLayout srl_auth_news;
    private RecyclerView rv_auth_news;
    private int page = 0;
    private NewsAdapter mNewsAdapter;

    public static AuthorNewsFragment getAuthorNewsFragment(String authId) {
        AuthorNewsFragment mAuthorNewsFragment = new AuthorNewsFragment();
        mAuthorNewsFragment.authId = authId;
        return mAuthorNewsFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_author_news_fragment, container, false);
        return view;
    }

    @Override
    protected void initView() {
        srl_auth_news = CommonUtil.getCommonUtil().bindView(view, R.id.srl_auth_news);
        rv_auth_news = CommonUtil.getCommonUtil().bindView(view, R.id.rv_auth_news);
    }

    @Override
    protected void initSet() {

    }

    @Override
    protected void initAdapter() {
        rv_auth_news.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_auth_news.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL,
                CommonUtil.getCommonUtil().convertDIP(1),
                Color.parseColor("#f2f2f2")));
        mNewsAdapter = new NewsAdapter(mInfromationContentList);
        rv_auth_news.setAdapter(mNewsAdapter);
        mNewsAdapter.setEmptyView(mNoDataView);
    }



    @Override
    protected void initListner() {
        //刷新
        srl_auth_news.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getNetData(page, false, true);
            }
        });
        //加载
        mNewsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page ++;
                getNetData(page, true, false);
            }
        },rv_auth_news);
        //Item
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent();
                mIntent.setClass(getActivity(), NewsInfoActivity.class);
                mIntent.putExtra("NEWS_ID",mInfromationContentList.get(position).getId());
                mIntent.putExtra("NEWS_TITLE",mInfromationContentList.get(position).getTitle());
                String content = mInfromationContentList.get(position).getContent();
                mIntent.putExtra("NEWS_CONTENT",content.length()>50?content.substring(0,50):content);
                mIntent.putExtra("NEWS_IMG",mInfromationContentList.get(position).getImage());
                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void initData() {
        getNetData(0, false, false);
    }

    private void getNetData(int page, final boolean isLoad, final boolean isRefresh) {
        OkGo.<String>post(ConfigUtil.AUTH_LIST)
                .params("author_id", authId)
                .params("type", "1")
                .params("page", page + "")
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (!isLoad && !isRefresh) {
                            dialog = CommonUtil.getCommonUtil().ejectLaoding(mContext, "正在加载");
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (!isLoad && !isRefresh) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                                dialog = null;
                            }
                        }
                        if (srl_auth_news.isRefreshing()) {
                            srl_auth_news.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+ response.body());
                        try {
                            CoreContentEntity mCoreContentEntity = new CoreContentEntity(new JSONObject(response.body()));
                            if (mCoreContentEntity.getStatus() == 1) {
                                if (isRefresh) {
                                    mInfromationContentList.clear();
                                }
                                List<CoreContentEntity.InfromationContentList> infromationContentLists = mCoreContentEntity.getmInfromationContentLists();
                                mInfromationContentList.addAll(infromationContentLists);
                                mNewsAdapter.notifyDataSetChanged();
                                if((mCoreContentEntity.getPage()+1)<mCoreContentEntity.getPage_total()){
                                    mNewsAdapter.loadMoreComplete();
                                }else {
                                    mNewsAdapter.loadMoreEnd();
                                }
                            } else {
                                CommonUtil.getCommonUtil().toast(mCoreContentEntity.getErr_msg());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
