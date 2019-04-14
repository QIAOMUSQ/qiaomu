package io.renren.modules.android.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.android.entity.Posts;
import io.renren.modules.android.entity.Reply;

/**
 * Created by wonly-song on 2019/1/6.
 */
public interface ReplyDao extends BaseMapper<Reply> {
   void save(Reply reply);
}
