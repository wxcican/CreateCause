package com.mzth.createcause.model.user;import com.mzth.createcause.entity.PaymentTypeEntity;import java.util.List;/** * Created by 购买股份支付方式接口 on 2017/10/18 0018. */public interface IPaymentType {    /**     * 获取支付类型     *     * @return     */    List<PaymentTypeEntity> getPaymentType();}