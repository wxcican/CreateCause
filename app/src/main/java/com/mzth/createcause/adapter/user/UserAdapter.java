package com.mzth.createcause.adapter.user;import com.chad.library.adapter.base.BaseQuickAdapter;import com.chad.library.adapter.base.BaseViewHolder;import com.mzth.createcause.R;import com.mzth.createcause.entity.UserContent;import java.util.List;/** * Created by Administrator on 2017/10/10 0010. */public class UserAdapter extends BaseQuickAdapter<UserContent,BaseViewHolder> {    public UserAdapter(List<UserContent> data) {        super(R.layout.item_user, data);    }    @Override    protected void convert(BaseViewHolder helper, UserContent item) {        helper.setText(R.id.tv_item_user,item.getHint());        helper.setImageResource(R.id.iv_item_user,item.getImg());    }}