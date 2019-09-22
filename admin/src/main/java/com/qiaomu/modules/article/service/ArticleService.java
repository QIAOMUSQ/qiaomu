package com.qiaomu.modules.article.service;

import com.qiaomu.modules.article.entity.ArticleEntity;
import com.qiaomu.modules.article.entity.ArticlePoint;
import com.qiaomu.modules.article.entity.CommentEntity;
import com.qiaomu.modules.article.model.ArticleModel;
import com.qiaomu.modules.article.model.ArticleSelectModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wenglei on 2019/5/25.
 */
public interface ArticleService {
    void add(ArticleEntity articleEntity);
    List<ArticleModel> query(ArticleSelectModel articleSelectModel);
    Map<String,Object> addPraiseNum(String userId, String articleId);
    void addComment(CommentEntity commentEntity)throws Exception;
    List<CommentEntity> queryComment(CommentEntity commentEntity);
    void  deleteCommentByCommentId(CommentEntity commentEntity);
    void  deleteCommentByArticleId(CommentEntity commentEntity);
    void deleteArticleByArticleId(String articleId);
    void updateArticle(ArticleEntity articleEntity);
    List<ArticlePoint> queryArticlePoints(String communityId);
    ArticlePoint queryArticlePoint(String userId,String communityId);
    void insertArticlePoint(ArticlePoint articlePoint);
    void updateArticlePraiseNum(ArticleEntity articleEntity);
    List<ArticleModel> queryOnly(ArticleSelectModel articleSelectModel);
}
