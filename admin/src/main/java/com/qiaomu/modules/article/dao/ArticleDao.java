package com.qiaomu.modules.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiaomu.modules.article.entity.ArticleEntity;
import com.qiaomu.modules.article.entity.ArticlePraiseEntity;
import com.qiaomu.modules.article.entity.CommentEntity;
import com.qiaomu.modules.article.model.ArticleSelectModel;
import com.qiaomu.modules.infopublish.entity.InvitationEntity;

import java.util.List;

/**
 * Created by wenglei on 2019/5/25.
 */
public interface ArticleDao extends BaseMapper<InvitationEntity> {
    List<ArticleEntity> query(ArticleSelectModel articleSelectModel);
    String queryPraise(ArticlePraiseEntity articlePraiseEntity);
    void deleteArticleByArticleId(ArticleEntity articleEntity);
    void updateArticle(ArticleEntity articleEntity);
    void updateArticlePraiseNum(ArticleEntity articleEntity);
    void insertArticlePraise(ArticlePraiseEntity articlePraiseEntity);
    void updateArticlePraise(ArticlePraiseEntity articlePraiseEntity);
    void insertArticle(ArticleEntity articleEntity);
    void deleteCommentById(CommentEntity commentEntity);
    void deleteCommentByArticleId(CommentEntity commentEntity);
    void insertArticleComment(CommentEntity commentEntity);
    List<CommentEntity> queryCommentByArticleId(String articleId);
    List<CommentEntity> queryCommentByUserId(String userId);

}
