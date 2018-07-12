package com.mzth.createcause.view.fragment.publish;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 10:37
 * @描述 图片实体
 */
public class Image {
    public String path;
    public String name;
    public long time;

    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
