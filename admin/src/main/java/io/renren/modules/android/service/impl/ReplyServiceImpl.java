package io.renren.modules.android.service.impl;


import io.renren.common.exception.ServiceProcessException;
import io.renren.modules.android.dao.PostsDao;
import io.renren.modules.android.dao.ReplyDao;
import io.renren.modules.android.entity.Posts;
import io.renren.modules.android.entity.Reply;
import io.renren.modules.android.service.ReplyService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author wonly
 * Create By 2019/1/1
 */
@Service
public class ReplyServiceImpl  implements ReplyService {

    @Autowired
    private PostsDao postsDao;

    @Autowired
    private ReplyDao replyDao;


    @Override
    public List<Reply> getReplyByPage(Integer postsId, int pageNo, int length) {

        return null;
    }




    @Transactional
    @Override
    public void saveReply(Reply reply, Integer postsId, SysUserEntity user) {
        try {
            Posts posts = postsDao.findOne(postsId);

            if (posts == null) throw new ServiceProcessException("帖子不存在!");

            //帖子回复数+1
            int count = posts.getReplyCount();
            posts.setReplyCount(++count);
            postsDao.save(posts);

            //添加回复
            reply.setInitTime(new Date());
            reply.setUser(user);
            reply.setPosts(posts);
            replyDao.save(reply);


        } catch (ServiceProcessException e) {
            throw e;
        } catch (Exception e) {
            // 所有编译期异常转换为运行期异常
            throw new ServiceProcessException("发布回复失败!");
        }
    }
}
