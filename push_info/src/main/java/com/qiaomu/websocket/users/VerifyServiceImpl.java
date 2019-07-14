package com.qiaomu.websocket.users;

import com.alibaba.fastjson.JSONArray;
import com.qiaomu.websocket.bootstrap.verify.InChatVerifyService;
import org.springframework.stereotype.Service;

/**
 * Created by MySelf on 2019/1/3.
 */
@Service
public class VerifyServiceImpl implements InChatVerifyService {


    public boolean verifyToken(String token) {
        return true;
    }



    public JSONArray getArrayByGroupId(String groupId) {
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
        return jsonArray;
    }
}
