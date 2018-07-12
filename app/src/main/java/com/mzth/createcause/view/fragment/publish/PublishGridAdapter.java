package com.mzth.createcause.view.fragment.publish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.mzth.createcause.R;

import java.util.List;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 9:35
 * @描述 发布图文/视频页图片选择的适配器
 */
public class PublishGridAdapter extends BaseAdapter {
    private List<String>   listUrls;
    private LayoutInflater inflater;
    private Context        mContext;

    public PublishGridAdapter(Context mContext, List<String> listUrls) {
        //当选择图片为9张时，不显示添加按钮
        if (listUrls.size() == 10) {
            listUrls.remove(listUrls.size() - 1);
        }
        this.listUrls = listUrls;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
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
            Glide.with(mContext)
                    .load(path)
                    .apply(RequestOptions.centerCropTransform()
                            .placeholder(R.drawable.icon_r_def)
                            .error(R.drawable.icon_r_def))
                    .into(holder.mImageView);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView mImageView;
    }
}
