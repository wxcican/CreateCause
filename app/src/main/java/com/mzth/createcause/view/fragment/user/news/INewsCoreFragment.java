package com.mzth.createcause.view.fragment.user.news;import com.mzth.createcause.base.BaseIView;import com.mzth.createcause.entity.user.NewsCoreEntity;import java.util.List;/** * Created by Administrator on 2018/1/23 0023. */public interface INewsCoreFragment extends BaseIView{    /**     * 初始化数据     * @param newsCoreEntity     */    void initUserNewsNewsData(NewsCoreEntity newsCoreEntity);    /**     * 刷新数据     * @param newsCoreEntity     */    void refreshUserNewsNewsData(NewsCoreEntity newsCoreEntity);    /**     * 加载数据     * @param newsCoreEntity     */    void loadUserNewsNewsData(NewsCoreEntity newsCoreEntity);}