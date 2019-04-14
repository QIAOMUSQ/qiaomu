package io.renren.modules.android.service;

import java.util.List;

/**
 * @Author wonly
 * Create By 2019/1/1
 */
public interface RankService {

    /**
     * 获取最近一周热帖排行榜
     * @return
     */
   List<Object> findPostsRank();

    /**
     * 获取最近一周的新注册用户
     * @return
     */
   List<Object> findUserRank();
}
