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
import com.mzth.createcause.adapter.video.VideoAdapter;
import com.mzth.createcause.base.BaseFragment;
import com.mzth.createcause.entity.core.CoreContentEntity;
import com.mzth.createcause.util.CommonUtil;
import com.mzth.createcause.util.ConfigUtil;
import com.mzth.createcause.util.DividerItemDecoration;
import com.mzth.createcause.view.fragment.video.impl.VideoInfoActivity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorVideoFragment extends BaseFragment {

    private String authId;
    private SwipeRefreshLayout srl_auth_video;
    private RecyclerView rv_auth_video;
    private View view;
    private Dialog dialog;
    private List<CoreContentEntity.InfromationContentList> mInfromationContentList = new ArrayList<>();
    private VideoAdapter mVideoAdapter;

    public static AuthorVideoFragment getAuthorVideoFragment(String authId) {
        AuthorVideoFragment mAuthorVideoFragment = new AuthorVideoFragment();
        mAuthorVideoFragment.authId = authId;
        return mAuthorVideoFragment;
    }

    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_author_video_fragment, container, false);
        return view;
    }

    @Override
    protected void initView() {
        srl_auth_video = CommonUtil.getCommonUtil().bindView(view,R.id.srl_auth_video);
        rv_auth_video = CommonUtil.getCommonUtil().bindView(view,R.id.rv_auth_video);
    }

    @Override
    protected void initSet() {

    }

    @Override
    protected void initAdapter() {
        rv_auth_video.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_auth_video.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL,
                CommonUtil.getCommonUtil().convertDIP(1),
                Color.parseColor("#f2f2f2")));
        mVideoAdapter = new VideoAdapter(mInfromationContentList, getActivity());
        rv_auth_video.setAdapter(mVideoAdapter);
        mVideoAdapter.setEmptyView(mNoDataView);
    }
    private int page=0;
    @Override
    protected void initListner() {
        //刷新
        srl_auth_video.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                getNetData(page,false,true);
            }
        });
        //加载
        mVideoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getNetData(page,true,false);
            }
        },rv_auth_video);
        //Item
        mVideoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent();
                mIntent.setClass(getActivity(), VideoInfoActivity.class);
                mIntent.putExtra("VIDEOS_ID",mInfromationContentList.get(position).getId());
                mIntent.putExtra("VIDEOS_TITLE",mInfromationContentList.get(position).getTitle());
                String content = mInfromationContentList.get(position).getContent();
                mIntent.putExtra("VIDEOS_CONENT",content.length()>50?content.substring(0,50):content);
                mIntent.putExtra("VIDEOS_IMG",mInfromationContentList.get(position).getImage());
                startActivity(mIntent);
            }
        });
        //滑动监听
        rv_auth_video.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) layoutManager;
                    int mFirstCompletelyVisibleItemPosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int mLastCompletelyVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    Map<Integer, StandardGSYVideoPlayer> standardGSYVideoPlayers = mVideoAdapter.getStandardGSYVideoPlayers();
                    if (dy > 0) {
                        //向上滑动
                        StandardGSYVideoPlayer standardGSYVideoPlayer = standardGSYVideoPlayers.get(mFirstCompletelyVisibleItemPosition - 1);
                        if (standardGSYVideoPlayer != null) {
                            //正在播放
                            if (standardGSYVideoPlayer.getCurrentState() != 0) {
                                standardGSYVideoPlayer.release();
                            }
                        }
                    } else {
                        //向下滑动
                        StandardGSYVideoPlayer standardGSYVideoPlayer = standardGSYVideoPlayers.get(mLastCompletelyVisibleItemPosition + 1);
                        if (standardGSYVideoPlayer != null) {
                            //正在播放
                            if (standardGSYVideoPlayer.getCurrentState() != 0) {
                                standardGSYVideoPlayer.release();
                            }
                        }
                    }

                }
            }
        });
    }

    @Override
    protected void initData() {
        getNetData(0,false,false);
    }


    private void getNetData(int page, final boolean isLoad, final boolean isRefresh) {
        OkGo.<String>post(ConfigUtil.AUTH_LIST)
                .params("author_id", authId)
                .params("type", "2")
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
                        if (srl_auth_video.isRefreshing()) {
                            srl_auth_video.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            CoreContentEntity mCoreContentEntity = new CoreContentEntity(new JSONObject(response.body()));
                            if (mCoreContentEntity.getStatus() == 1) {
                                if (isRefresh) {
                                    mInfromationContentList.clear();
                                }
                                List<CoreContentEntity.InfromationContentList> infromationContentLists = mCoreContentEntity.getmInfromationContentLists();
                                mInfromationContentList.addAll(infromationContentLists);
                                mVideoAdapter.notifyDataSetChanged();
                                if((mCoreContentEntity.getPage()+1)<mCoreContentEntity.getPage_total()){
                                    mVideoAdapter.loadMoreComplete();
                                }else {
                                    mVideoAdapter.loadMoreEnd();
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
