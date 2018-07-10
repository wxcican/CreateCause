package com.mzth.createcause.entity.user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 我的资产详情 on 2018/2/2 0002.
 */

public class InComenInfoEntity {

    private  int status;
    private  String err_msg;
    private  String total;
    private List<InComeInfoList> mInComeInfoLists=new ArrayList<>();
    public InComenInfoEntity(JSONObject jsonObject) {
        if(jsonObject!=null){
            status = jsonObject.optInt("status");
            if(status ==1){
                JSONObject result = jsonObject.optJSONObject("result");
                if(result!=null){
                    total = result.optString("total");
                    JSONArray class_list = result.optJSONArray("class_list");
                    if(class_list!=null){
                        for (int i = 0; i < class_list.length(); i++) {
                            mInComeInfoLists.add(new InComeInfoList(class_list.optJSONObject(i)));
                        }
                    }
                }
            }else {
                err_msg = jsonObject.optString("err_msg");
            }
        }
    }

    public int getStatus() {
        return status;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public String getTotal() {
        return total;
    }

    public List<InComeInfoList> getmInComeInfoLists() {
        return mInComeInfoLists;
    }

    public class InComeInfoList{

        private  String aClass;
        private  String sum;
        private  String class_name;

        public InComeInfoList(JSONObject jsonObject) {
            if(jsonObject!=null){
                aClass = jsonObject.optString("class");
                sum = jsonObject.optString("sum");
                class_name = jsonObject.optString("class_name");
            }
        }

        public String getaClass() {
            return aClass;
        }

        public String getSum() {
            return sum;
        }

        public String getClass_name() {
            return class_name;
        }
    }
}
