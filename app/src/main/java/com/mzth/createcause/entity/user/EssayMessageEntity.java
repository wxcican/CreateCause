package com.mzth.createcause.entity.user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 文章管理 on 2018/2/7 0007.
 */

public class EssayMessageEntity {
    private String err_mag;
    private int status;
    private int page;
    private int page_total;
    private List<EssayMessageList> mEssayMessageLists = new ArrayList<>();

    public EssayMessageEntity(JSONObject jsonObject) {
        if (jsonObject != null) {
            status = jsonObject.optInt("status");
            if (status == 1) {
                JSONObject result = jsonObject.optJSONObject("result");
                if (result != null) {
                    page = result.optInt("page");
                    page_total = result.optInt("page_total");
                    JSONArray list = result.optJSONArray("list");
                    if (list != null) {
                        for (int i = 0; i < list.length(); i++) {
                            mEssayMessageLists.add(new EssayMessageList(list.optJSONObject(i)));
                        }
                    }
                }
            } else {
                String err_mag = jsonObject.optString("err_mag");
            }

        }
    }

    public String getErr_mag() {
        return err_mag;
    }

    public int getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public int getPage_total() {
        return page_total;
    }

    public List<EssayMessageList> getmEssayMessageLists() {
        return mEssayMessageLists;
    }

    public class EssayMessageList {

        private String id;
        private String title;
        private String cat_name;
        private String add_time;
        private String comment;
        private String image;
        private boolean isSelect=false;

        public EssayMessageList(JSONObject jsonObject) {
            if (jsonObject != null) {
                id = jsonObject.optString("id");
                title = jsonObject.optString("title");
                cat_name = jsonObject.optString("cat_name");
                add_time = jsonObject.optString("add_time");
                comment = jsonObject.optString("comment");
                image = jsonObject.optString("image");
            }
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCat_name() {
            return cat_name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public String getComment() {
            return comment;
        }

        public String getImage() {
            return image;
        }
    }
}
