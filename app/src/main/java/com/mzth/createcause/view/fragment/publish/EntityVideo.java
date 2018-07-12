package com.mzth.createcause.view.fragment.publish;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 17:03
 * @描述 视频实体类
 */
public class EntityVideo {
    private String thumbPath;//缩略图地址
    private String path;//视频路径
    private String duration;//视频时长

    public EntityVideo(String thumbPath, String path, String duration) {
        this.thumbPath = thumbPath;
        this.path = path;
        this.duration = duration;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
