package com.mzth.createcause.view.fragment.user.eaaaymessage;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mzth.createcause.base.BasePresenter;
import com.mzth.createcause.util.CommonUtil;
import com.mzth.createcause.util.ConfigUtil;
import com.mzth.createcause.util.RetentionDataUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public class ReleaseUtil {
    private static ReleaseUtil mReleaseUtil;
    private Dialog dialog;
    //封面图选择回调
    private OnCoverSelectListener mOnCoverSelectListener;
    private OnEssaySelectListener mOnEssaySelectListener;

    public static ReleaseUtil getReleaseUtil() {
        if (mReleaseUtil == null) {
            mReleaseUtil = new ReleaseUtil();
        }
        return mReleaseUtil;
    }

    private ReleaseUtil() {
    }

    /**
     * 上传图片
     *
     * @param activity
     * @param userId
     * @param userAuthID
     * @param imgPage
     */
    public void actionRequestImg(final Activity activity, String userId, String userAuthID, String imgPage) {
        OkGo.<String>post(ConfigUtil.REQUEST_UTIL)
                .params("user_id", userId)
                .params("auth_key", userAuthID)
                .params("add_http", true)
                .params("file", new File(imgPage))
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialog = CommonUtil.getCommonUtil().ejectLaoding(activity, "正在上传");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtil.getCommonUtil().toast(ConfigUtil.HINT_OVERTIME);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            dialog = null;
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject mJSONObject = new JSONObject(response.body());
                            int status = mJSONObject.optInt("status");
                            if (status == 1) {
                                if (mOnCoverSelectListener != null) {
                                    mOnCoverSelectListener.onCoverSelect(mJSONObject.optString("result"));
                                }
                            } else {
                                CommonUtil.getCommonUtil().toast(mJSONObject.optString("err_msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 发布文章
     *
     * @param activity  当前活动
     * @param essayID   文章ID(编辑文章时候使用)
     * @param userID    用户ID
     * @param typeID    发布类型ID
     * @param title     标题
     * @param keyWord   关键字
     * @param desc      简介
     * @param content   内容
     * @param type      文章或者视频
     * @param imgPath   封面图片路径
     * @param videoPath 视频路径(上传视频的时候用)
     */
    public void actionRequestEssay(final Activity activity, String essayID, String userID, String authKey, String typeID, String title, String keyWord, String desc, String content, String type, String imgPath, String videoPath) {
        OkGo.<String>post(ConfigUtil.REQUEST_EDIT)
                .params("id", essayID)
                .params("user_id", userID)
                .params("uid", userID)
                .params("auth_key", authKey)
                .params("cat_id", typeID)
                .params("share_title", title)
                .params("keywords", keyWord)
                .params("desc", desc)
                .params("content", content)
                .params("thumb", imgPath)
                .params("type", type)
                .params("vid", videoPath)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialog = CommonUtil.getCommonUtil().ejectLaoding(activity, "正在发布");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtil.getCommonUtil().toast(ConfigUtil.HINT_OVERTIME);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            dialog = null;
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject mJSONObject = new JSONObject(response.body());
                            int status = mJSONObject.optInt("status");
                            if (status == 1) {
                                RetentionDataUtil.getRetention().setString("RELEASE_CONTENT", "");
                                activity.setResult(403);
                                activity.finish();
                            } else {
                                CommonUtil.getCommonUtil().toast(mJSONObject.optString("err_msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 修改文章的状态
     *
     * @param activity
     * @param userId
     * @param userKey
     * @param essagId
     * @param type
     */
    public void actionEssagStatus(final Activity activity, String userId, String userKey, String essagId, String type) {
        OkGo.<String>post(ConfigUtil.REQUEST_ESSAT_STATUS)
                .params("user_id", userId)
                .params("auth_key", userKey)
                .params("id", essagId)
                .params("status", type)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        dialog = CommonUtil.getCommonUtil().ejectLaoding(activity, "正在提交");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        CommonUtil.getCommonUtil().toast(ConfigUtil.HINT_OVERTIME);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            dialog = null;
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject mJSONObject = new JSONObject(response.body());
                            if (mJSONObject.optInt("status") == 1) {
                                if (mOnEssaySelectListener != null) {
                                    mOnEssaySelectListener.onEssaySelect();
                                }
                            } else {
                                CommonUtil.getCommonUtil().toast(mJSONObject.optString("err_msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public interface OnCoverSelectListener {
        void onCoverSelect(String path);
    }

    public interface OnEssaySelectListener {
        void onEssaySelect();
    }

    public void setOnCoverSelectListener(OnCoverSelectListener onCoverSelectListener) {
        this.mOnCoverSelectListener = onCoverSelectListener;
    }

    public void setOnEssaySelectListener(OnEssaySelectListener onEssaySelectListener) {
        this.mOnEssaySelectListener = onEssaySelectListener;
    }
}
