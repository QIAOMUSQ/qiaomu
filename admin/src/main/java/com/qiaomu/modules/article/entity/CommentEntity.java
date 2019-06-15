package com.qiaomu.modules.article.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Created by wenglei on 2019/5/26.
 */
@TableName("pluto_article_comment")
public class CommentEntity {
    private String commentId;
    private String content;
    private String articleId;
    private String userId;
    private String createdAt;
    private String updatedAt;
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
