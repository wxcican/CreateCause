package com.mzth.createcause.view.fragment.user.news;import android.app.Dialog;import android.content.Context;import android.graphics.Bitmap;import android.graphics.Canvas;import android.graphics.Matrix;import android.graphics.drawable.BitmapDrawable;import android.graphics.drawable.Drawable;import android.text.Html;import android.text.method.ScrollingMovementMethod;import android.util.DisplayMetrics;import android.util.Log;import android.view.View;import android.widget.ImageView;import android.widget.TextView;import com.bumptech.glide.Glide;import com.bumptech.glide.request.target.SimpleTarget;import com.bumptech.glide.request.transition.Transition;import com.lzy.okgo.OkGo;import com.lzy.okgo.callback.StringCallback;import com.lzy.okgo.model.Response;import com.lzy.okgo.request.base.Request;import com.mzth.createcause.R;import com.mzth.createcause.base.BaseActivity;import com.mzth.createcause.util.CommonUtil;import com.mzth.createcause.util.ConfigUtil;import org.json.JSONException;import org.json.JSONObject;public class NewsCoreInfoActivity extends BaseActivity {    private TextView base_title;    private ImageView base_iv;    private TextView base_tv;    private TextView tv_newscore_info_1;    private TextView tv_newscore_info_2;    private Dialog dialog;    @Override    protected void setActivity() {        setContentView(R.layout.activity_news_core_info);    }    @Override    protected void initView() {        base_title = CommonUtil.getCommonUtil().bindView(this, R.id.base_title);        base_iv = CommonUtil.getCommonUtil().bindView(this, R.id.base_iv);        base_tv = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv);        tv_newscore_info_1 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_newscore_info_1);        tv_newscore_info_2 = CommonUtil.getCommonUtil().bindView(this, R.id.tv_newscore_info_2);    }    @Override    protected void initSet() {        base_title.setText("消息详情");        base_iv.setVisibility(View.GONE);        base_tv.setVisibility(View.GONE);        tv_newscore_info_2.setMovementMethod(ScrollingMovementMethod.getInstance());    }    @Override    protected void initAdapter() {    }    @Override    protected void initListener() {    }    @Override    protected void initData() {        String newscore_id = getIntent().getStringExtra("NEWSCORE_ID");        Log.i(TAG, "userId: "+userId);        Log.i(TAG, "userKey: "+userKey);        OkGo.<String>post(ConfigUtil.USER_NEWS_NEWS_ONE)                .params("user_id",userId)                .params("auth_key",userKey)                .params("notice_id",newscore_id)                .execute(new StringCallback() {                    @Override                    public void onStart(Request<String, ? extends Request> request) {                        super.onStart(request);                        dialog = CommonUtil.getCommonUtil().ejectLaoding(mContext, "正在加载");                    }                    @Override                    public void onError(Response<String> response) {                        super.onError(response);                        CommonUtil.getCommonUtil().toast(ConfigUtil.HINT_OVERTIME);                    }                    @Override                    public void onFinish() {                        super.onFinish();                        if(dialog!=null&&dialog.isShowing()){                            dialog.dismiss();                            dialog=null;                        }                    }                    @Override                    public void onSuccess(Response<String> response) {                        try {                            JSONObject mJsonObject=new JSONObject(response.body());                            int status = mJsonObject.optInt("status");                            if(status==1){                                JSONObject result = mJsonObject.optJSONObject("result");                                if(result!=null){                                    String title = result.optString("title");                                    String content = result.optString("content");                                    String look = result.optString("look");                                    if(look.equals("0")){                                        setResult(101);                                    }                                    tv_newscore_info_1.setText(title);                                    tv_newscore_info_2.setText(Html.fromHtml(content, new URLImageParser(mContext, tv_newscore_info_2), null));                                }                            }else {                                CommonUtil.getCommonUtil().toast(mJsonObject.optString("err_msg"));                            }                        } catch (JSONException e) {                            e.printStackTrace();                        }                    }                });    }    public class URLImageParser implements Html.ImageGetter {        private Context context;        private TextView tvContent;        private int actX;//实际的宽  放大缩小基于textview的宽度        private int actY;        public URLImageParser(Context context, TextView tvContent) {            this.context = context;            this.tvContent = tvContent;            //获取全屏大小            DisplayMetrics metrics = context.getResources().getDisplayMetrics();            //我的textview有左右留边  margin            actX = metrics.widthPixels - CommonUtil.getCommonUtil().convertDIP(20);            actY = metrics.heightPixels;        }        @Override        public Drawable getDrawable(String source) {            final URLDrawable urlDrawable = new URLDrawable();            Glide.with(context)                    .asBitmap()                    .load(source)                    .into(new SimpleTarget<Bitmap>() {                        @Override                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {                            int x = resource.getWidth();                            int y = resource.getHeight();                            if (x > actX || y > actY) {                                //进行等比例缩放程序                                Matrix matrix = new Matrix();                                matrix.postScale((float) (actX * 1.00 / x), (float) (actX * 1.00 / x));                                //长和宽放大缩小的比例                                resource = Bitmap.createBitmap(resource, 0, 0, x, y, matrix, true);                            }                            urlDrawable.bitmap = resource;                            urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());                            tvContent.invalidate();                            tvContent.setText(tvContent.getText()); // 解决图文重叠                        }                    });            return urlDrawable;        }    }    public class URLDrawable extends BitmapDrawable {        protected Bitmap bitmap;        @Override        public void draw(Canvas canvas) {            if (bitmap != null) {                canvas.drawBitmap(bitmap, 0, 0, getPaint());            }        }    }}