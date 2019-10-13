package com.qiaomu.modules.article.service;

import com.alibaba.fastjson.JSONObject;
import com.qiaomu.common.utils.AESUtil;
import com.qiaomu.common.utils.DateUtils;
import com.qiaomu.modules.article.dao.ArticleDao;
import com.qiaomu.modules.article.entity.ArticleEntity;
import com.qiaomu.modules.article.entity.ArticlePoint;
import com.qiaomu.modules.article.entity.ArticlePraiseEntity;
import com.qiaomu.modules.article.entity.CommentEntity;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.article.model.ArticleModel;
import com.qiaomu.modules.article.model.ArticleSelectModel;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.entity.UserExtend;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.UserExtendService;
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

    @Autowired
    private UserExtendService userExtendService;

    @Override
    public void add(ArticleEntity articleEntity) {
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setTitle(articleEntity.getTitle());
        List<ArticleModel> articles =  articleDao.query(articleSelectModel);
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
    public List<ArticleModel> query(ArticleSelectModel articleSelectModel) {
        SysUserEntity sysUserEntity = sysUserService.queryById(Long.valueOf(articleSelectModel.getUserId()));

        if(sysUserEntity==null||sysUserEntity.getRealName()==null||sysUserEntity.getRealName().isEmpty()){
            throw new CommentException("请先实名认证！");
        }
        UserExtend userExtendQ = new UserExtend();
        userExtendQ.setUserId(Long.valueOf(articleSelectModel.getUserId()));
        userExtendQ.setCommunityId(Long.valueOf(articleSelectModel.getCommunityId()));
        UserExtend userExtend = userExtendService.queryUserExtend(userExtendQ);
        if(userExtend==null||userExtend.getCommunityId()==null){
            throw new CommentException("请认证该小区！");
        }
        List<ArticleModel> articleModels =null;
        if(articleSelectModel.getQueryType()==null||"1".equals(articleSelectModel.getQueryType())||"3".equals(articleSelectModel.getQueryType())){
            articleModels = articleDao.query(articleSelectModel);
        }else if("2".equals(articleSelectModel.getQueryType())){
            articleModels = articleDao.queryArticleByCommentTime(articleSelectModel);
        }else if("4".equals(articleSelectModel.getQueryType())){
            articleModels = articleDao.queryArticleByCommentNum(articleSelectModel);
        }

        for(ArticleModel articleModel:articleModels){
            articleModel.setRealName(AESUtil.decrypt(articleModel.getRealName()));
        }
        return articleModels;
    }

    @Override
    public List<ArticleModel> queryOnly(ArticleSelectModel articleSelectModel) {
        List<ArticleModel> articleModels = articleDao.query(articleSelectModel);
        return articleModels;
    }

    @Override
    public synchronized Map<String,Object> addPraiseNum(String userId, String articleId ) {
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setArticleId(articleId);
        List<ArticleModel> articles =  articleDao.query(articleSelectModel);
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

            articleDao.updateArticlePraiseNum(JSONObject.parseObject(JSONObject.toJSONString(articles.get(0)),ArticleEntity.class));
            articlePraiseEntity.setIsPraise("1");
            if(null == isPraise){
                articleDao.insertArticlePraise(articlePraiseEntity);
            }else{
                articleDao.updateArticlePraise(articlePraiseEntity);
            }
        }else if("1".equals(isPraise)&&praiseNum>=1){
            praiseNum = praiseNum - 1;
            articles.get(0).setPraiseNum(praiseNum);
            articleDao.updateArticlePraiseNum(JSONObject.parseObject(JSONObject.toJSONString(articles.get(0)),ArticleEntity.class));
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

    @Override
    public List<ArticlePoint> queryArticlePoints(String communityId) {
        List<ArticlePoint> articlePoint = articleDao.queryArticlePoints(communityId);
        for(ArticlePoint ap:articlePoint) {
            if(ap.getRealName()!=null) {
                ap.setRealName(AESUtil.decrypt(ap.getRealName()));
            }
        }
        return articlePoint;
    }

    @Override
    public ArticlePoint queryArticlePoint(String userId,String communityId) {
        return articleDao.queryArticlePoint(userId,communityId);
    }

    @Override
    public void insertArticlePoint(ArticlePoint articlePoint) {
        articleDao.insertArticlePoint(articlePoint);
    }

    @Override
    public synchronized void updateArticleViewNum(ArticleEntity articleEntity) {
         articleDao.updateArticleViewNum(articleEntity);
    }

}
