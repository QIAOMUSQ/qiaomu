package com.qiaomu.modules.article.service;

import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.modules.article.dao.ArticleDao;
import com.qiaomu.modules.article.entity.ArticleEntity;
import com.qiaomu.modules.article.entity.ArticlePraiseEntity;
import com.qiaomu.modules.article.entity.CommentEntity;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.article.model.ArticleSelectModel;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenglei on 2019/5/25.
 */
@Service
public class ArticleServiceImp implements ArticleService{
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void add(ArticleEntity articleEntity) {
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setTitle(articleEntity.getTitle());
        List<ArticleEntity> articles =  articleDao.query(articleSelectModel);
        if(articles!=null&&articles.size()>0){
            throw new DuplicateKeyException("文章标题重名");
        }
        Date date = new Date();
        articleEntity.setCreatedAt(DateUtils.formats(date));
        articleEntity.setUpdatedAt(DateUtils.formats(date));
        try {
            String headUrl = sysUserService.queryUserImageUrl(articleEntity.getAuthorId());
            if(headUrl!=null){
                articleEntity.setHeadUrl(headUrl);
            }
            articleDao.insertArticle(articleEntity);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public List<ArticleEntity> query(ArticleSelectModel articleSelectModel) {
        SysUserEntity sysUserEntity = sysUserService.queryById(Long.valueOf(articleSelectModel.getUserId()));
        if(sysUserEntity==null||sysUserEntity.getRealName()==null||sysUserEntity.getRealName().isEmpty()){
            throw new CommentException("请先实名认证！");
        }
        return articleDao.query(articleSelectModel);
    }

    @Override
    public synchronized Map<String,Object> addPraiseNum(String userId, String articleId ) {
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setArticleId(articleId);
        List<ArticleEntity> articles =  articleDao.query(articleSelectModel);
        ArticlePraiseEntity articlePraiseEntity = new ArticlePraiseEntity();
        articlePraiseEntity.setUserId(userId);
        articlePraiseEntity.setArticleId(articleId);
        String isPraise = articleDao.queryPraise(articlePraiseEntity);
        Integer praiseNum = articles.get(0).getPraiseNum();
        if(praiseNum==null){
            praiseNum = 0;
        }
        if(null == isPraise||"0".equals(isPraise)) {
            praiseNum = praiseNum + 1;
            articles.get(0).setPraiseNum(praiseNum);
            articleDao.updateArticlePraiseNum(articles.get(0));
            articlePraiseEntity.setIsPraise("1");
            if(null == isPraise){
                articleDao.insertArticlePraise(articlePraiseEntity);
            }else{
                articleDao.updateArticlePraise(articlePraiseEntity);
            }
        }else if("1".equals(isPraise)&&praiseNum>=1){
            praiseNum = praiseNum - 1;
            articles.get(0).setPraiseNum(praiseNum);
            articleDao.updateArticlePraiseNum(articles.get(0));
            articlePraiseEntity.setIsPraise("0");
            articleDao.updateArticlePraise(articlePraiseEntity);
        }
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("praiseNum",praiseNum);
        returnMap.put("isPraise",articlePraiseEntity.getIsPraise());
        return returnMap;
    }

    @Override
    public void addComment (CommentEntity commentEntity) throws Exception{
        Map<String,String> returnMap = new HashMap<String,String>();
        List<CommentEntity> commentEntities = articleDao.queryCommentByUserId(commentEntity.getUserId());
        if(commentEntities.size()>100){
            throw new CommentException("最多只能评论100次！");
        }
        Date date = new Date();
        commentEntity.setCreatedAt(DateUtils.formats(date));
        commentEntity.setUpdatedAt(DateUtils.formats(date));
        try {
            articleDao.insertArticleComment(commentEntity);
        }catch(Exception e){
            e.printStackTrace();
            throw new CommentException("评论失败!");
        }
    }

    @Override
    public List<CommentEntity> queryComment(CommentEntity commentEntity) {
        List<CommentEntity> commentEntities = articleDao.queryCommentByArticleId(commentEntity.getArticleId());
        return commentEntities;
    }

    @Override
    public void  deleteCommentByCommentId(CommentEntity commentEntity) {
        articleDao.deleteCommentById(commentEntity);
    }
    @Override
    public void  deleteCommentByArticleId(CommentEntity commentEntity) {
        articleDao.deleteCommentByArticleId(commentEntity);
    }
    @Override
    public void deleteArticleByArticleId(String articleId){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setArticleId(articleId);
        articleDao.deleteArticleByArticleId(articleEntity);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setArticleId(articleEntity.getArticleId());
        deleteCommentByArticleId(commentEntity);
    }
    @Override
    public void updateArticle(ArticleEntity articleEntity){
        Date date = new Date();
        articleEntity.setUpdatedAt(DateUtils.formats(date));
        articleDao.updateArticle(articleEntity);
    }

}
