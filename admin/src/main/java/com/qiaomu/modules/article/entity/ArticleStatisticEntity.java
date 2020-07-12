package com.qiaomu.modules.article.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2020-07-05 17:29
 */
@Data
@TableName("user_article_statistic")
public class ArticleStatisticEntity {
    private String userId;

    private Date date;

    private String userName;

    private Integer number;

    private Long communityId;

    public ArticleStatisticEntity(String userId, Date date, String userName, Integer number, Long communityId) {
        this.userId = userId;
        this.date = date;
        this.userName = userName;
        this.number = number;
        this.communityId = communityId;
    }
}
