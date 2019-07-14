package com.qiaomu.websocket.users;

import com.qiaomu.websocket.bootstrap.data.InChatToDataBaseService;
import com.qiaomu.websocket.common.bean.InChatMessage;
import org.springframework.stereotype.Service;

/**
 * Created by MySelf on 2019/1/3.
 */
@Service
public class DataBaseServiceImpl implements InChatToDataBaseService {
    //获取消息
    public Boolean writeMessage(InChatMessage inChatMessage) {
        System.out.println(inChatMessage.toString());
        return true;
    }
}
