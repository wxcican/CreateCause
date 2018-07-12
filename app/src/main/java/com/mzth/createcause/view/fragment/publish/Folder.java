package com.mzth.createcause.view.fragment.publish;

import java.util.List;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 10:37
 * @描述 文件夹
 */
public class Folder {
    public String      name;
    public String      path;
    public Image       cover;
    public List<Image> images;

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
