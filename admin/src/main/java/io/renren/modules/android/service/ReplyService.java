package io.renren.modules.android.service;


import io.renren.modules.android.base.BaseService;
import io.renren.modules.android.entity.Reply;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReplyService {

    /**
     * 翻页获取回复
     *
     * @param postsId
     * @param pageNo
     * @param length
     * @return
     */
    List<Reply> getReplyByPage(Integer postsId, int pageNo, int length);

    /**
     * 保存回复
     * @param reply
     * @param postsId
     * @param user
     */
    void saveReply(Reply reply, Integer postsId, SysUserEntity user);
}
