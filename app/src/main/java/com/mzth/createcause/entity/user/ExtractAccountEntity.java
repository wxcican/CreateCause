package com.mzth.createcause.entity.user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 提现账号 on 2018/2/2 0002.
 */

public class ExtractAccountEntity {

    private String err_msg;
    private int status;
    private List<AccountList> mAccountList = new ArrayList<>();

    public ExtractAccountEntity(JSONObject jsonObject) {
        if (jsonObject != null) {
            status = jsonObject.optInt("status");
            if (status == 1) {
                JSONArray result = jsonObject.optJSONArray("result");
                if (result != null) {
                    for (int i = 0; i < result.length(); i++) {
                        mAccountList.add(new AccountList(result.optJSONObject(i)));
                    }
                }
            } else {
                err_msg = jsonObject.optString("err_msg");
            }
        }
    }

    public String getErr_msg() {
        return err_msg;
    }

    public int getStatus() {
        return status;
    }

    public List<AccountList> getmAccountList() {
        return mAccountList;
    }

    public class AccountList {

        private String id;
        private String zhifubao;
        private String type;
        private String bank_name;

        public AccountList(JSONObject jsonObject) {
            if (jsonObject != null) {
                id = jsonObject.optString("id");
                zhifubao = jsonObject.optString("zhifubao");
                type = jsonObject.optString("type");
                bank_name = jsonObject.optString("bank_name");
            }
        }

        public String getId() {
            return id;
        }

        public String getZhifubao() {
            return zhifubao;
        }

        public String getType() {
            return type;
        }

        public String getBank_name() {
            return bank_name;
        }
    }
}
