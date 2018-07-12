package com.mzth.createcause.view.fragment.publish;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 11:02
 * @描述  跳转到相册页面的意图，带值
 */
public class PhotoPickerIntent extends Intent {
    public PhotoPickerIntent(Context packageContext) {
        super(packageContext, PhotoPickerActivity.class);
    }

    //是否打开相机
    public void setShowCarema(boolean bool){
        this.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, bool);
    }

    //最大图片选择数量
    public void setMaxTotal(int total){
        this.putExtra(PhotoPickerActivity.EXTRA_SELECT_COUNT, total);
    }

    /**
     * 选择
     * @param model
     */
    public void setSelectModel(SelectModel model){
        this.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, Integer.parseInt(model.toString()));
    }

    /**
     * 已选择的照片地址
     * @param imagePathis
     */
    public void setSelectedPaths(ArrayList<String> imagePathis){
        this.putStringArrayListExtra(PhotoPickerActivity.EXTRA_DEFAULT_SELECTED_LIST, imagePathis);
    }

    /**
     * 显示相册图片的属性
     * @param config
     */
    public void setImageConfig(ImageConfig config){
        this.putExtra(PhotoPickerActivity.EXTRA_IMAGE_CONFIG, config);
    }
}
