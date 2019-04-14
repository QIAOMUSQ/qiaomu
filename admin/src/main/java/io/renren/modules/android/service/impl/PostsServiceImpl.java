package io.renren.modules.android.service.impl;


import io.renren.common.exception.ServiceProcessException;
import io.renren.modules.android.dao.LabelDao;
import io.renren.modules.android.dao.PostsDao;
import io.renren.modules.android.entity.Label;
import io.renren.modules.android.entity.Posts;
import io.renren.modules.android.service.PostsService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author wonly
 * Create By 2019/1/1
 */
@Service
public class PostsServiceImpl  implements PostsService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private SysUserDao userDao;

    @Autowired
    private PostsDao postsDao;

    @Transactional
    @Override
    public void savePosts(Posts posts, Integer labelId, SysUserEntity user) {
        try {
            Label label = labelDao.findOne(labelId);

            if (label == null) throw new ServiceProcessException("标签不存在!");
            //标签的帖子数量+1
            Integer postsCount = label.getPostsCount();
            label.setPostsCount(++postsCount);
            labelDao.save(label);

            //添加帖子
            posts.setLabel(label);
            posts.setInitTime(new Date());
            posts.setUser(user);
            postsDao.save(posts);
        } catch (ServiceProcessException e) {
            throw e;
        } catch (Exception e) {
            // 所有编译期异常转换为运行期异常
            throw new ServiceProcessException("发布帖子失败!");
        }
    }

    @Override
    public Page<Posts> getPostsByPage(String type, String search, int pageNo, int length) {



        return null;
    }

    @Override
    public List<Posts> getPostsByUser(SysUserEntity user) {

        return null;
    }

    @Override
    public List<Posts> getPostsByLabel(Label label, int pageNo, int lenght) {

        return null;
    }

    @Override
    public Posts findOne(Integer integer) {
        return null;
    }
}
