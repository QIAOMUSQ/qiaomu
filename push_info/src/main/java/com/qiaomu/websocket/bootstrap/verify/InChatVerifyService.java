package com.qiaomu.websocket.bootstrap.verify;

import com.alibaba.fastjson.JSONArray;

/**
 * 用户校验层
 * Created by MySelf on 2018/11/22.
 */
public interface InChatVerifyService {

    /**
     * token 验证
     * @param token
     * @return
     */
    boolean verifyToken(String token);

    /**
     * 群发，获取列表
     * @param groupId
     * @return
     */
    JSONArray getArrayByGroupId(String groupId);

}