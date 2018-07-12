package com.mzth.createcause.view.fragment.publish;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 11:04
 * @描述  图片选择类型（单选，多选）
 */
public enum SelectModel {
    SINGLE(PhotoPickerActivity.MODE_SINGLE),
    MULTI(PhotoPickerActivity.MODE_MULTI);

    private int model;

    SelectModel(int model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return String.valueOf(this.model);
    }
}
