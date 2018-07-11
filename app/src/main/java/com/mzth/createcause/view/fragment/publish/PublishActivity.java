package com.mzth.createcause.view.fragment.publish;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.mzth.createcause.R;
import com.mzth.createcause.base.BaseActivity;
import com.mzth.createcause.util.CommonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

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
    private EditText et_title, et_content;
    private GridView          mGridView;
    private GridAdapter       mGridAdapter;
    private ArrayList<String> imagePaths;

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
                String imgs = (String) parent.getItemAtPosition(position);
                if ("paizhao".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(PublishActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(6); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    Toast.makeText(PublishActivity.this, "1" + position, Toast.LENGTH_SHORT).show();
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(PublishActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("paizhao");
        mGridAdapter = new GridAdapter(imagePaths);
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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "数量："+list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("paizhao")){
            paths.remove("paizhao");
        }
        paths.add("paizhao");
        imagePaths.addAll(paths);
        mGridAdapter  = new GridAdapter(imagePaths);
        mGridView.setAdapter(mGridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class GridAdapter extends BaseAdapter {
        private List<String>   listUrls;
        private LayoutInflater inflater;

        public GridAdapter(List<String> listUrls) {
            this.listUrls = listUrls;
            if (listUrls.size() == 7) {
                listUrls.remove(listUrls.size() - 1);
            }
            inflater = LayoutInflater.from(PublishActivity.this);
        }

        @Override
        public int getCount() {
            return listUrls.size();
        }

        @Override
        public Object getItem(int i) {
            return listUrls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_publish_pic_add, parent, false);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_item_publsh_pic_add);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String path = listUrls.get(position);
            if (path.equals("paizhao")) {
                holder.mImageView.setImageResource(R.drawable.add);
            } else {
                RequestOptions options = new RequestOptions();
                options.centerCrop()
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error);

                Glide.with(PublishActivity.this)
                        .load(path)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .apply(options)
                        .into(holder.mImageView);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }
}
