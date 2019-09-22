package com.qiaomu.modules.article.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiaomu.common.utils.BuildResponse;
import com.qiaomu.common.utils.CommonUtils;
import com.qiaomu.modules.article.entity.ArticleEntity;
import com.qiaomu.modules.article.entity.ArticlePoint;
import com.qiaomu.modules.article.entity.CommentEntity;
import com.qiaomu.modules.article.exception.CommentException;
import com.qiaomu.modules.article.model.ArticleModel;
import com.qiaomu.modules.article.model.ArticleSelectModel;
import com.qiaomu.modules.article.service.ArticleService;
import com.qiaomu.modules.sys.controller.AbstractController;
import com.qiaomu.modules.sys.entity.SysUserEntity;
import com.qiaomu.modules.sys.service.SysUserService;
import com.qiaomu.modules.sys.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.qiaomu.common.utils.Constant.OUT_DIR;
import static com.qiaomu.common.utils.Constant.SERVER_URL;

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
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserServiceImpl sysUserServiceImpl;


    /**
     * 发布新闻
     * @param
     * @return
     */
    @RequestMapping(value = "publishMsg",method = RequestMethod.POST)
    public String add(HttpServletRequest request){
        ArticleEntity articleEntity = buildAticle(request);
        articleEntity.setViewNum(0);
        articleService.add(articleEntity);
        ArticlePoint articlePoint = new ArticlePoint();
        articlePoint.setUserId(articleEntity.getAuthorId());
        articlePoint.setPoint(3);
        articlePoint.setCommunityId(articleEntity.getCommunityId());
        articleService.insertArticlePoint(articlePoint);
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
        List<JSONObject> returnList = new ArrayList<>();

        for(CommentEntity commentEntity1:commentEntities){
            String headUrl = sysUserService.queryUserImageUrl(commentEntity1.getUserId());
            JSONObject json = (JSONObject)JSON.toJSON(commentEntity1);
            SysUserEntity sysUserEntity = sysUserServiceImpl.queryById(Long.valueOf(commentEntity1.getUserId()));
            json.put("headUrl",headUrl);
            String nickName = sysUserEntity.getNickName();
            if(nickName==null||nickName.isEmpty()){
                json.put("name",sysUserEntity.getUsername());
            }else{
                json.put("name",nickName);
            }

            returnList.add(json);
        }

        return JSON.toJSONString(BuildResponse.success(returnList));

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
    public String queryAll(String communityId){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setCommunityId(communityId);
        List<ArticleModel> articles = articleService.query(articleSelectModel);
        return JSON.toJSONString(BuildResponse.success(articles));

    }

    /**
     * 查询所有文章
     * @param
     * @return
     */
    @RequestMapping(value = "queryAllByCommunityIdAndCategary",method = RequestMethod.POST)
    public String queryAllByCommunityIdAndCategary(String communityId,String category,String userId){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setCommunityId(communityId);
        articleSelectModel.setCategory(category);
        articleSelectModel.setUserId(userId);//用户id并非文章作者
        List<ArticleModel> articles = articleService.query(articleSelectModel);
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
        List<ArticleModel> articles = articleService.query(articleSelectModel);
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
        List<ArticleModel> articles = articleService.query(articleSelectModel);
        return JSON.toJSONString(BuildResponse.success(articles));

    }

    /**
     * 增加浏览量
     * @param
     * @return
     */
    @RequestMapping(value = "addViewByArticleId",method = RequestMethod.POST)
    public String addViewByArticleId(@RequestParam String articleId){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setTitle(articleId);
        List<ArticleModel> articles = articleService.queryOnly(articleSelectModel);
        if(articles.size()>0){
            ArticleModel articleModel = articles.get(0);
            ArticleEntity articleEntity = JSONObject.parseObject(JSONObject.toJSONString(articleModel),ArticleEntity.class);
            int viewNum = articleEntity.getViewNum()+1;
            articleEntity.setViewNum(viewNum);
            articleService.updateArticlePraiseNum(articleEntity);
        }

        return JSON.toJSONString(BuildResponse.success(articles));

    }
    /**
     * 查询指定作者的文章
     * @param
     * @return
     */
    @RequestMapping(value = "queryByAuthorId",method = RequestMethod.POST)
    public String queryByAuthorId(String communityId,String category,String authorId){
        ArticleSelectModel articleSelectModel = new ArticleSelectModel();
        articleSelectModel.setUserId(authorId);
        articleSelectModel.setAuthorId(authorId);
        articleSelectModel.setCommunityId(communityId);
        articleSelectModel.setCategory(category);
        List<ArticleModel> articles = articleService.query(articleSelectModel);
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

    @RequestMapping(value = "updateArticleWithImage",method = RequestMethod.POST)
    public String updateArticleWithImage(HttpServletRequest request){
        ArticleEntity articleEntity = buildAticle(request);
        articleService.updateArticle(articleEntity);
        return JSON.toJSONString(BuildResponse.success());

    }


    private ArticleEntity buildAticle(HttpServletRequest request){
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath =OUT_DIR+"image/";    //需要放到spring容器中，待修改;()

        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;

        Map<String,String[]> params = req.getParameterMap();
        String title = CommonUtils.getMapValue("title",params)[0];
        String content = CommonUtils.getMapValue("content",params)[0];
        String category = CommonUtils.getMapValue("category",params)[0];
        String authorId = CommonUtils.getMapValue("authorId",params)[0];
        String isPrivate = CommonUtils.getMapValue("isPrivate",params)[0];
        String communityId = CommonUtils.getMapValue("communityId",params)[0];

        Map<String,String> imageUrls = new HashMap<String,String>();
        Iterator<String> filenames=req.getFileNames();

        while(filenames.hasNext()){
            String filekey = filenames.next();
            MultipartFile multipartFile = req.getFile(filekey);
            String fileName =CommonUtils.mkFileName(multipartFile.getOriginalFilename()) ;
            File saveFile = new File(savePath+fileName);
            try {
                multipartFile.transferTo(saveFile);
                imageUrls.put(filekey,SERVER_URL+"/outapp/image/"+fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(title);
        articleEntity.setCategory(category);
        articleEntity.setCommunityId(communityId);
        articleEntity.setContent(content);
        articleEntity.setAuthorId(authorId);
        articleEntity.setIsPrivate(isPrivate);
        articleEntity.setImageUrls(JSON.toJSONString(imageUrls));
        return articleEntity;

    }

    /**
     * 查询文章积分总数
     * @param
     * @return
     */
    @RequestMapping(value = "queryArticlePoint",method = RequestMethod.POST)
    public String queryArticlePoint(String userId,String communityId){
        ArticlePoint articlePoint = articleService.queryArticlePoint(userId,communityId);
        return JSON.toJSONString(BuildResponse.success(articlePoint));
    }

    /**
     * 查询文章积分排行
     * @param
     * @return
     */
    @RequestMapping(value = "queryArticlePoints",method = RequestMethod.POST)
    public String queryArticlePoints(String communityId){
        List<ArticlePoint> articlePoint = articleService.queryArticlePoints(communityId);
        return JSON.toJSONString(BuildResponse.success(articlePoint));
    }


}
