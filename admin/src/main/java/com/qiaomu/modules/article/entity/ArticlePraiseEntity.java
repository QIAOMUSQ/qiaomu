package com.qiaomu.modules.article.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Created by wenglei on 2019/5/25.
 */
@TableName("pluto_article_praise")
public class ArticlePraiseEntity {

    private String isPraise;
    private String userId;
    public String getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
