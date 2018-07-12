package com.mzth.createcause.view.fragment.publish;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzth.createcause.R;
import com.mzth.createcause.base.BaseActivity;
import com.mzth.createcause.util.CommonUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONArray;

import java.util.ArrayList;

public class PublishActivity extends BaseActivity {
    private TextView  base_title;
    private ImageView base_iv;
    private TextView  base_tv, base_tv_publish;
    private static final String PUBLISH_MODE = "publish_mode";
    public static final  int    MODE_TEXT    = 1;
    public static final  int    MODE_VIDEO   = 2;
    private int mode;
    private static final int REQUEST_CAMERA_CODE  = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private static final int REQUEST_VIDEO_CODE = 30;
    private EditText et_title, et_content;
    private GridView mGridView;
    private View     video_rl, cover_cl;//上传视频布局，视频封面选择
    private StandardGSYVideoPlayer video_gsy;//视频播放控件
    private PublishGridAdapter mGridAdapter;
    private ArrayList<String>  imagePaths;

    public static void startPublishActivity(Context context, int mode) {
        Intent intent = new Intent(context, PublishActivity.class);
        intent.putExtra(PUBLISH_MODE, mode);
        context.startActivity(intent);
    }


    @Override
    protected void setActivity() {
        setContentView(R.layout.activity_publish);
        imagePaths = new ArrayList<>();
    }

    @Override
    protected void initView() {
        base_title = CommonUtil.getCommonUtil().bindView(this, R.id.base_title);
        base_iv = CommonUtil.getCommonUtil().bindView(this, R.id.base_iv);
        base_tv = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv);
        base_tv_publish = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv_publish);
        et_title = CommonUtil.getCommonUtil().bindView(this, R.id.et_title_activity_publish);
        et_content = CommonUtil.getCommonUtil().bindView(this, R.id.et_content_activity_publish);
        mGridView = CommonUtil.getCommonUtil().bindView(this, R.id.gv_activity_publish);
        video_rl = CommonUtil.getCommonUtil().bindView(this, R.id.video_rl_activity_publish);
        cover_cl = CommonUtil.getCommonUtil().bindView(this, R.id.cover_iv_activity_publish);
        video_gsy = CommonUtil.getCommonUtil().bindView(this, R.id.gsy_activity_publish);

        mode = getIntent().getIntExtra(PUBLISH_MODE, MODE_TEXT);
        if (mode == MODE_TEXT) {
            mGridView.setVisibility(View.VISIBLE);
            video_rl.setVisibility(View.GONE);
            cover_cl.setVisibility(View.GONE);
            video_gsy.setVisibility(View.GONE);
        } else if (mode == MODE_VIDEO) {
            mGridView.setVisibility(View.GONE);
            video_rl.setVisibility(View.VISIBLE);
            cover_cl.setVisibility(View.VISIBLE);
            video_gsy.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initSet() {
        base_iv.setVisibility(View.GONE);
        base_tv.setVisibility(View.GONE);
        base_title.setText("发布图文");
    }

    @Override
    protected void initAdapter() {
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        mGridView.setNumColumns(cols);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断点击的是否是添加图片
                String imgs = (String) parent.getItemAtPosition(position);
                if ("paizhao".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(PublishActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    CommonUtil.getCommonUtil().toast("点击了：" + position);
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(PublishActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("paizhao");
        mGridAdapter = new PublishGridAdapter(PublishActivity.this, imagePaths);
        mGridView.setAdapter(mGridAdapter);
    }

    @Override
    protected void initListener() {
        base_tv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.getCommonUtil().toast("执行发布请求");
            }
        });
        video_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,
                        REQUEST_VIDEO_CODE);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "数量：" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdpater(ListExtra);
                    break;
                    //视频
                case REQUEST_VIDEO_CODE:
                    Uri uri = data.getData();
                    Log.e("aaa","拿到视频路径：" + uri.toString());
                    CommonUtil.getCommonUtil().toast("拿到视频路径");
                    video_rl.setVisibility(View.GONE);
                    video_gsy.setVisibility(View.VISIBLE);

                    // TODO: 2018/7/12 完成视频点击播放 
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("paizhao")) {
            paths.remove("paizhao");
        }
        paths.add("paizhao");
        imagePaths.addAll(paths);
        mGridAdapter = new PublishGridAdapter(PublishActivity.this, imagePaths);
        mGridView.setAdapter(mGridAdapter);
        try {
            JSONArray obj = new JSONArray(imagePaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
