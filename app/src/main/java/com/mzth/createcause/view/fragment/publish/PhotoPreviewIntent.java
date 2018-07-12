package com.mzth.createcause.view.fragment.publish;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 11:06
 * @描述  预览照片
 */
public class PhotoPreviewIntent extends Intent {
    public PhotoPreviewIntent(Context packageContext) {
        super(packageContext, PhotoPreviewActivity.class);
    }

    /**
     * 照片地址
     * @param paths
     */
    public void setPhotoPaths(ArrayList<String> paths){
        this.putStringArrayListExtra(PhotoPreviewActivity.EXTRA_PHOTOS, paths);
    }

    /**
     * 当前照片的下标
     * @param currentItem
     */
    public void setCurrentItem(int currentItem){
        this.putExtra(PhotoPreviewActivity.EXTRA_CURRENT_ITEM, currentItem);
    }
}
