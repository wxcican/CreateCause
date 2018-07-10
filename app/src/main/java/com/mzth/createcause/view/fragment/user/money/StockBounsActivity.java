package com.mzth.createcause.view.fragment.user.money;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.View;import android.widget.ImageView;import android.widget.TextView;import com.mzth.createcause.R;import com.mzth.createcause.adapter.user.StockBoundAdapter;import com.mzth.createcause.base.BaseActivity;import com.mzth.createcause.entity.StockBoundEntity;import com.mzth.createcause.util.CommonUtil;import com.mzth.createcause.util.DividerItemDecoration;import java.util.ArrayList;import java.util.List;/** * 股份分红 */public class StockBounsActivity extends BaseActivity {    private TextView base_title;    private ImageView base_iv;    private TextView base_tv;    private RecyclerView rv_user_stock_bouns;    @Override    protected void setActivity() {        setContentView(R.layout.activity_stock_bouns);    }    @Override    protected void initView() {        base_title = CommonUtil.getCommonUtil().bindView(this, R.id.base_title);        base_iv = CommonUtil.getCommonUtil().bindView(this, R.id.base_iv);        base_tv = CommonUtil.getCommonUtil().bindView(this, R.id.base_tv);        rv_user_stock_bouns = CommonUtil.getCommonUtil().bindView(this, R.id.rv_user_stock_bouns);    }    @Override    protected void initSet() {        base_title.setText("股份分红");        base_iv.setVisibility(View.GONE);        base_tv.setVisibility(View.GONE);    }    @Override    protected void initAdapter() {    }    @Override    protected void initData() {        List<StockBoundEntity> mStockBoundEntitys = new ArrayList<>();        for (int i = 0; i < 5; i++) {            mStockBoundEntitys.add(new StockBoundEntity());        }        StockBoundAdapter mStockBoundAdapter = new StockBoundAdapter(mStockBoundEntitys);        rv_user_stock_bouns.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));        rv_user_stock_bouns.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL,                CommonUtil.getCommonUtil().convertDIP(5),                getResources().getColor(R.color.colorBackround)));        rv_user_stock_bouns.setAdapter(mStockBoundAdapter);    }    @Override    protected void initListener() {    }}