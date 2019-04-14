package io.renren.modules.android.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.android.entity.Posts;
import org.springframework.stereotype.Repository;

/**
 * Created by wonly-song on 2019/1/5.
 */
public interface PostsDao extends BaseMapper<Posts> {

    void save(Posts posts);

    Posts findOne(Integer postsId);
}
