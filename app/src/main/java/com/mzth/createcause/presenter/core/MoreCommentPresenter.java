package com.mzth.createcause.presenter.core;import com.lzy.okgo.callback.StringCallback;import com.lzy.okgo.model.Response;import com.lzy.okgo.request.base.Request;import com.mzth.createcause.base.BasePresenter;import com.mzth.createcause.entity.core.AnalyCommentInfoEntity;import com.mzth.createcause.entity.core.NewsInfoEntity;import com.mzth.createcause.model.core.InfromationVideoDataModel;import com.mzth.createcause.view.fragment.news.IMoreCommentActivity;import org.json.JSONException;import org.json.JSONObject;import java.util.List;/** * Created by Administrator on 2018/1/29 0029. */public class MoreCommentPresenter extends BasePresenter {    private IMoreCommentActivity mIMoreCommentActivity;    /**     * 初始化更多评论UI     *     * @param iMoreCommentActivity     */    public void initIMoreCommentActivity(IMoreCommentActivity iMoreCommentActivity) {        this.mIMoreCommentActivity = iMoreCommentActivity;    }    public void initMoreCommentData(String newsId, int page, final List<NewsInfoEntity> mNewsInfoEntitys, final boolean isRefresh) {        InfromationVideoDataModel.getICoreInfoData().queryCoreInfoCommentData(newsId, page + "", user_Id, new StringCallback() {            @Override            public void onStart(Request<String, ? extends Request> request) {                super.onStart(request);                if (isRefresh) {                    if (mIMoreCommentActivity != null) {                        mIMoreCommentActivity.onRequestStart();                    }                }            }            @Override            public void onError(Response<String> response) {                super.onError(response);                if (mIMoreCommentActivity != null) {                    mIMoreCommentActivity.onRequestError();                }            }            @Override            public void onFinish() {                super.onFinish();                if (isRefresh) {                    if (mIMoreCommentActivity != null) {                        mIMoreCommentActivity.onRequestFinish();                    }                }            }            @Override            public void onSuccess(Response<String> response) {                try {                    AnalyCommentInfoEntity mAnalyNewsInfoCommentEntity = new AnalyCommentInfoEntity(new JSONObject(response.body()), mNewsInfoEntitys);                    if (mAnalyNewsInfoCommentEntity.getStatus() == 1) {                        if (mIMoreCommentActivity != null) {                            mIMoreCommentActivity.commentInfoData(mAnalyNewsInfoCommentEntity);                        }                    } else {                        if (mIMoreCommentActivity != null) {                            mIMoreCommentActivity.onToast(mAnalyNewsInfoCommentEntity.getErr_msg());                        }                    }                } catch (JSONException e) {                    e.printStackTrace();                }            }        });    }}