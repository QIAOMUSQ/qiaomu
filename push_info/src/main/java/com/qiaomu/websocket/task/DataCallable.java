package com.qiaomu.websocket.task;



import com.qiaomu.websocket.bootstrap.data.InChatToDataBaseService;
import com.qiaomu.websocket.common.utils.MessageChangeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 获取结果的异步线程任务
 * Create by UncleCatMySelf in 11:25 2018\12\30 0030
 */
public class DataCallable implements Callable<Boolean>{

    /** 用户读数据接口伪实现 */
    @Autowired
    private  InChatToDataBaseService inChatToDataBaseService;
    /** 消息数据 */
    private final Map<String,Object> maps;

    DataCallable(InChatToDataBaseService inChatToDataBaseService,Map<String,Object> maps) {
        this.inChatToDataBaseService = inChatToDataBaseService;
        this.maps = maps;
    }

    @Override
    public Boolean call() throws Exception {
        inChatToDataBaseService.writeMessage(MessageChangeUtil.Change(maps));
        return true;
    }
}
