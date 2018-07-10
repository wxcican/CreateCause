package com.mzth.createcause.entity.user;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public class EssayEditEntity implements MultiItemEntity {
    public static final int ESSAY_VIDEO = 310;
    public static final int ESSAY_TEXT = 320;
    public static final int ESSAY_IMAGE = 330;
    private String videoPath="";
    private boolean videoDown = false;
    private boolean videoUp = false;
    private boolean videoDel = false;

    private String textContent;
    private boolean textoDown = false;
    private boolean textUp = false;
    private boolean textDel = false;

    private String imagePath="";
    private boolean imageDown = false;
    private boolean imageUp = false;
    private boolean imageDel = false;

    private int type;

    public EssayEditEntity(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }


    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public boolean isVideoDown() {
        return videoDown;
    }

    public void setVideoDown(boolean videoDown) {
        this.videoDown = videoDown;
    }

    public boolean isVideoUp() {
        return videoUp;
    }

    public void setVideoUp(boolean videoUp) {
        this.videoUp = videoUp;
    }

    public boolean isVideoDel() {
        return videoDel;
    }

    public void setVideoDel(boolean videoDel) {
        this.videoDel = videoDel;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public boolean isTextoDown() {
        return textoDown;
    }

    public void setTextoDown(boolean textoDown) {
        this.textoDown = textoDown;
    }

    public boolean isTextUp() {
        return textUp;
    }

    public void setTextUp(boolean textUp) {
        this.textUp = textUp;
    }

    public boolean isTextDel() {
        return textDel;
    }

    public void setTextDel(boolean textDel) {
        this.textDel = textDel;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isImageDown() {
        return imageDown;
    }

    public void setImageDown(boolean imageDown) {
        this.imageDown = imageDown;
    }

    public boolean isImageUp() {
        return imageUp;
    }

    public void setImageUp(boolean imageUp) {
        this.imageUp = imageUp;
    }

    public boolean isImageDel() {
        return imageDel;
    }

    public void setImageDel(boolean imageDel) {
        this.imageDel = imageDel;
    }
}
