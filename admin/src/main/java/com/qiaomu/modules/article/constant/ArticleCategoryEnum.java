package com.qiaomu.modules.article.constant;

/**
 * Created by wenglei on 2019/5/25.
 */
public enum ArticleCategoryEnum {
    Invitation("1"),Notice("2");
    private String value;

    ArticleCategoryEnum(String value) {

        this.value = value;
    }
    public String getCategoryValue(){
        return this.value;
    }
    public void setCategoryValue(String value){
        this.value = value;
    }
}
