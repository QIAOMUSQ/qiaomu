package io.renren.modules.android.service;


import io.renren.modules.android.base.BaseService;
import io.renren.modules.android.entity.Label;
import io.renren.modules.android.entity.Posts;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author wonly
 * Create By 2019/1/1
 */
public interface PostsService{

    /**
     * 保存帖子
     * @param posts 帖子
     * @param labelId 标签id
     */
    void savePosts(Posts posts, Integer labelId, SysUserEntity user);
    /**
     * 翻页查询帖子
     * @param type
     * @param search
     * @param pageNo
     * @param length
     * @return
     */
    Page<Posts> getPostsByPage(String type, String search, int pageNo, int length);

    /**
     * 获取用户最近发布的10个POSTS
     * @param user
     * @return
     */
    List<Posts> getPostsByUser(SysUserEntity user);


    /**
     * 根据标签分页获取获取Posts
     * @param label
     * @return
     */
    List<Posts> getPostsByLabel(Label label, int pageNo, int lenght);

    Posts findOne(Integer integer);
}
