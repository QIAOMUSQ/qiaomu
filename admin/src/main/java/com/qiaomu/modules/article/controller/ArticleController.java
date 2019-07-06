package com.qiaomu.modules.article.controller;

import com.alibaba.fastjson.JSON;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.R;
import com.qiaomu.modules.article.entity.ArticleEntity;
import com.qiaomu.modules.article.entity.CommentEntity;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.article.model.ArticleSelectModel;
import com.qiaomu.modules.article.service.ArticleService;
import com.qiaomu.modules.infopublish_publish.service.InvitationService;
import com.qiaomu.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wonly
 * @description:发帖子管理
 * @Date 2019-05-25 0:02
 */
@RestController
@RequestMapping(value = "mobile/article")
public class ArticleController extends AbstractController{

    @Autowired
    private ArticleService articleService;

    /**
     * 发布新闻
     * @param
     * @return
     */
    @RequestMapping(value = "publishMsg",method = RequestMethod.POST)
    public String add(@RequestBody ArticleEntity articleEntity){
        articleService.add(articleEntity);
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 发表评论
     * @param
     * @return
     */
    @RequestMapping(value = "addComment",produces="application/json;charset=utf-8",method = RequestMethod.POST)
    public String addcomment(@RequestBody CommentEntity commentEntity){
        try {
            articleService.addComment(commentEntity);
        }catch (Exception e){
            if(e instanceof CommentException){
                return JSON.toJSONString(BuildResponse.fail("CM1001",((CommentException) e).getMsg()));
            }else{
                return JSON.toJSONString(BuildResponse.fail());
            }
        }
        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 查找评论
     * @param
     * @return
     */
    @RequestMapping(value = "queryComment",method = RequestMethod.POST)
    public String queryComment(CommentEntity commentEntity){

        List<CommentEntity> commentEntities = articleService.queryComment(commentEntity);

        return JSON.toJSONString(BuildResponse.success(commentEntities));

    }

    /**
     * 删除所有该文章的评论
     * @param
     * @return
     */
    @RequestMapping(value = "deleteCommentByArticleId",method = RequestMethod.POST)
    public String deleteCommentByArticleId(CommentEntity commentEntity){

       articleService.deleteCommentByArticleId(commentEntity);

        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 删除单个该文章的评论
     * @param
     * @return
     */
    @RequestMapping(value = "deleteCommentByCommentId",method = RequestMethod.POST)
    public String deleteCommentByCommentId(CommentEntity commentEntity){

        articleService.deleteCommentByCommentId(commentEntity);

        return JSON.toJSONString(BuildResponse.success());

    }

    /**
     * 查询所有文章
     * @param
     * @return
     */
    @RequestMapping(value = "queryAll",method = RequestMethod.POST)
    public String queryAll(){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        List<ArticleEntity> articles = articleService.query(articleSelectModel);
        return JSON.toJSONString(BuildResponse.success(articles));

    }

    /**
     * 查询指定类型的文章
     * @param
     * @return
     */
    @RequestMapping(value = "queryByCategory",method = RequestMethod.POST)
    public String queryByCategory(@RequestParam String category){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setCategory(category);
        List<ArticleEntity> articles = articleService.query(articleSelectModel);
        return JSON.toJSONString(BuildResponse.success(articles));

    }
    /**
     * 查询指定标题的文章
     * @param
     * @return
     */
    @RequestMapping(value = "queryByArticleId",method = RequestMethod.POST)
    public String queryByArticleId(@RequestParam String articleId){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setTitle(articleId);
        List<ArticleEntity> articles = articleService.query(articleSelectModel);
        return JSON.toJSONString(BuildResponse.success(articles));

    }
    /**
     * 查询指定作者的文章
     * @param
     * @return
     */
    @RequestMapping(value = "queryByAuthorId",method = RequestMethod.POST)
    public String queryByAuthorId(@RequestParam String authorId){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setAuthorId(authorId);
        List<ArticleEntity> articles = articleService.query(articleSelectModel);
        return JSON.toJSONString(BuildResponse.success(articles));

    }
    /**
     * 查询指定作者的文章
     * @param
     * @return
     */
    @RequestMapping(value = "addPraiseNum",method = RequestMethod.POST)
    public String addPraiseNum(String userId,String articleId){
        Map<String,Object> returnMap = articleService.addPraiseNum(userId,articleId);
        return JSON.toJSONString(BuildResponse.success(returnMap));

    }
    @RequestMapping(value = "deleteArticleByArticleId",method = RequestMethod.POST)
    public String deleteArticleByArticleId(String  articleId){
        articleService.deleteArticleByArticleId(articleId);
        return JSON.toJSONString(BuildResponse.success());
    }
    @RequestMapping(value = "updateArticle",method = RequestMethod.POST)
    public String updateArticle(@RequestBody ArticleEntity  articleEntity){
        articleService.updateArticle(articleEntity);
        return JSON.toJSONString(BuildResponse.success());
    }

}
